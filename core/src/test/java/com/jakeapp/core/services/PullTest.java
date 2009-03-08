package com.jakeapp.core.services;

import static org.mockito.Mockito.when;

import java.io.File;
import java.io.StringBufferInputStream;
import java.io.StringReader;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import junit.framework.Assert;
import local.test.Tracer;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.jakeapp.core.dao.ILogEntryDao;
import com.jakeapp.core.domain.FileObject;
import com.jakeapp.core.domain.JakeObject;
import com.jakeapp.core.domain.LogEntrySerializer;
import com.jakeapp.core.domain.Project;
import com.jakeapp.core.domain.ProtocolType;
import com.jakeapp.core.domain.User;
import com.jakeapp.core.domain.logentries.JakeObjectNewVersionLogEntry;
import com.jakeapp.core.synchronization.ISyncService;
import com.jakeapp.core.synchronization.SyncServiceImpl;
import com.jakeapp.core.synchronization.change.ChangeListener;
import com.jakeapp.core.synchronization.helpers.MessageMarshaller;
import com.jakeapp.core.synchronization.request.TrustAllRequestHandlePolicy;
import com.jakeapp.core.util.ProjectApplicationContextFactory;
import com.jakeapp.core.util.UnprocessedBlindLogEntryDaoProxy;
import com.jakeapp.jake.fss.FSService;
import com.jakeapp.jake.fss.IFSService;
import com.jakeapp.jake.ics.ICService;
import com.jakeapp.jake.ics.UserId;
import com.jakeapp.jake.ics.exceptions.NotLoggedInException;
import com.jakeapp.jake.ics.filetransfer.AdditionalFileTransferData;
import com.jakeapp.jake.ics.filetransfer.FailoverCapableFileTransferService;
import com.jakeapp.jake.ics.filetransfer.FileRequestFileMapper;
import com.jakeapp.jake.ics.filetransfer.IFileTransferService;
import com.jakeapp.jake.ics.filetransfer.IncomingTransferListener;
import com.jakeapp.jake.ics.filetransfer.methods.ITransferMethod;
import com.jakeapp.jake.ics.filetransfer.methods.ITransferMethodFactory;
import com.jakeapp.jake.ics.filetransfer.negotiate.FileRequest;
import com.jakeapp.jake.ics.filetransfer.negotiate.INegotiationSuccessListener;
import com.jakeapp.jake.ics.filetransfer.runningtransfer.Status;
import com.jakeapp.jake.ics.impl.mock.MockICService;
import com.jakeapp.jake.ics.impl.mock.MockUserId;
import com.jakeapp.jake.ics.impl.sockets.filetransfer.FileTransfer;
import com.jakeapp.jake.ics.impl.sockets.filetransfer.SimpleSocketFileTransferMethod.ClientHandler;
import com.jakeapp.jake.ics.msgservice.IMsgService;
import com.jakeapp.jake.test.SimpleFakeMessageExchanger;
import com.jakeapp.jake.test.TmpdirEnabledTestCase;


public class PullTest extends TmpdirEnabledTestCase {
	private final class TracingChangeListener implements ChangeListener {

		@Override
		public INegotiationSuccessListener beganRequest(JakeObject jo) {
			tracer.step("beganRequest");
			return null;
		}

		@Override
		public void pullDone(JakeObject jo) {
			tracer.step("pullDone");
		}

		@Override
		public void pullFailed(JakeObject jo, Exception reason) {
			tracer.step("pullFailed");
		}

		@Override
		public void pullNegotiationDone(JakeObject jo) {
			tracer.step("pullNegotiationDone");
		}

		@Override
		public void pullProgressUpdate(JakeObject jo, Status status, double progress) {
			tracer.step("pullProgressUpdate");
		}
	}

	private static final Logger log = Logger.getLogger(PullTest.class);

	private ISyncService sync;

	@Mock
	private ICSManager icsmanager;

	@Mock
	private MessageMarshaller messageMarshaller;

	@Mock
	private ProjectApplicationContextFactory projectApplicationContextFactory;

	@Mock
	private IProjectsFileServices projectsFileServices;

	@Mock
	private MsgService msgService;

	private Project project = new Project("myProject", new UUID(4, 2), null, tmpdir);

	private FileObject fo = new FileObject(new UUID(2, 4), project, "/my/path.txt");

	@Mock
	private ILogEntryDao logEntryDao;

	private UnprocessedBlindLogEntryDaoProxy ublogEntryDao;

	private User member = new User(ProtocolType.XMPP, "a@b");

	private JakeObjectNewVersionLogEntry le = new JakeObjectNewVersionLogEntry(fo,
			member, "blabla", "bla", true);

	private ICService icservice = new MockICService();

	private IFileTransferService fileTransferService;

	private ITransferMethod mockTransferMethod;

	private Tracer tracer;
	
	@Before
	public void setup() throws Exception {
		super.setup();
		MockitoAnnotations.initMocks(this);
		SyncServiceImpl sync = new SyncServiceImpl();
		sync.setICSManager(icsmanager);
		sync.setMessageMarshaller(new MessageMarshaller(new LogEntrySerializer(projectApplicationContextFactory)));
		sync.setApplicationContextFactory(projectApplicationContextFactory);
		sync.setProjectsFileServices(projectsFileServices);
		sync.setRequestHandlePolicy(new TrustAllRequestHandlePolicy(
				projectApplicationContextFactory, projectsFileServices));

		this.sync = sync;
		when(msgService.getIcsManager()).thenReturn(icsmanager);

		ublogEntryDao = new UnprocessedBlindLogEntryDaoProxy(logEntryDao);
		le.setUuid(new UUID(432,3214));
		project.setMessageService(msgService);
		fo.setProject(project);

		when(projectApplicationContextFactory.getLogEntryDao(fo)).thenReturn(
				ublogEntryDao);
		when(projectApplicationContextFactory.getLogEntryDao(project)).thenReturn(
				ublogEntryDao);
		when(projectApplicationContextFactory.getUnprocessedAwareLogEntryDao(fo))
				.thenReturn(logEntryDao);
		when(projectApplicationContextFactory.getUnprocessedAwareLogEntryDao(project))
				.thenReturn(logEntryDao);
		
		
		IFSService fss = new FSService();
		fss.setRootPath(tmpdir.getAbsolutePath());
		when(projectsFileServices.getProjectFSService(project)).thenReturn(
				fss);

		when(logEntryDao.getLastVersion(fo, true)).thenReturn(le);
		when(logEntryDao.getLastVersion(fo, false)).thenReturn(le);
		when(logEntryDao.getLastOfJakeObject(fo, true)).thenReturn(le);
		when(logEntryDao.getLastOfJakeObject(fo, false)).thenReturn(le);

		when(icsmanager.getICService(project)).thenReturn(icservice);

		tracer = new Tracer();

	}

	private void responderSetup(final File file) throws NotLoggedInException {
		mockTransferMethod = new ITransferMethod() {
			private final Logger log = Logger.getLogger("Mocked ITransferMethod");

			@Override
			public void request(final FileRequest inrequest,
					INegotiationSuccessListener nsl) {
				log.debug("request: " + inrequest);
				
				log.debug("declaring success");
				FileTransfer ft = new FileTransfer() {

					{
						this.request = inrequest;
						this.localFile = file;
						this.request.setData(new AdditionalFileTransferData(
								this.localFile));
						this.status = Status.complete;
						this.amountWritten = inrequest.getFileSize();
					}

					@Override
					public Boolean isReceiving() {
						return true;
					}

				};
				log.debug("created transfer: " + ft);
				nsl.succeeded(ft);
				log.debug("declared success");
			}

			@Override
			public void startServing(IncomingTransferListener l,
					FileRequestFileMapper mapper) throws NotLoggedInException {

			}

			@Override
			public void stopServing() {

			}

		};

		ITransferMethodFactory transferMethodFactory = new ITransferMethodFactory() {

			@Override
			public ITransferMethod getTransferMethod(IMsgService negotiationService,
					UserId user) throws NotLoggedInException {
				return mockTransferMethod;
			}

		};
		SimpleFakeMessageExchanger sfme = new SimpleFakeMessageExchanger();
		MockUserId backendUser = new MockUserId(member.getUserId());
		IMsgService msg = sfme.addUser(backendUser);
		fileTransferService = new FailoverCapableFileTransferService();
		fileTransferService.addTransferMethod(transferMethodFactory, msg, backendUser);

		when(icsmanager.getTransferService(project)).thenReturn(fileTransferService);
	}

	@Test
	public void pull_noIncomingFile() throws Exception {
		responderSetup(new File(PullTest.this.tmpdir, "fileDoesntExist"));
		
		
		Assert.assertNotNull(projectApplicationContextFactory.getLogEntryDao(fo)
				.getLastVersion(fo));
		Assert.assertTrue(projectApplicationContextFactory.getLogEntryDao(fo)
				.getLastVersion(fo).getBelongsTo() instanceof FileObject);

		sync.startServing(project, new TracingChangeListener());
		Assert.assertNull(sync.pullObject(fo));
		Assert.assertTrue(tracer.await("pullNegotiationDone", 10, TimeUnit.MILLISECONDS));
		Assert.assertTrue(tracer.isDone());
	}

	@SuppressWarnings("deprecation")
	@Test
	public void pull() throws Exception {
		File file = new File(PullTest.this.tmpdir, "myOutputFile");
		StringBufferInputStream sbis = new StringBufferInputStream("Foo bar");
		FSService.writeFileStreamAbs(file.getAbsolutePath(), sbis);
		Assert.assertTrue(file.length() > 6);
		
		responderSetup(file);
		
		
		Assert.assertNotNull(projectApplicationContextFactory.getLogEntryDao(fo)
				.getLastVersion(fo));
		Assert.assertTrue(projectApplicationContextFactory.getLogEntryDao(fo)
				.getLastVersion(fo).getBelongsTo() instanceof FileObject);

		sync.startServing(project, new TracingChangeListener());
		Assert.assertEquals(fo, sync.pullObject(fo));
		Assert.assertTrue(tracer.await("pullNegotiationDone", 10, TimeUnit.MILLISECONDS));
		Assert.assertTrue(tracer.isDone());
	}

}