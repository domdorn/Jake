package com.jakeapp.gui.swing.helpers;

import com.jakeapp.core.domain.Project;
import com.jakeapp.gui.swing.JakeMainView;
import com.jakeapp.gui.swing.actions.file.*;
import com.jakeapp.gui.swing.actions.notes.CommitNoteAction;
import com.jakeapp.gui.swing.actions.notes.CreateNoteAction;
import com.jakeapp.gui.swing.actions.notes.DeleteNoteAction;
import com.jakeapp.gui.swing.actions.notes.SoftlockNoteAction;
import com.jakeapp.gui.swing.actions.project.CreateProjectAction;
import com.jakeapp.gui.swing.actions.project.DeleteProjectAction;
import com.jakeapp.gui.swing.actions.project.RenameProjectAction;
import com.jakeapp.gui.swing.actions.project.StartStopProjectAction;
import com.jakeapp.gui.swing.actions.project.SyncProjectAction;
import com.jakeapp.gui.swing.actions.users.InviteUsersAction;
import com.jakeapp.gui.swing.actions.view.SwitchFilesProjectContextAction;
import com.jakeapp.gui.swing.actions.view.SwitchLoginProjectContextAction;
import com.jakeapp.gui.swing.actions.view.SwitchNewsProjectContextAction;
import com.jakeapp.gui.swing.actions.view.SwitchNotesProjectContextAction;
import com.jakeapp.gui.swing.callbacks.DataChangedCallback;
import com.jakeapp.gui.swing.dialogs.debugging.ActiveTasks;
import com.jakeapp.gui.swing.dialogs.debugging.JakeDebugger;
import com.jakeapp.gui.swing.dialogs.generic.JSheet;
import com.jakeapp.gui.swing.globals.JakeContext;
import com.jakeapp.gui.swing.panels.FilePanel;
import com.jakeapp.gui.swing.panels.NotesPanel;
import com.jakeapp.gui.swing.xcore.CreateExampleProject;
import com.jakeapp.gui.swing.xcore.EventCore;
import com.jakeapp.gui.swing.xcore.ObjectCache;
import net.roydesign.app.AboutJMenuItem;
import net.roydesign.app.Application;
import net.roydesign.app.QuitJMenuItem;
import net.roydesign.mac.MRJAdapter;
import net.roydesign.ui.StandardMacAboutFrame;
import org.apache.log4j.Logger;
import org.jivesoftware.smack.XMPPConnection;

import javax.swing.*;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URI;

/**
 * The Main Jake Menu Bar.
 *
 * @author studpete
 */
public class JakeMenuBar extends JMenuBar {
	private static final Logger log = Logger.getLogger(JakeMenuBar.class);

	public JakeMenuBar() {
		super();

		// Get the application instance
		Application app = Application.getInstance();
		org.jdesktop.application.ResourceMap resourceMap =
						org.jdesktop.application.Application
										.getInstance(com.jakeapp.gui.swing.JakeMainApp.class)
										.getContext().getResourceMap(JakeMainView.class);


		/****************************** Project *********************************/
		final JMenu projectMenu = new JMenu();
		projectMenu.setText(resourceMap.getString("projectMenu.text"));

		projectMenu.add(new JMenuItem(new CreateProjectAction(true)));
		projectMenu.add(new JMenuItem(new SyncProjectAction()));
		projectMenu.addSeparator();
		projectMenu.add(new JMenuItem(new StartStopProjectAction()));
		projectMenu.add(new JMenuItem(new RenameProjectAction()));
		projectMenu.add(new JMenuItem(new DeleteProjectAction()));
		projectMenu.addSeparator();
		projectMenu.add(new JMenuItem(new InviteUsersAction(true)));

		this.add(projectMenu);

		/****************************** View **********************************/
		final JMenu viewMenu = new JMenu();
		viewMenu.setText(resourceMap.getString("viewMenu.text"));

		viewMenu.add(new JMenuItem(new SwitchNewsProjectContextAction()));
		viewMenu.add(new JMenuItem(new SwitchFilesProjectContextAction()));
		viewMenu.add(new JMenuItem(new SwitchNotesProjectContextAction()));
		viewMenu.addSeparator();
		viewMenu.add(new JMenuItem(new SwitchLoginProjectContextAction()));

		this.add(viewMenu);

		/******************************* File *****************************/
		final JMenu fileMenu = new JMenu();
		fileMenu.setText(resourceMap.getString("fileMenu.text"));

		fileMenu.add(new JMenuItem(new OpenFileAction()));
		fileMenu.add(new JMenuItem(new ShowInBrowserFileAction()));
		fileMenu.add(new JMenuItem(new ResolveConflictFileAction()));
		fileMenu.addSeparator();
		fileMenu.add(new JMenuItem(new AnnounceFileAction()));
		fileMenu.add(new JMenuItem(new PullFileAction()));
		fileMenu.addSeparator();
		fileMenu.add(new JMenuItem(new DeleteFileAction()));
		fileMenu.add(new JMenuItem(new RenameFileAction()));
		fileMenu.add(new JMenuItem(new LockFileAction()));
		fileMenu.addSeparator();
		fileMenu.add(new JMenuItem(new InspectorFileAction()));
		fileMenu.add(new JMenuItem(new CreateFolderFileAction()));
		fileMenu.add(new JMenuItem(new ImportFileAction()));
		this.add(fileMenu);

		/****************************** Notes *******************************/
		final JMenu notesMenu = new JMenu();
		notesMenu.setText(resourceMap.getString("notesMenu.text"));

		notesMenu.add(new JMenuItem(new CreateNoteAction()));
		notesMenu.addSeparator();
		notesMenu.add(new JMenuItem(new DeleteNoteAction()));
		notesMenu.add(new JMenuItem(new CommitNoteAction()));
		notesMenu.addSeparator();
		notesMenu.add(new JMenuItem(new SoftlockNoteAction()));

		this.add(notesMenu);


		/******************************** Help **************************/
		JMenu helpMenu = new JMenu();
		helpMenu.setText(resourceMap.getString("helpMenu.text"));

		JMenuItem visitWebsiteMenuItem = new JMenuItem();
		javax.swing.ActionMap actionMap = org.jdesktop.application.Application
						.getInstance(com.jakeapp.gui.swing.JakeMainApp.class).getContext()
						.getActionMap(JakeMainView.class, JakeMainView.getMainView());
		visitWebsiteMenuItem.setAction(actionMap.get("showJakeWebsite")); // NOI18N
		visitWebsiteMenuItem
						.setText(resourceMap.getString("visitWebsiteMenuItem.text")); // NOI18N
		visitWebsiteMenuItem.setName("visitWebsiteMenuItem"); // NOI18N
		helpMenu.add(visitWebsiteMenuItem);


		// Get an About item instance.
		AboutJMenuItem aboutMenuItem = app.getAboutJMenuItem();
		// aboutMenuItem.setAction(actionMap.get("showAboutBox"));
		aboutMenuItem.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				StandardMacAboutFrame aboutFrame =
								new StandardMacAboutFrame(AppUtilities.getAppName(),
												AppUtilities.getAppVersion());
				aboutFrame
								.setApplicationIcon(UIManager.getIcon("OptionPane.informationIcon"));
				aboutFrame.setBuildVersion("001");
				aboutFrame.setCopyright("Copyright 2007-2009, Slowest ASE Team in the known universe");
				aboutFrame.setCredits(
								"<html><body>Jake<br>" + "<a href=\"http://jakeapp.com/\">jakeapp.com</a><br>" + "<br>We are proud to present you Jake." + "<b></b><br>" + "Send your Feedback to: " + "<a href=\"mailto:jake@jakeapp.com\">jake@jakeapp.com</a>" + "</body></html>",
								"text/html");
				aboutFrame.setHyperlinkListener(new HyperlinkListener() {

					public void hyperlinkUpdate(HyperlinkEvent e) {
						if (e.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
							try {
								Desktop.getDesktop().browse(new URI(e.getURL().toString()));
							} catch (Exception ex) {
								ex.printStackTrace();
							}
						}
					}
				});
				aboutFrame.setVisible(true);
			}
		});
		// If the menu is not already present because it's provided by
		// the OS (like on Mac OS X), then append it to our menu
		if (!AboutJMenuItem.isAutomaticallyPresent())
			helpMenu.add(aboutMenuItem);

		this.add(helpMenu);


		/***************************** Debug *****************************/
		JMenu debugMenu = new JMenu();
		debugMenu.setText("Debug");

		JMenuItem createExampleDebugItem = new JMenuItem("Create JakeShared Directory");
		createExampleDebugItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				CreateExampleProject.create();
			}
		});
		debugMenu.add(createExampleDebugItem);


		JMenuItem tasksDebugItem = new JMenuItem("Open Task Window");
		tasksDebugItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				ActiveTasks.createDialog();
			}
		});
		debugMenu.add(tasksDebugItem);


		final JMenuItem nimbusDebugItem = new JCheckBoxMenuItem("Use Nimbus LAF");
		nimbusDebugItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					if (nimbusDebugItem.isSelected()) {
						UIManager.setLookAndFeel(
										"com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
					} else {
						UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
					}
					//SwingUtilities.updateComponentTreeUI(JakeMainApp.getFrame());
					//JakeMainApp.getFrame().pack();

				} catch (Exception r) {
					ExceptionUtilities.showError(r);
				}
			}
		});
		debugMenu.add(nimbusDebugItem);

		debugMenu.add(new JSeparator());

		JMenuItem testInvitationDebugItem =
						new JMenuItem("Add Test Invitation (broken)");
		testInvitationDebugItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				EventCore.get().getInvitationListener().invited(null, new Project());
			}
		});
		debugMenu.add(testInvitationDebugItem);


		JMenuItem reloadFileDebugItem = new JMenuItem("Reload File View");
		reloadFileDebugItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				log.info("called: reload file view");
				FilePanel.getInstance().resetFilter();
				FilePanel.getInstance().updatePanel();
			}
		});
		debugMenu.add(reloadFileDebugItem);

		JMenuItem reloadNotesDebugItem = new JMenuItem("Reload Notes View");
		reloadNotesDebugItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				log.info("called: reload note view");
				NotesPanel.getInstance().resetFilter();
				ObjectCache.get().updateNotes(JakeContext.getProject());
			}
		});
		debugMenu.add(reloadNotesDebugItem);

		JMenuItem reloadDebugItem = new JMenuItem("Reload Everything");
		reloadDebugItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				EventCore.get().fireDataChanged(DataChangedCallback.ALL, null);
			}
		});
		debugMenu.add(reloadDebugItem);

		JMenuItem forceObjectCacheRefresh = new JMenuItem("Force cache refresh");
		forceObjectCacheRefresh.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				ObjectCache.get().updateAll();
			}
		});
		debugMenu.add(forceObjectCacheRefresh);


		final JCheckBoxMenuItem xmppDebugViewItem =
						new JCheckBoxMenuItem("XMPP Debug Window (activates on new connection)");
		xmppDebugViewItem.setSelected(XMPPConnection.DEBUG_ENABLED);
		xmppDebugViewItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				log.info("XMPP Debug Window");
				XMPPConnection.DEBUG_ENABLED = true;
				xmppDebugViewItem.setSelected(XMPPConnection.DEBUG_ENABLED);
			}
		});
		debugMenu.add(xmppDebugViewItem);


		JMenuItem showDebuggerDebugViewItem = new JMenuItem("Show JakeDebugger");
		showDebuggerDebugViewItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				new JakeDebugger();
			}
		});
		debugMenu.add(showDebuggerDebugViewItem);


		final JMenuItem showThreads = new JMenuItem("show threads");
		showThreads.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				ThreadGroup root = Thread.currentThread().getThreadGroup().getParent();
				while (root.getParent() != null) {
					root = root.getParent();
				}
				StringBuffer output = new StringBuffer("Running Threads:\n");
				this.visit(root, 0, output);

				JSheet.showMessageSheet(JakeMenuBar.this, output.toString());
			}

			public void visit(ThreadGroup group, int level, StringBuffer output) {
				// Get threads in `group'
				int numThreads = group.activeCount();
				Thread[] threads = new Thread[numThreads * 2 + 10];
				numThreads = group.enumerate(threads, false);

				// Enumerate each thread in `group'
				for (int i = 0; i < numThreads; i++) {
					// Get thread
					Thread thread = threads[i];
					for (int j = 0; j < level; j++) {
						output.append("\t");
					}
					String stack = "";
					StackTraceElement[] trace = thread.getStackTrace();
					if (trace != null && trace.length > 1)
						stack = trace[0].toString();
					output.append(thread.getState()).append(" - [").append(thread.getName())
									.append("] ").append(stack).append("\n");
				}

				// Get thread subgroups of `group'
				int numGroups = group.activeGroupCount();
				ThreadGroup[] groups = new ThreadGroup[numGroups * 2 + 10];
				numGroups = group.enumerate(groups, false);

				// Recursively visit each subgroup
				for (int i = 0; i < numGroups; i++) {
					visit(groups[i], level + 1, output);
				}
			}
		});
		debugMenu.add(showThreads);

		this.add(debugMenu);

		QuitJMenuItem quitMenuItem = app.getQuitJMenuItem();
		quitMenuItem.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				quit();
			}
		});
		if (!QuitJMenuItem.isAutomaticallyPresent()) {
			projectMenu.addSeparator();
			projectMenu.add(quitMenuItem);
		}

		// add special mac os event listener
		if (Platform.isMac()) {
			MRJAdapter.addReopenApplicationListener(new ActionListener() {

				public void actionPerformed(ActionEvent e) {
					log.trace("reopen");
					JakeMainView.setMainWindowVisible(true);
				}
			});

			MRJAdapter.addOpenDocumentListener(new ActionListener() {

				public void actionPerformed(ActionEvent e) {
					log.trace("openDocument");
					// TODO
				}
			});

			// TODO: does not work?
			Application.getInstance().setFramelessJMenuBar(this);
		}
	}


	private void quit() {
		log.trace("calling quit from MenuBar");
		JakeMainView.getMainView().quit();
	}
}