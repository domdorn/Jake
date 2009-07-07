package com.jakeapp.gui.swing.view.menuBar;

import com.jakeapp.gui.swing.model.MenuBarModel;
import com.jakeapp.gui.swing.controller.MenuBarController;

import com.jakeapp.gui.swing.view.menuBar.project.*;
import com.jakeapp.gui.swing.view.menuBar.view.SwitchViewEventsAction;
import com.jakeapp.gui.swing.view.menuBar.view.SwitchViewFilesAction;
import com.jakeapp.gui.swing.view.menuBar.view.SwitchViewNotesAction;
import com.jakeapp.gui.swing.view.menuBar.view.SwitchViewLoginAction;
import com.jakeapp.gui.swing.view.menuBar.files.*;
import com.jakeapp.gui.swing.view.menuBar.notes.CreateNoteAction;
import com.jakeapp.gui.swing.view.menuBar.notes.CommitNoteAction;
import com.jakeapp.gui.swing.view.menuBar.notes.DeleteNoteAction;
import com.jakeapp.gui.swing.view.menuBar.notes.SoftLockNoteAction;
import com.jakeapp.gui.swing.view.menuBar.debug.OpenTaskWindowAction;
import com.jakeapp.gui.swing.view.menuBar.debug.CreateExampleProjectAction;
import com.jakeapp.gui.swing.view.menuBar.debug.SetNimbusLAFAction;
import com.jakeapp.gui.swing.view.menuBar.help.ShowJakeWebsiteAction;
import com.jakeapp.gui.swing.view.menuBar.help.ShowAboutDialogAction;
import com.jakeapp.gui.swing.dialogs.debugging.JakeDebugger;
import com.jakeapp.gui.swing.dialogs.generic.JSheet;
import org.jdesktop.application.ResourceMap;
import org.jivesoftware.smack.XMPPConnection;

import javax.swing.*;
import java.util.Observer;
import java.util.Observable;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import net.roydesign.app.QuitJMenuItem;
import net.roydesign.app.Application;
import net.roydesign.app.AboutJMenuItem;


public class MenuBarView extends JMenuBar implements Observer {

	private final MenuBarModel model;
	private final MenuBarController controller;
	private final ResourceMap resourceMap;


	private final JMenu projectMenu;
	private final JMenu viewMenu;
	private final JMenu fileMenu;
	private final JMenu notesMenu;
	private final JMenu helpMenu;
	private final JMenu debugMenu;

	public MenuBarView(MenuBarModel model, MenuBarController controller, ResourceMap resourceMap) {
		this.model = model;
		this.controller = controller;
		this.resourceMap = resourceMap;


		this.model.addObserver(this);

		this.projectMenu = createProjectMenu();
		this.add(projectMenu);


		this.viewMenu = createViewMenu();
		this.add(viewMenu);


		this.fileMenu = createFileMenu();
		this.add(fileMenu);


		this.notesMenu = createNotesMenu();
		this.add(notesMenu);

		this.helpMenu = createHelpMenu();
		this.add(helpMenu);

		this.debugMenu = createDebugMenu();
		this.add(debugMenu);


		handleQuitButtonCreation();
		doOSSpecificStyling();
	}

	@Override
	public void update(Observable o, Object arg) {
		//To change body of implemented methods use File | Settings | File Templates.
	}


	private JMenu createProjectMenu() {
		final JMenu projectMenu = new JMenu();

		projectMenu.setText(resourceMap.getString("projectMenu.text"));
//
		projectMenu.add(new JMenuItem(new CreateProjectAction(controller, resourceMap)));
		projectMenu.add(new JMenuItem(new SyncProjectAction(controller, resourceMap)));
		projectMenu.addSeparator();
		projectMenu.add(new JMenuItem(new StartStopProjectAction(controller, resourceMap)));
		projectMenu.add(new JMenuItem(new RenameProjectAction(controller, resourceMap)));
		projectMenu.add(new JMenuItem(new DeleteProjectAction(controller, resourceMap)));
		projectMenu.addSeparator();
		projectMenu.add(new JMenuItem(new InviteUsersAction(controller, resourceMap)));
		return projectMenu;
	}


	private JMenu createViewMenu() {
		final JMenu viewMenu = new JMenu();
		viewMenu.setText(resourceMap.getString("viewMenu.text"));
		viewMenu.add(new JMenuItem(new SwitchViewEventsAction(controller, resourceMap)));
		viewMenu.add(new JMenuItem(new SwitchViewFilesAction(controller, resourceMap)));
		viewMenu.add(new JMenuItem(new SwitchViewNotesAction(controller, resourceMap)));
		viewMenu.addSeparator();
		viewMenu.add(new JMenuItem(new SwitchViewLoginAction(controller, resourceMap)));
		return viewMenu;
	}

	private JMenu createFileMenu() {
		final JMenu fileMenu = new JMenu();
		fileMenu.setText(resourceMap.getString("fileMenu.text"));
		fileMenu.add(new JMenuItem(new OpenFileAction(controller, resourceMap)));
		fileMenu.add(new JMenuItem(new ShowInBrowserFileAction(controller, resourceMap)));
		fileMenu.add(new JMenuItem(new ResolveConflictFileAction(controller, resourceMap)));
		fileMenu.add(new JMenuItem(new AnnounceFileAction(controller, resourceMap)));
		fileMenu.add(new JMenuItem(new PullFileAction(controller, resourceMap)));
		fileMenu.addSeparator();
		fileMenu.add(new JMenuItem(new DeleteFileAction(controller, resourceMap)));
		fileMenu.add(new JMenuItem(new RenameFileAction(controller, resourceMap)));
		fileMenu.add(new JMenuItem(new LockFileAction(controller, resourceMap)));
		fileMenu.addSeparator();
		fileMenu.add(new JMenuItem(new InspectorFileAction(controller, resourceMap)));
		fileMenu.add(new JMenuItem(new CreateFolderFileAction(controller, resourceMap)));
		fileMenu.add(new JMenuItem(new ImportFileAction(controller, resourceMap)));

		return fileMenu;
	}


	private JMenu createNotesMenu() {
		final JMenu notesMenu = new JMenu();
		notesMenu.setText(resourceMap.getString("notesMenu.text"));
		notesMenu.add(new JMenuItem(new CreateNoteAction(controller, resourceMap)));
		notesMenu.addSeparator();
		notesMenu.add(new JMenuItem(new DeleteNoteAction(controller, resourceMap)));
		notesMenu.add(new JMenuItem(new CommitNoteAction(controller, resourceMap)));
		notesMenu.addSeparator();
		notesMenu.add(new JMenuItem(new SoftLockNoteAction(controller, resourceMap)));

		return notesMenu;
	}

	private JMenu createHelpMenu() {
		JMenu helpMenu = new JMenu();
		helpMenu.setText(resourceMap.getString("helpMenu.text"));

		helpMenu.add(new JMenuItem(new ShowJakeWebsiteAction(controller, resourceMap)));

		AboutJMenuItem aboutMenuItem = Application.getInstance().getAboutJMenuItem(); // TODO FIX THIS DIRTY HACK
		aboutMenuItem.setAction(new ShowAboutDialogAction(controller, resourceMap));
		// If the menu is not already present because it's provided by
		// the OS (like on Mac OS X), then append it to our menu
		if (!AboutJMenuItem.isAutomaticallyPresent())
			helpMenu.add(aboutMenuItem);

		return helpMenu;
	}

	private JMenu createDebugMenu() {
		JMenu debugMenu = new JMenu();
		debugMenu.setText("Debug");


		debugMenu.add(new JMenuItem(new CreateExampleProjectAction(controller, resourceMap)));
		debugMenu.add(new JMenuItem(new OpenTaskWindowAction(controller, resourceMap)));
		debugMenu.add(new JMenuItem(new SetNimbusLAFAction(controller, resourceMap)));
		debugMenu.add(new JSeparator());
		debugMenu.add(new JCheckBoxMenuItem(new SetNimbusLAFAction(controller, resourceMap)));
		debugMenu.add(new JSeparator());
//
//		JMenuItem testInvitationDebugItem =
//						new JMenuItem("Add Test Invitation (broken)");
//		testInvitationDebugItem.addActionListener(new ActionListener() {
//
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				EventCore.getInstance().getInvitationListener().invited(null, new Project());
//			}
//		});
//		debugMenu.add(testInvitationDebugItem);
//
//
//		JMenuItem reloadFileDebugItem = new JMenuItem("Reload File View");
//		reloadFileDebugItem.addActionListener(new ActionListener() {
//
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				log.info("called: reload file view");
//				FilePanel.getInstance().resetFilter();
//				FilePanel.getInstance().updatePanel();
//			}
//		});
//		debugMenu.add(reloadFileDebugItem);
//
//		JMenuItem reloadNotesDebugItem = new JMenuItem("Reload NOTES View");
//		reloadNotesDebugItem.addActionListener(new ActionListener() {
//
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				log.info("called: reload note view");
//				NotesPanel.getInstance().resetFilter();
//				ObjectCache.get().updateNotes(JakeContext.getProject());
//			}
//		});
//		debugMenu.add(reloadNotesDebugItem);
//
//		JMenuItem reloadDebugItem = new JMenuItem("Reload Everything");
//		reloadDebugItem.addActionListener(new ActionListener() {
//
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				EventCore.getInstance().fireDataChanged(DataChangedCallback.ALL, null);
//			}
//		});
//		debugMenu.add(reloadDebugItem);
//
//		JMenuItem forceObjectCacheRefresh = new JMenuItem("Force cache refresh");
//		forceObjectCacheRefresh.addActionListener(new ActionListener() {
//
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				ObjectCache.get().updateAll();
//			}
//		});
//		debugMenu.add(forceObjectCacheRefresh);
//
//
		final JCheckBoxMenuItem xmppDebugViewItem =
						new JCheckBoxMenuItem("XMPP Debug Window (activates on new connection)");
		xmppDebugViewItem.setSelected(XMPPConnection.DEBUG_ENABLED);
		xmppDebugViewItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
//				log.info("XMPP Debug Window");
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

				JSheet.showMessageSheet(MenuBarView.this, output.toString());
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
//
//		this.add(debugMenu);
		return debugMenu;
	}

	private void handleQuitButtonCreation() {
		QuitJMenuItem quitMenuItem = Application.getInstance().getQuitJMenuItem(); // TODO fixme fix this dirty hack!
		quitMenuItem.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				controller.quit();
			}
		});
		if (!QuitJMenuItem.isAutomaticallyPresent()) {
			projectMenu.addSeparator();
			projectMenu.add(quitMenuItem);
		}
	}


	private void doOSSpecificStyling() {
		//		// add special mac os event listener
//		if (Platform.isMac()) {
//			MRJAdapter.addReopenApplicationListener(new ActionListener() {
//
//				public void actionPerformed(ActionEvent e) {
//					log.trace("reopen");
//					MainWindow.setMainWindowVisible(true);
//				}
//			});
//
//			MRJAdapter.addOpenDocumentListener(new ActionListener() {
//
//				public void actionPerformed(ActionEvent e) {
//					log.trace("openDocument");
//					// TODO
//				}
//			});
//
//			// TODO: does not work?
//			Application.getInstance().setFramelessJMenuBar(this);
//		}
	}
}