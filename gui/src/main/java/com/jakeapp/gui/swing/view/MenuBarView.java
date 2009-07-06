package com.jakeapp.gui.swing.view;

import com.jakeapp.gui.swing.model.MenuBarModel;
import com.jakeapp.gui.swing.controller.MenuBarController;
import com.jakeapp.gui.swing.actions.project.CreateProjectAction;
import com.jakeapp.gui.swing.helpers.ImageLoader;
import org.jdesktop.application.ResourceMap;

import javax.swing.*;
import java.util.Observer;
import java.util.Observable;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import net.roydesign.app.QuitJMenuItem;
import net.roydesign.app.Application;


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
		projectMenu.add(
				new JMenuItem(
						resourceMap.getString("createProjectMenuItem.text") + (model.getEllipsis() ? "..." : ""),
						ImageLoader.getScaled(getClass(), "/icons/createproject.png", 32))).addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				controller.createProject();
			}
		});


//		projectMenu.add(new JMenuItem(new CreateProjectAction(true, resourceMap)));
//		projectMenu.add(new JMenuItem(new SyncProjectAction(resourceMap)));
//		projectMenu.addSeparator();
//		projectMenu.add(new JMenuItem(new StartStopProjectAction(projectHelper)));
//		projectMenu.add(new JMenuItem(new RenameProjectAction(resourceMap)));
//		projectMenu.add(new JMenuItem(new DeleteProjectAction(resourceMap)));
//		projectMenu.addSeparator();
//		projectMenu.add(new JMenuItem(new InviteUsersAction(true, resourceMap)));
		return projectMenu;
	}


	private JMenu createViewMenu() {
		final JMenu viewMenu = new JMenu();
		viewMenu.setText(resourceMap.getString("viewMenu.text"));
//
//		viewMenu.add(new JMenuItem(new SwitchNewsProjectContextAction(contextViewChangedHolder, resourceMap, contextViewPanelHolder)));
//		viewMenu.add(new JMenuItem(new SwitchFilesProjectContextAction(contextViewChangedHolder, resourceMap, contextViewPanelHolder)));
//		viewMenu.add(new JMenuItem(new SwitchNotesProjectContextAction(contextViewChangedHolder, resourceMap, contextViewPanelHolder)));
//		viewMenu.addSeparator();
//		viewMenu.add(new JMenuItem(new SwitchLoginProjectContextAction(contextViewChangedHolder, resourceMap, contextViewPanelHolder)));
		return viewMenu;
	}

	private JMenu createFileMenu() {
		final JMenu fileMenu = new JMenu();
		fileMenu.setText(resourceMap.getString("fileMenu.text"));
//
//		fileMenu.add(new JMenuItem(new OpenFileAction(this.eventCore, this.filePanel, resourceMap)));
//		fileMenu.add(new JMenuItem(new ShowInBrowserFileAction(eventCore, filePanel, resourceMap)));
//		fileMenu.add(new JMenuItem(new ResolveConflictFileAction(eventCore, filePanel, resourceMap)));
//		fileMenu.addSeparator();
//		fileMenu.add(new JMenuItem(new AnnounceFileAction(eventCore, filePanel, resourceMap)));
//		fileMenu.add(new JMenuItem(new PullFileAction(eventCore, filePanel, resourceMap)));
//		fileMenu.addSeparator();
//		fileMenu.add(new JMenuItem(new DeleteFileAction(eventCore, filePanel, resourceMap)));
//		fileMenu.add(new JMenuItem(new RenameFileAction(eventCore, filePanel, resourceMap)));
//		fileMenu.add(new JMenuItem(new LockFileAction(eventCore, filePanel, resourceMap)));
//		fileMenu.addSeparator();
//		fileMenu.add(new JMenuItem(new InspectorFileAction(eventCore, filePanel, this.inspectorStateHolder, resourceMap)));
//		fileMenu.add(new JMenuItem(new CreateFolderFileAction(eventCore, filePanel, resourceMap)));
//		fileMenu.add(new JMenuItem(new ImportFileAction(eventCore, filePanel, resourceMap)));
		return fileMenu;
	}


	private JMenu createNotesMenu() {
		final JMenu notesMenu = new JMenu();
		notesMenu.setText(resourceMap.getString("notesMenu.text"));
//
//
////		TODO reenable this!
////		notesMenu.add(new JMenuItem(new CreateNoteAction(this.notesPanel, resourceMap)));
////		notesMenu.addSeparator();
////		notesMenu.add(new JMenuItem(new DeleteNoteAction(this.notesPanel, resourceMap)));
////		notesMenu.add(new JMenuItem(new CommitNoteAction(this.notesPanel, resourceMap)));
////		notesMenu.addSeparator();
////		notesMenu.add(new JMenuItem(new SoftlockNoteAction(this.notesPanel, resourceMap)));
		return notesMenu;
	}

	private JMenu createHelpMenu() {
		JMenu helpMenu = new JMenu();
		helpMenu.setText(resourceMap.getString("helpMenu.text"));
//
//
//		/*
//
//		// TODO fixme - is it really necessary to use actionmap just to make a clickable link?
//		JMenuItem visitWebsiteMenuItem = new JMenuItem();
//
//
//		javax.swing.ActionMap actionMap = org.jdesktop.application.Application
//						.getInstance(com.jakeapp.gui.swing.JakeMainApp.class).getContext()
//						.getActionMap(MainWindow.class, MainWindow.getMainView());
//		visitWebsiteMenuItem.setAction(actionMap.get("showJakeWebsite")); // NOI18N
//
//		visitWebsiteMenuItem
//						.setText(resourceMap.getString("visitWebsiteMenuItem.text")); // NOI18N
//		visitWebsiteMenuItem.setName("visitWebsiteMenuItem"); // NOI18N
//		helpMenu.add(visitWebsiteMenuItem);
//
//		*/
//
//
//		// Get an About item instance.
//		AboutJMenuItem aboutMenuItem = app.getAboutJMenuItem();
//		// aboutMenuItem.setAction(actionMap.getInstance("showAboutBox"));
//		aboutMenuItem.addActionListener(new ActionListener() {
//
//			public void actionPerformed(ActionEvent e) {
//				StandardMacAboutFrame aboutFrame =
//								new StandardMacAboutFrame(AppUtilities.getAppName(),
//												AppUtilities.getAppVersion());
//				aboutFrame
//								.setApplicationIcon(UIManager.getIcon("OptionPane.informationIcon"));
//				aboutFrame.setBuildVersion("001");
//				aboutFrame.setCopyright("Copyright 2007-2009, Slowest ASE Team in the known universe");
//				aboutFrame.setCredits(
//								"<html><body>Jake<br>" + "<a href=\"http://jakeapp.com/\">jakeapp.com</a><br>" + "<br>We are proud to present you Jake." + "<b></b><br>" + "Send your Feedback to: " + "<a href=\"mailto:jake@jakeapp.com\">jake@jakeapp.com</a>" + "</body></html>",
//								"text/html");
//				aboutFrame.setHyperlinkListener(new HyperlinkListener() {
//
//					public void hyperlinkUpdate(HyperlinkEvent e) {
//						if (e.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
//							try {
//								Desktop.getDesktop().browse(new URI(e.getURL().toString()));
//							} catch (Exception ex) {
//								ex.printStackTrace();
//							}
//						}
//					}
//				});
//				aboutFrame.setVisible(true);
//			}
//		});
//		// If the menu is not already present because it's provided by
//		// the OS (like on Mac OS X), then append it to our menu
//		if (!AboutJMenuItem.isAutomaticallyPresent())
//			helpMenu.add(aboutMenuItem);
//
//		this.add(helpMenu);
		return helpMenu;
	}

	private JMenu createDebugMenu()
	{
				JMenu debugMenu = new JMenu();
		debugMenu.setText("Debug");
//
//		JMenuItem createExampleDebugItem = new JMenuItem("Create JakeShared Directory");
//		createExampleDebugItem.addActionListener(new ActionListener() {
//
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				CreateExampleProject.create();
//			}
//		});
//		debugMenu.add(createExampleDebugItem);
//
//
//		JMenuItem tasksDebugItem = new JMenuItem("Open Task Window");
//		tasksDebugItem.addActionListener(new ActionListener() {
//
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				ActiveTasks.createDialog();
//			}
//		});
//		debugMenu.add(tasksDebugItem);
//
//
//		final JMenuItem nimbusDebugItem = new JCheckBoxMenuItem("Use Nimbus LAF");
//		nimbusDebugItem.addActionListener(new ActionListener() {
//
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				try {
//					if (nimbusDebugItem.isSelected()) {
//						UIManager.setLookAndFeel(
//										"com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
//					} else {
//						UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
//					}
//					//SwingUtilities.updateComponentTreeUI(JakeMainApp.getFrame());
//					//JakeMainApp.getFrame().pack();
//
//				} catch (Exception r) {
//					ExceptionUtilities.showError(r);
//				}
//			}
//		});
//		debugMenu.add(nimbusDebugItem);
//
//		debugMenu.add(new JSeparator());
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
//		final JCheckBoxMenuItem xmppDebugViewItem =
//						new JCheckBoxMenuItem("XMPP Debug Window (activates on new connection)");
//		xmppDebugViewItem.setSelected(XMPPConnection.DEBUG_ENABLED);
//		xmppDebugViewItem.addActionListener(new ActionListener() {
//
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				log.info("XMPP Debug Window");
//				XMPPConnection.DEBUG_ENABLED = true;
//				xmppDebugViewItem.setSelected(XMPPConnection.DEBUG_ENABLED);
//			}
//		});
//		debugMenu.add(xmppDebugViewItem);
//
//
//		JMenuItem showDebuggerDebugViewItem = new JMenuItem("Show JakeDebugger");
//		showDebuggerDebugViewItem.addActionListener(new ActionListener() {
//
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				new JakeDebugger();
//			}
//		});
//		debugMenu.add(showDebuggerDebugViewItem);
//
//
//		final JMenuItem showThreads = new JMenuItem("show threads");
//		showThreads.addActionListener(new ActionListener() {
//
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				ThreadGroup root = Thread.currentThread().getThreadGroup().getParent();
//				while (root.getParent() != null) {
//					root = root.getParent();
//				}
//				StringBuffer output = new StringBuffer("Running Threads:\n");
//				this.visit(root, 0, output);
//
//				JSheet.showMessageSheet(JakeMenuBar.this, output.toString());
//			}
//
//			public void visit(ThreadGroup group, int level, StringBuffer output) {
//				// Get threads in `group'
//				int numThreads = group.activeCount();
//				Thread[] threads = new Thread[numThreads * 2 + 10];
//				numThreads = group.enumerate(threads, false);
//
//				// Enumerate each thread in `group'
//				for (int i = 0; i < numThreads; i++) {
//					// Get thread
//					Thread thread = threads[i];
//					for (int j = 0; j < level; j++) {
//						output.append("\t");
//					}
//					String stack = "";
//					StackTraceElement[] trace = thread.getStackTrace();
//					if (trace != null && trace.length > 1)
//						stack = trace[0].toString();
//					output.append(thread.getState()).append(" - [").append(thread.getName())
//									.append("] ").append(stack).append("\n");
//				}
//
//				// Get thread subgroups of `group'
//				int numGroups = group.activeGroupCount();
//				ThreadGroup[] groups = new ThreadGroup[numGroups * 2 + 10];
//				numGroups = group.enumerate(groups, false);
//
//				// Recursively visit each subgroup
//				for (int i = 0; i < numGroups; i++) {
//					visit(groups[i], level + 1, output);
//				}
//			}
//		});
//		debugMenu.add(showThreads);
//
//		this.add(debugMenu);
		return debugMenu;
	}

	private void handleQuitButtonCreation()
	{
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


	private void doOSSpecificStyling()
	{
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

//
//
//		/***************************** Debug *****************************/

//

//
