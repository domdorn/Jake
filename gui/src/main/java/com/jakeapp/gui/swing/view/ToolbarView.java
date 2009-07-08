package com.jakeapp.gui.swing.view;

import com.explodingpixels.macwidgets.LabeledComponentGroup;
import com.explodingpixels.macwidgets.MacButtonFactory;
import com.explodingpixels.macwidgets.MacWidgetFactory;
import com.explodingpixels.macwidgets.TriAreaComponent;
import com.jakeapp.gui.swing.controller.ToolbarController;
import com.jakeapp.gui.swing.controls.SearchField;
import com.jakeapp.gui.swing.helpers.ImageLoader;
import com.jakeapp.gui.swing.helpers.Platform;
import com.jakeapp.gui.swing.helpers.SegmentButtonCreator;
import com.jakeapp.gui.swing.model.ToolbarModel;
import org.apache.log4j.Logger;
import org.jdesktop.application.ResourceMap;
import org.jdesktop.swingx.JXPanel;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;


public class ToolbarView extends JPanel implements Observer {
	private static Logger log = Logger.getLogger(ToolbarView.class);

	private final ToolbarModel model;
	private final ToolbarController controller;
	private final ResourceMap resourceMap;


	private final TriAreaComponent toolbar;

	private SearchField searchField;
	private AbstractButton createProjectButton;
	private AbstractButton addFilesButton;
	private AbstractButton invitePeopleButton;
	private AbstractButton inspectorButton;

	JPanel contextSwitcherPane;



	public ToolbarView(ToolbarModel model, ToolbarController controller, ResourceMap resourceMap) {
		super(new BorderLayout());

		this.model = model;
		this.controller = controller;
		this.resourceMap = resourceMap;

		this.setBackground(new Color(255, 0, 0)); // get this from model
		contextSwitcherPane = createContextSwitcherPanel();

		this.toolbar = createToolBar();
		this.add(toolbar.getComponent(), BorderLayout.CENTER);
		
		this.validate();
	}

	@Override
	public void update(Observable o, Object arg) {
		//To change body of implemented methods use File | Settings | File Templates.
	}


			/**
	 * Context Switcher Panel
	 *
	 * @return the context switcher panel
	 */
	private JPanel createContextSwitcherPanel() {
		JXPanel switcherPanel = new JXPanel();
		switcherPanel.setOpaque(false);



//		class ContextSwitchActionListener implements ActionListener {
//			public void actionPerformed(ActionEvent event) {
//				setProjectViewFromToolBarButtons();
//			}
//		}

//		ContextSwitchActionListener cslistener = new ContextSwitchActionListener();

//		getContextSwitcherButtons().get(0).addActionListener(cslistener);
//		getContextSwitcherButtons().get(1).addActionListener(cslistener);
//		getContextSwitcherButtons().get(2).addActionListener(cslistener);

		JPanel flowButtons = new JPanel();
		flowButtons.setOpaque(false);
		flowButtons.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
		switcherPanel.setLayout(new BorderLayout());
		switcherPanel.add(flowButtons, BorderLayout.CENTER);

		for (JToggleButton button : contextSwitcherButtons) {
			flowButtons.add(button);
		}

		return switcherPanel;
	}



	/**
	 * Creates the unified toolbar on top.
	 *
	 * @return TriAreaComponent of toolbar.
	 */
	public TriAreaComponent createToolBar() {
		// create empty toolbar
		TriAreaComponent toolBar = MacWidgetFactory.createUnifiedToolBar();

		// create project
		JButton createProjectJButton = new JButton();
		createProjectButton = MacButtonFactory.makeUnifiedToolBarButton(createProjectJButton);
		createProjectButton.setEnabled(true);
		createProjectButton.setBorder(new LineBorder(Color.BLACK, 0));

		// add files
		// Add PROJECT_FILES
		Icon addFilesIcon = ImageLoader.getScaled(MainWindow.class,"/icons/toolbar-addfiles.png", 32);
		JButton createAddFilesJButton = new JButton(resourceMap.getString("toolbarAddFiles"),addFilesIcon);
		addFilesButton = MacButtonFactory.makeUnifiedToolBarButton(createAddFilesJButton);
		addFilesButton.setEnabled(true);
		createAddFilesJButton.setBorder(new LineBorder(Color.BLACK, 0));

		// invitations
		JButton invitePeopleJButton = new JButton();

		invitePeopleButton = MacButtonFactory.makeUnifiedToolBarButton(invitePeopleJButton);
		invitePeopleButton.setBorder(new LineBorder(Color.BLACK, 0));
		invitePeopleButton.setEnabled(true);

		// inspector
		Icon inspectorIcon = ImageLoader.getScaled(MainWindow.class, "/icons/inspector.png", 32);
		JButton inspectorJButton = new JButton("Inspector", inspectorIcon);
		inspectorButton = MacButtonFactory.makeUnifiedToolBarButton(inspectorJButton);
		inspectorButton.setEnabled(true);
		inspectorJButton.setBorder(new LineBorder(Color.BLACK, 0));


		// search box
		searchField = new SearchField();
		searchField.setSize(searchField.getHeight(), 30);
		searchField.putClientProperty("JTextField.variant", "search");
		searchField.setSendsNotificationForEachKeystroke(true);




		// adding actions
		createProjectJButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				controller.createProject();
			}
		});

		addFilesButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent event) {
				controller.createImportFileDialog();
			}
		});


		invitePeopleJButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e) {
				controller.invitePeople();
			}
		});

		inspectorJButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent event) {
				controller.toggleInspector();
			}
		});


		searchField.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String pattern = e.getActionCommand();
				controller.searchObjects(pattern);
			}
		});





		// adding to toolbar
		toolBar.addComponentToLeft(createProjectButton, 10);
		toolBar.addComponentToLeft(addFilesButton, 10);
//		toolBar.addComponentToLeft(addFilesButton, 10);
		toolBar.addComponentToLeft(invitePeopleButton, 10);
		toolBar.addComponentToRight(inspectorButton, 10);
		toolBar.addComponentToRight(new LabeledComponentGroup("Search", searchField).getComponent());


		/*
		// Create Note
		Icon noteIcon = new ImageIcon(Toolkit.getDefaultToolkit().getImage(
				  getClass().getResource("/icons/notes.png")).getScaledInstance(32, 32, Image.SCALE_SMOOTH));
		JButton jCreateNodeButton = new JButton(getResourceMap().getString("toolbarCreateNote"), noteIcon);

		addFilesButton = MacButtonFactory.makeUnifiedToolBarButton(jCreateNodeButton);
		addFilesButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent event) {
				addFilesAction();
			}
		});
		addFilesButton.setEnabled(true);
		jCreateNodeButton.setBorder(new LineBorder(Color.BLACK, 0));
		*/





		/*
			 // Announce File
			 Icon announceIcon = new ImageIcon(Toolkit.getDefaultToolkit().getImage(
						getClass().getResourceMap("/icons/announce.png")).getScaledInstance(32, 32, Image.SCALE_SMOOTH));
			 JButton announceJButton = new JButton("Announce", announceIcon);


			 AbstractButton announceButton =
						MacButtonFactory.makeUnifiedToolBarButton(announceJButton);

			 announceButton.setEnabled(true);
			 announceJButton.setBorder(new LineBorder(Color.BLACK, 0));
			 toolBar.addComponentToRight(announceButton, 10);

			 // Pull File
			 Icon pullIcon = new ImageIcon(Toolkit.getDefaultToolkit().getImage(
						getClass().getResourceMap("/icons/pull.png")).getScaledInstance(32, 32, Image.SCALE_SMOOTH));

			 JButton jPullButton = new JButton("Pull", pullIcon);
			 AbstractButton pullButton =
						MacButtonFactory.makeUnifiedToolBarButton(jPullButton);
			 pullButton.setEnabled(true);
			 jPullButton.setBorder(new LineBorder(Color.BLACK, 0));

			 toolBar.addComponentToRight(pullButton, 10);
  */
		/*
					// Lock File
					Icon lockIcon = new ImageIcon(Toolkit.getDefaultToolkit().getImage(
									getClass().getResourceMap("/icons/lock.png")).getScaledInstance(32, 32, Image.SCALE_SMOOTH));
					JButton jLockButton = new JButton("Lock File", lockIcon);
					AbstractButton lockButton =
									MacButtonFactory.makeUnifiedToolBarButton(
													jLockButton);
					lockButton.setEnabled(false);
					jLockButton.setBorder(new LineBorder(Color.BLACK, 0));
					toolBar.addComponentToRight(lockButton, 10);
	*/



		// The mighty Inspector


		//announceButton.setBackground(Color.);

		/*
				  JButton annouceButton = new JButton("Announce");
				  annouceButton.putClientProperty("JButton.buttonType", "textured");
					*/
		//toolBar.addComponentToLeft(announceButton);
		//toolBar.add(announceButton);



//		final FilePanel filePanel = jakeMainView.getFilePanel();


		toolBar.addComponentToCenter(new LabeledComponentGroup("View", contextSwitcherPane).getComponent());

		controller.installWindowDragger(toolBar);

		updateToolBar();
		return toolBar;
	}

	/**
	 * Enables/disables the toolbar depending on current dataset
	 */
	public void updateToolBar() {


		boolean hasProject = model.isProjectSet();
		boolean isInvite = model.isInviteSet();

//		boolean isProjectContext = contextViewPanelHolder.getContextViewPanel() == ContextPanelEnum.Project;
		boolean isProjectContext = true; // TODO!
		boolean hasUser = model.isMsgServiceSet();

		createProjectButton.setEnabled(hasUser);
		addFilesButton.setEnabled(hasProject && !isInvite);
		invitePeopleButton.setEnabled(hasProject && !isInvite);
		for (JToggleButton btn : contextSwitcherButtons) {
			btn.setEnabled(isProjectContext && hasProject && !isInvite);
		}
		inspectorButton.setEnabled(model.isInspectorAllowed());
		searchField.setEditable(hasProject);
	}


	private ButtonGroup switcherGroup = new ButtonGroup();
	private java.util.List<JToggleButton> contextSwitcherButtons = SegmentButtonCreator.createSegmentedTexturedButtons(3, switcherGroup);


	private void createContextSwitcherButtons() {
		contextSwitcherButtons.get(0).setText("Project");
		contextSwitcherButtons.get(1).setText("PROJECT_FILES");
		contextSwitcherButtons.get(2).setText("Notes");

		contextSwitcherButtons.get(0).addActionListener(contextSwitcherButtonActionListener);
		contextSwitcherButtons.get(1).addActionListener(contextSwitcherButtonActionListener);
		contextSwitcherButtons.get(2).addActionListener(contextSwitcherButtonActionListener);

	}


	private ActionListener contextSwitcherButtonActionListener = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {

			// determine toggle button selection
			if (contextSwitcherButtons.get(ViewEnum.PROJECT_EVENTS.ordinal()).isSelected()) {
				controller.changeView(ViewEnum.PROJECT_EVENTS);
			} else if (contextSwitcherButtons.get(ViewEnum.PROJECT_FILES.ordinal()).isSelected()) {
				controller.changeView(ViewEnum.PROJECT_FILES);
			} else if (contextSwitcherButtons.get(ViewEnum.PROJECT_NOTES.ordinal()).isSelected()) {
				controller.changeView(ViewEnum.PROJECT_NOTES);
			}
		}
	};


	/**
	 * Updates the state of the toogle bottons to keep them in sync with
	 * ProjectViewPanels - state.
	 */
	public void updateProjectToggleButtons(boolean canBeSelected) {

		log.trace("updateProjectToggleButtons. canBeSelected=" + canBeSelected);

		if (canBeSelected) {
			contextSwitcherButtons.get(ViewEnum.PROJECT_EVENTS.ordinal())
					.setSelected(controller.getCurrentView().equals(ViewEnum.PROJECT_EVENTS));

			contextSwitcherButtons.get(ViewEnum.PROJECT_FILES.ordinal())
					.setSelected(controller.getCurrentView().equals(ViewEnum.PROJECT_FILES));
			contextSwitcherButtons.get(ViewEnum.PROJECT_NOTES.ordinal())
					.setSelected(controller.getCurrentView().equals(ViewEnum.PROJECT_NOTES));
		}

		// adapt button style
		for (JToggleButton btn : contextSwitcherButtons) {
			Platform.getStyler().styleToolbarButton(btn);
		}
	}


}
