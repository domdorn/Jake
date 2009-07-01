package com.jakeapp.gui.swing;

import com.explodingpixels.macwidgets.MacUtils;
import com.explodingpixels.macwidgets.MacWidgetFactory;
import com.explodingpixels.macwidgets.TriAreaComponent;
import com.explodingpixels.widgets.WindowUtils;
import com.jakeapp.core.domain.Invitation;
import com.jakeapp.core.domain.InvitationState;
import com.jakeapp.core.domain.Project;
import com.jakeapp.gui.swing.callbacks.ContextChangedCallback;
import com.jakeapp.gui.swing.callbacks.ContextViewChangedCallback;
import com.jakeapp.gui.swing.callbacks.ProjectChangedCallback;
import com.jakeapp.gui.swing.callbacks.ProjectViewChangedCallback;
import com.jakeapp.gui.swing.components.JakeSourceList;
import com.jakeapp.gui.swing.components.JakeStatusBar;
import com.jakeapp.gui.swing.components.JakeToolbar;
import com.jakeapp.gui.swing.dialogs.JakeAboutDialog;
import com.jakeapp.gui.swing.globals.JakeContext;
import com.jakeapp.gui.swing.helpers.*;
import com.jakeapp.gui.swing.helpers.dragdrop.FileDropHandler;
import com.jakeapp.gui.swing.panels.FilePanel;
import com.jakeapp.gui.swing.panels.InspectorPanel;
import com.jakeapp.gui.swing.panels.InvitationPanel;
import com.jakeapp.gui.swing.panels.NewsPanel;
import com.jakeapp.gui.swing.panels.NotesPanel;
import com.jakeapp.gui.swing.panels.UserPanel;
import com.jakeapp.gui.swing.worker.tasks.InitCoreTask;
import com.jakeapp.gui.swing.worker.JakeExecutor;
import com.jakeapp.gui.swing.xcore.EventCore;
import com.jakeapp.gui.swing.xcore.JakeDatabaseTools;
import com.jakeapp.gui.swing.xcore.SyncUpdateTimer;
import org.apache.log4j.Logger;
import org.jdesktop.application.Action;
import org.jdesktop.application.FrameView;
import org.jdesktop.application.ResourceMap;
import org.jdesktop.swingx.JXPanel;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.List;


/**
 * The application's main frame.
 */
public class JakeMainView extends FrameView implements ContextChangedCallback {
	private static final Logger log = Logger.getLogger(JakeMainView.class);
	public static final int CONTENT_SPLITTERSIZE = 2;
	private static JakeMainView mainView;
	private final EventCore eventCore;

	private final JakeMainApp jakeMainApp;
	// all the ui panels
	private final NewsPanel newsPanel;

	private final FilePanel filePanel;
	private final NotesPanel notesPanel;
	private final InvitationPanel invitationPanel;
	private final UserPanel loginPanel;
	private final JakeToolbar jakeToolbar;
	private final ContextViewChangedHolder contextViewChangedHolder;
	private final ContextViewPanelHolder contextViewPanelHolder;
	private final ProjectViewHolder projectViewHolder;

	private final ResourceMap resourceMap;
	private final ContentPanelHolder contentPanelHolder;
	private final ContextSwitcherButtonsHolder contextSwitcherButtonsHolder;

	private JPanel inspectorPanel;


	private JakeMenuBar menuBar;
	private javax.swing.JPanel contentPanel = new JPanel();
	private JSplitPane contentPanelSplit;
	private JDialog aboutBox;

	private JakeStatusBar jakeStatusBar;
	private JakeTrayIcon tray;


	/**************** BEGIN INSPECTOR STUFF ******************/
	private final InspectorStateHolder inspectorStateHolder;

	public boolean isInspectorEnabled() {
		return inspectorStateHolder.isInspectorEnabled();
	}

	public void setInspectorEnabled(boolean inspectorEnabled) {

		inspectorStateHolder.setInspectorEnabled(inspectorEnabled);
		updateInspectorPanelVisibility();
	}

	/**
	 * Checks if the inspector is allowed to be displayed.
	 *
	 * @return true if CAN be displayed with current content.
	 */
	public boolean isInspectorAllowed() {
		return inspectorStateHolder.isInspectorAllowed();
	}

	/**************** END INSPECTOR STUFF ******************/



	private final ProjectViewChangedHolder projectViewChangedHolder;

	// TODO will be removed
	public void addProjectViewChangedListener(ProjectViewChangedCallback pvc) {
		projectViewChangedHolder.add(pvc);
	}

	// TODO will be removed
	public void removeProjectViewChangedListener(ProjectViewChangedCallback pvc) {
		projectViewChangedHolder.remove(pvc);
	}

	private JPanel statusPanel;

	private JakeMainApp app;
	private final JakeSourceList sourceList;
	private JSplitPane mainSplitPane;

	private Image IconAppSmall;
	private Image IconAppLarge;



	/**
	 * Returns the large application image.
	 *
	 * @return
	 */
	public Image getLargeAppImage() {
		return IconAppLarge;
	}

	@Override
	public void contextChanged(EnumSet<Reason> reason, Object context) {
		updateAll();
	}


	//	public JakeMainView(SingleFrameApplication app) {
	public JakeMainView(JakeMainApp app,
						EventCore eventCore,
						UserPanel loginPanel,
						NewsPanel newsPanel,
						ContextViewChangedHolder contextViewChangedHolder,
						ContextViewPanelHolder contextViewPanelHolder,
						InvitationPanel invitationPanel,
						JakeSourceList jakeSourceList,
						ResourceMap resourceMap,
						FilePanel filePanel,
						NotesPanel notesPanel,
						InspectorPanel inspectorPanel,

						ProjectViewChangedHolder projectViewChangedHolder, ProjectViewHolder projectViewHolder, ContentPanelHolder contentPanelHolder,
						InspectorStateHolder inspectorStateHolder,
						ContextSwitcherButtonsHolder contextSwitcherButtonsHolder) {
		super(app);

		this.jakeMainApp = app;
		this.eventCore = eventCore;
		this.loginPanel = loginPanel;
		this.contextViewChangedHolder = contextViewChangedHolder;
		this.contextViewPanelHolder = contextViewPanelHolder;
		this.invitationPanel = invitationPanel;
		this.sourceList = jakeSourceList;
		this.resourceMap = resourceMap;
		this.filePanel = filePanel;
		this.newsPanel = newsPanel;
		this.notesPanel = notesPanel;
		this.inspectorPanel = inspectorPanel;
		this.projectViewChangedHolder = projectViewChangedHolder;
		this.projectViewHolder = projectViewHolder;
		this.contentPanelHolder = contentPanelHolder;


		this.inspectorStateHolder = inspectorStateHolder;
		this.contextSwitcherButtonsHolder = contextSwitcherButtonsHolder;


		IconAppSmall = ImageLoader.get(getClass(), "/icons/jakeapp.png").getImage();
		IconAppLarge = ImageLoader.get(getClass(), "/icons/jakeapp-large.png").getImage();

		setMainView(this);
		this.app = app;

		log.debug("tray icon ...");
		tray = new JakeTrayIcon();
		log.debug("toolbar ...");
		jakeToolbar = new JakeToolbar(eventCore,
				filePanel,
				inspectorStateHolder,
				contextViewPanelHolder,
				contextSwitcherButtonsHolder,
				resourceMap,

				this.getFrame()
		);


		
		// initialize helper code
		log.debug("helpers ...");
		JakeHelper.initializeJakeMainHelper();

		// macify-window
		if (Platform.isMac()) {
			MacUtils.makeWindowLeopardStyle(this.getFrame().getRootPane());
			setMacSystemProperties();
		}

		// set window icon (small, large)
		// large icon may be shown e.g. on big icon tab switch on vista
		log.debug("icons ...");
		this.getFrame().setIconImage(IconAppSmall);
		this.getFrame().setIconImages(Arrays.asList(IconAppSmall, IconAppLarge));

		// set app size
		this.getFrame().setMinimumSize(new Dimension(600, 600));
		this.getFrame().setSize(new Dimension(800, 800));

		// initialize the mantisse gui components (menu)
		log.debug("setting up components ...");
		initComponents();

		// adapt the menu if we live on a mac
		if (Platform.isMac()) {
			// install the close handler (meta-w)
			GuiUtilities.installMacCloseHandler(getFrame());
		}

		// init the content panel and the splitter
//		getContentPanel().setLayout(new BorderLayout());

		contentPanelHolder.init();

		contentPanelSplit = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
				contentPanelHolder.getContentPanel(),
				inspectorPanel);
		contentPanelSplit.setOneTouchExpandable(false);
		contentPanelSplit.setContinuousLayout(true);
		contentPanelSplit.setBorder(null);
		contentPanelSplit.setResizeWeight(1.0);
		contentPanelSplit.setEnabled(true);
		contentPanelSplit.setDividerSize(CONTENT_SPLITTERSIZE);
		contentPanelSplit
				.addPropertyChangeListener(new ResizeListener(contentPanelSplit));
		updateInspectorPanelVisibility();

		// add the toolbar
		TriAreaComponent toolBar = jakeToolbar.createToolBar();
		this.getFrame().add(toolBar.getComponent(), BorderLayout.NORTH);

		// create the panels split pane


		mainSplitPane = this.createSourceListAndMainArea();
		this.getFrame().add(mainSplitPane, BorderLayout.CENTER);

		// create status bar
		jakeStatusBar = new JakeStatusBar(eventCore, contextViewPanelHolder, resourceMap);
		statusPanel.add(jakeStatusBar.getComponent());

		// set default window behaviour
		log.debug("setting up window behaviour ...");
		WindowUtils.createAndInstallRepaintWindowFocusListener(this.getFrame());
		this.getFrame().setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		// register dragdrop handler
		log.debug("setting up drag and drop ...");
		this.getFrame().setTransferHandler(new FileDropHandler());

		log.debug("callbacks ...");
		registerCallbacks();

//		log.debug("selecting login ...");
//		contextViewPanelHolder.setContextViewPanel(ContextPanelEnum.Login);

		updateTitle();

		/**
		 * Check if the CAPS_LOCK key is pressed at startup.
		 * If so, ask the User if he wants to reset the Database.
		 */
		JakeDatabaseTools.checkKeysResetDatabase();

		log.debug("launching ...");
		JakeExecutor.exec(new InitCoreTask());

		// debug property
		if (System.getProperty("com.jakeapp.gui.test.instantquit") != null) {
//			JakeMainApp.getInstance().saveQuit();
			jakeMainApp.saveQuit();
		}

		// init auto syncer
		new SyncUpdateTimer();
	}


	public FilePanel getFilePanel() {
		return filePanel;
	}

	public NotesPanel getNotesPanel() {
		return notesPanel;
	}




	public List<JToggleButton> getContextSwitcherButtons() {
		return contextSwitcherButtonsHolder.getContextSwitcherButtons();
	}

	/**
	 * This is a private inner class to control the resizing
	 * of the JSplitPane for the Inspector.
	 */
	private class ResizeListener implements PropertyChangeListener {
		private boolean inSplitPaneResized;
		private JSplitPane splitPane;

		public ResizeListener(JSplitPane splitPane) {
			this.splitPane = splitPane;
		}

		public void propertyChange(PropertyChangeEvent evt) {
			splitPaneResized(evt);
		}

		private void splitPaneResized(PropertyChangeEvent evt) {
			if (inSplitPaneResized) {
				return;
			}

			inSplitPaneResized = true;

			if (evt.getPropertyName().equalsIgnoreCase("dividerLocation")) {
				int current = splitPane.getDividerLocation();
				int max = splitPane.getMaximumDividerLocation();
				log.debug("splitPaneResized: current: " + current + " max: " + max);
				log.debug("splitPane.getWidth()-current: " + (splitPane
						.getWidth() - current));

				// hide inspector!
				if ((splitPane.getWidth() - current) < 155) {

					// only hide if this was done by user
					// inspector is hidden sometimes, but remember the original state!
					if (inspectorStateHolder.isInspectorAllowed()) {
						inspectorStateHolder.setInspectorEnabled(false);
					}
				}
				// limit the minimum size manually, to detect closing wish
				else if ((splitPane.getWidth() - current) < InspectorPanel.INSPECTOR_SIZE) {
					splitPane.setDividerLocation(splitPane
							.getWidth() - InspectorPanel.INSPECTOR_SIZE);
					inspectorStateHolder.setInspectorEnabled(true);
				} else {
					inspectorStateHolder.setInspectorEnabled(true);
				}
			}

			inSplitPaneResized = false;
		}
	}


	/**
	 * Inner class that handles the project changed events
	 * for status bar / source list.
	 */
	private class UpdateAllProjectChangedCallback implements ProjectChangedCallback {
		public void projectChanged(ProjectChangedEvent ev) {
			log.trace("Received project changed callback.");

			Runnable runner = new Runnable() {
				public void run() {
					updateAll();
				}
			};

			SwingUtilities.invokeLater(runner);
		}
	}

	/**
	 * Registers the callbacks with the core
	 */
	private void registerCallbacks() {
		eventCore.addProjectChangedCallbackListener(new UpdateAllProjectChangedCallback());
//		EventCore.getInstance().addProjectChangedCallbackListener(new UpdateAllProjectChangedCallback());
//		eventCore.addContextChangedListener(this);
		EventCore.getInstance().addContextChangedListener(this);

	}


	/**
	 * Public Resource Map
	 *
	 * @return the JakeMainView Resource Map.
	 */
	public static ResourceMap getStaticResouceMap() {
		return mainView.getResourceMap();
	}


	/**
	 * Update the application title to show the project, once it's
	 */
	private void updateTitle() {
		String jakeStr = AppUtilities.getAppName();

		if (JakeContext.getProject() != null && JakeContext.getProject()
				.getInvitationState() == InvitationState.ACCEPTED) {
			String projectPath = JakeContext.getProject().getRootPath();
			getFrame().setTitle(projectPath + " - " + jakeStr);

			// mac only
			if (Platform.isMac()) {
				getFrame().getRootPane().putClientProperty("Window.documentFile",
						new File(JakeContext.getProject().getRootPath()));
			}
		} else {
			getFrame().setTitle(jakeStr);

			// mac only
			if (Platform.isMac()) {
				getFrame().getRootPane().putClientProperty("Window.documentFile", null);
			}
		}
	}



	/**
	 * Called after pressing the toggle buttons for project view.
	 */
	public void setProjectViewFromToolBarButtons() {
		// determine toggle button selection
		if (getContextSwitcherButtons().get(ProjectViewEnum.News.ordinal()).isSelected()) {
			setProjectViewPanel(ProjectViewEnum.News);
		} else if (getContextSwitcherButtons().get(ProjectViewEnum.Files.ordinal())
				.isSelected()) {
			setProjectViewPanel(ProjectViewEnum.Files);
		} else if (getContextSwitcherButtons().get(ProjectViewEnum.Notes.ordinal())
				.isSelected()) {
			setProjectViewPanel(ProjectViewEnum.Notes);
		}
	}

	/**
	 * Changes Menu Bar to be Mac compatible.
	 */
	public static void setMacSystemProperties() {
		System.setProperty("apple.laf.useScreenMenuBar", "true");
	}



	/**
	 * Creates the SplitPane for SourceList and the Main Content Area.
	 *
	 * @return the JSplitPane
	 */
	private JSplitPane createSourceListAndMainArea() {
//		sourceList = new JakeSourceList(eventCore, resourceMap);

		// creates the special SplitPlane
		JSplitPane splitPane = MacWidgetFactory
				.createSplitPaneForSourceList(sourceList.getSourceList(),
						contentPanelSplit);

		// TODO: divider location should be a saved property
		splitPane.setDividerLocation(180);
		splitPane.getLeftComponent().setMinimumSize(new Dimension(150, 150));

		return splitPane;
	}


	/**
	 * Show or hide the inspector panel.
	 * This may not succeed if inspector is not allowed.
	 * Checks isInspectorEnabled property.
	 */
	private void updateInspectorPanelVisibility() {
		//log.debug("pre: isInspectorEnabled: " + isInspectorEnabled() +
		//		  " isInspectorPanelVisible: " + isInspectorPanelVisible() +
		//		  " isInspectorAllowed: " + isInspectorAllowed());
		if (isInspectorEnabled()) {
			// add inspector IF allowed
			if (inspectorStateHolder.isInspectorAllowed() && !inspectorPanel.isVisible()) {
				inspectorPanel.setVisible(true);
				contentPanelSplit.setDividerLocation(contentPanelSplit
						.getWidth() - InspectorPanel.INSPECTOR_SIZE - 1 - contentPanelSplit
						.getDividerSize());
			} else if (!inspectorStateHolder.isInspectorAllowed() ) {
				inspectorPanel.setVisible(false);
			}
		} else {
			if (inspectorPanel.isVisible()) {
				inspectorPanel.setVisible(false);
			}
		}

		// hide divider if not allowed


		if (inspectorStateHolder.isInspectorAllowed()) {
			showDivider();
		} else {
			hideDivider();
		}

		// refresh panel
		contentPanelHolder.getContentPanel().updateUI();

//		log.trace("now: isInspectorEnabled: " + isInspectorEnabled() + " isInspectorPanelVisible: " + inspectorPanel.isVisible() + " isInspectorAllowed: " + isInspectorAllowed());
	}

	private void hideDivider()
	{
		contentPanelSplit.setDividerSize(0);
	}

	private void showDivider()
	{
		contentPanelSplit.setDividerSize(CONTENT_SPLITTERSIZE);

	}


	@Action
	public void showAboutBox() {
		if (aboutBox == null) {
//			JFrame mainFrame = JakeMainApp.getInstance().getMainFrame();
			JFrame mainFrame = jakeMainApp.getMainFrame();
			aboutBox = new JakeAboutDialog(mainFrame);
			aboutBox.setLocationRelativeTo(mainFrame);
		}
//		JakeMainApp.getInstance().show(aboutBox);
		jakeMainApp.show(aboutBox);
	}


	/**
	 * init app
	 */
	private void initComponents() {
//		setMenuBar(menuBar);

		statusPanel = new JPanel();
		statusPanel.setLayout(new java.awt.BorderLayout());
		setStatusBar(statusPanel);
	}

	/**
	 * Updates the window
	 */
	private void updateAll() {
		jakeToolbar.updateToolBar();
		updateTitle();
		updateView();
	}


	/**
	 * Set the Project View Panel.
	 * Only works if the ContextView is set to Project.
	 *
	 * @param view: the project view panel that should be active.
	 */
	public void setProjectViewPanel(ProjectViewEnum view) {
		projectViewHolder.setCurrentView(view);

		updateProjectViewPanel();
		projectViewChangedHolder.fireProjectViewChanged(projectViewHolder.getCurrentView());
	}

	/**
	 * Updates the state of the toogle bottons to keep them in sync with
	 * ProjectViewPanels - state.
	 */
	private void updateProjectToggleButtons() {
		boolean canBeSelected = contextViewPanelHolder.getContextViewPanel() == ContextPanelEnum.Project;
		log.trace("updateProjectToggleButtons. canBeSelected=" + canBeSelected);

		getContextSwitcherButtons().get(ProjectViewEnum.News.ordinal())
				.setSelected(canBeSelected && projectViewHolder.getCurrentView() == ProjectViewEnum.News);
		getContextSwitcherButtons().get(ProjectViewEnum.Files.ordinal())
				.setSelected(canBeSelected && projectViewHolder.getCurrentView() == ProjectViewEnum.Files);
		getContextSwitcherButtons().get(ProjectViewEnum.Notes.ordinal())
				.setSelected(canBeSelected && projectViewHolder.getCurrentView() == ProjectViewEnum.Notes);

		// adapt button style
		for (JToggleButton btn : getContextSwitcherButtons()) {
			Platform.getStyler().styleToolbarButton(btn);
		}
	}

	/**
	 * Updates the Project View, called after setting with setProjectViewPanel
	 */
	public void updateProjectViewPanel() {
		ProjectViewEnum view = projectViewHolder.getCurrentView();

		// only set if project panels are shown!
		boolean show = contextViewPanelHolder.getContextViewPanel() == ContextPanelEnum.Project;

		contentPanelHolder.showContentPanel(newsPanel, show && view == ProjectViewEnum.News);
		contentPanelHolder.showContentPanel(filePanel, show && view == ProjectViewEnum.Files);
		contentPanelHolder.showContentPanel(notesPanel, show && view == ProjectViewEnum.Notes);

		updateProjectToggleButtons();

		// show or hide the inspector
		updateInspectorPanelVisibility();

		updateSourceListVisibility();

		// toolbar changes with viewPort
		jakeToolbar.updateToolBar();
	}

	private void updateSourceListVisibility() {
		log.trace("update sourcelist visible state: visible=" + JakeContext
				.getMsgService());

		if (JakeContext.getMsgService() == null) {
			this.mainSplitPane.getLeftComponent().setVisible(false);
		} else {
			this.mainSplitPane.getLeftComponent().setVisible(true);
			// TODO: save original value
			this.mainSplitPane.setDividerLocation(180);
		}

		this.mainSplitPane.updateUI();
	}


	/**
	 * Called everytime a new project is selected.
	 * Updates the view depending on that selection
	 * Called automatically on setProject()
	 */
	private void updateView() {
		log.trace("updating view");
		Project pr = JakeContext.getProject();
		Invitation invite = JakeContext.getInvitation();

		boolean showLogin = pr == null && invite == null;

		// determine what to show
		if (showLogin) {
			contextViewPanelHolder.setContextViewPanel(ContextPanelEnum.Login);
		} else if (invite != null) {
			contextViewPanelHolder.setContextViewPanel(ContextPanelEnum.Invitation);
		} else {
			contextViewPanelHolder.setContextViewPanel(ContextPanelEnum.Project);
		}
		updateProjectViewPanel();
		contentPanelHolder.getContentPanel().updateUI();
	}


	public static JakeMainView getMainView() {
		return mainView;
	}

	private static void setMainView(JakeMainView mainView) {
		JakeMainView.mainView = mainView;
	}


	@Action
	public void hideApplicationAction() {
		getFrame().setVisible(false);
	}


	@Action
	public static void showJakeWebsite() {
		JakeHelper.showJakeWebsite();
	}




	public void addContextViewChangedListener(ContextViewChangedCallback pvc) {
		contextViewChangedHolder.addContextViewChangedListener(pvc);
	}

	public void removeContextViewChangedListener(ContextViewChangedCallback pvc) {
		contextViewChangedHolder.getContextViewChanged().remove(pvc);
	}

	public void quit() {
		app.saveQuit();
	}

	public static boolean isMainWindowVisible() {
		return JakeMainView.getMainView().getFrame().isVisible();
	}

	public static void setMainWindowVisible(boolean visible) {
		JakeMainView.getMainView().getFrame().setVisible(visible);
		JakeMainView.getMainView().getFrame().toFront();
		//JakeMainView.getMainView().getFrame().requestFocus();
	}

	public static void toggleShowHideMainWindow() {
		if (!isMainWindowVisible()) {
			//JakeMainView.getMainView().getFrame().setExtendedState(JFrame.ICONIFIED);
		}
		JakeMainView.getMainView().getFrame().setVisible(!isMainWindowVisible());
		if (isMainWindowVisible()) {
			//JakeMainView.getMainView().getFrame().requestFocus();
			//JakeMainView.getMainView().getFrame().setExtendedState(JFrame.NORMAL);
		}
	}


	// getters and setters

	public JPanel getInspectorPanel() {
		return inspectorPanel;
	}

	public void setInspectorPanel(JPanel inspectorPanel) {
		this.inspectorPanel = inspectorPanel;
	}

	public InvitationPanel getInvitationPanel() {
		return invitationPanel;
	}

	public UserPanel getLoginPanel() {
		return loginPanel;
	}

	public NewsPanel getNewsPanel() {
		return newsPanel;
	}

}
