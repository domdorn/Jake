package com.jakeapp.gui.swing.view;

import com.jakeapp.gui.swing.model.StatusBarModel;
import com.jakeapp.gui.swing.model.StatusBarModelEnum;
import com.jakeapp.gui.swing.controller.StatusBarController;
import com.jakeapp.gui.swing.helpers.*;
import com.jakeapp.gui.swing.controls.SpinningWheelComponent;
import com.jakeapp.gui.swing.controls.SpinningDial;
import com.jakeapp.gui.swing.globals.JakeContext;
import com.jakeapp.gui.swing.JakeMainApp;
import com.jakeapp.gui.swing.ContextPanelEnum;
import com.jakeapp.jake.ics.status.ILoginStateListener;
import com.explodingpixels.macwidgets.TriAreaComponent;
import com.explodingpixels.macwidgets.BottomBarSize;
import com.explodingpixels.macwidgets.MacWidgetFactory;

import javax.swing.*;
import java.util.Observer;
import java.util.Observable;
import java.util.Date;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import org.apache.log4j.Logger;
import org.jdesktop.application.ResourceMap;


public class StatusBarView extends JPanel implements Observer {
	private static Logger log = Logger.getLogger(StatusBarView.class);

	private final StatusBarModel model;
	private final StatusBarController controller;
	private final ResourceMap resourceMap;


	private TriAreaComponent statusBar;
	private JLabel statusLabel = MacWidgetFactory.createEmphasizedLabel("statusLabel");
	private SpinningWheelComponent progressDrawer = new SpinningWheelComponent();
	private JLabel progressMsg;
	private JButton connectionButton = new JButton();
	private Icon chooseUserIcon = ImageLoader.get(getClass(), "/icons/login.png");
	Icon spinningDial = new SpinningDial(16, 16);

	public StatusBarView(StatusBarModel model, StatusBarController controller, ResourceMap resourceMap) {
		super(new BorderLayout());
		this.model = model;
		this.controller = controller;
		this.resourceMap = resourceMap;

		this.setBackground(new Color(0, 255, 0)); // get this from model
		this.statusBar = createStatusBar();


		this.add(statusBar.getComponent(), BorderLayout.CENTER);




		this.model.addObserver(this);

		updateConnectionDisplay();
		animateProgressDrawer();
		this.validate();
		this.updateUI();

	}


	public ResourceMap getResourceMap() {
		return resourceMap;
	}

	@Override
	public void update(Observable o, Object arg) {

		if(o instanceof StatusBarModel && arg instanceof StatusBarModelEnum)
		{
			StatusBarModelEnum changed = (StatusBarModelEnum) arg;

			switch (changed) {

				case connectionState:

					break;
				case lastConnectionMsg:
					break;
				case loggedInUsername:
					break;
				case messageServiceSet:
					break;
				case operationInProgress:
					break;
				case statusMessage:
					break;
			}
		}


		//To change body of implemented methods use File | Settings | File Templates.

		updateConnectionDisplay();
		animateProgressDrawer();
		this.validate();
		this.updateUI();
		
	}


	/**
	 * Create status bar code
	 *
	 * @return TriAreaComponent of status bar.
	 */
	private TriAreaComponent createStatusBar() {
		log.trace("creating status bar...");

		// only draw the 'small' statusbar if we are in on win. mac and linux want it fat! ;o)
		BottomBarSize bottombarSize =
				Platform.isWin() ? BottomBarSize.SMALL : BottomBarSize.LARGE;

		TriAreaComponent bottomBar = MacWidgetFactory.createBottomBar(bottombarSize);

		// make status label 2 px smaller
		statusLabel.setFont(statusLabel.getFont().deriveFont(statusLabel.getFont().getSize() - 2f));

		bottomBar.addComponentToCenter(statusLabel);
		controller.installWindowDragger(bottomBar);

		progressDrawer.setVisible(false);
		bottomBar.addComponentToLeft(progressDrawer, 3);

		// FIXME: remove after release
		progressMsg = MacWidgetFactory.createEmphasizedLabel("");
		progressMsg.setFont(
				progressMsg.getFont().deriveFont(progressMsg.getFont().getSize() - 2f));
		bottomBar.addComponentToLeft(progressMsg, 3);

		// connection info
		connectionButton.setIcon(chooseUserIcon);
		connectionButton.setHorizontalTextPosition(SwingConstants.LEFT);
		connectionButton.putClientProperty("JComponent.sizeVariant", "small");
		connectionButton.putClientProperty("JButton.buttonType", "textured");
		if (!Platform.isMac()) {
			connectionButton.setFont(connectionButton.getFont().deriveFont(
					connectionButton.getFont().getSize() - 2f));
		}

		connectionButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent event) {
				JPopupMenu menu = createConnectionMenu();

				// calculate contextmenu directly above signin-status button
				menu.show((JButton) event.getSource(), ((JButton) event.getSource()).getX(),
						((JButton) event.getSource()).getY() - 20);
			}
		});
		bottomBar.addComponentToRight(connectionButton);
		return bottomBar;
	}


	private String getPrettyConnectionState(ILoginStateListener.ConnectionState connectionState) {
		switch (connectionState) {
			case LOGGED_IN:
				return "Connected";
			case LOGGED_OUT:
				return "Offline";
			case CONNECTING:
				return "Connecting";
			case INVALID_CREDENTIALS:
				return "Invalid User/Pass";
			default:
				return "Unknown Error";
		}
	}


	/**
	 * One-Time-Init of the Connection Menu
	 *
	 * @return
	 */
	private JPopupMenu createConnectionMenu() {
		JakePopupMenu connectionMenu = new JakePopupMenu();

		JMenuItem signInOut = new JMenuItem(getResourceMap().getString(model.isMessageServiceSet() ? "menuSignOut" : "menuSignIn"));

		signInOut.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent actionEvent) {
				if (model.isMessageServiceSet()) {
					controller.logoutUser();
				} else {
					controller.loginUser();
				}
			}
		});
		connectionMenu.add(signInOut);

		JMenuItem chooseUser = new JMenuItem("Choose User...");
		chooseUser.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					controller.logoutUser();
				} catch (Exception ex) {
					ExceptionUtilities.showError(ex);
				}

			}
		});

		connectionMenu.add(chooseUser);
		return connectionMenu;
	}


	enum IconEnum {
		Offline, LoggingIn, Online
	}

	/**
	 * Updates the connection Button with new credentals informations.
	 */
	private void updateConnectionDisplay() {
		String msg;
		IconEnum icon = IconEnum.Offline;

		if (model.isMessageServiceSet()) {


			String user = model.getLoggedInUsername();

			ILoginStateListener.ConnectionState connectionState = model.getConnectionState();

			msg = getPrettyConnectionState(connectionState) + " - " + user;

			if (connectionState == ILoginStateListener.ConnectionState.CONNECTING) {
				icon = IconEnum.LoggingIn;
			} else if (connectionState == ILoginStateListener.ConnectionState.LOGGED_IN) {
				icon = IconEnum.Online;
			}
			connectionButton.setVisible(true);
		} else {
			msg = getResourceMap().getString("statusLoginNotSignedIn");
			connectionButton.setVisible(false);
		}

		statusLabel = MacWidgetFactory.createEmphasizedLabel(StringUtilities.htmlize(model.getStatusMessage()));
//		statusLabel.setText(StringUtilities.htmlize(model.getStatusMessage()));
//				"newLabelText " + new Date());

		connectionButton.setText(msg);
		connectionButton.setIcon(getConnectionIcon(icon));

		connectionButton.setToolTipText(model.getLastConnectionMsg());
	}


	/**
	 * Get the Icon corresponding to the Connection State.
	 *
	 * @param icon
	 * @return
	 */
	private Icon getConnectionIcon(IconEnum icon) {
		switch (icon) {
			case Offline:
				return chooseUserIcon;
			case LoggingIn:
				return spinningDial;
			case Online:
				return chooseUserIcon;
			default:
				return null;
		}
	}


	public void animateProgressDrawer() {
		if (model.isOperationInProgress()) {
			progressDrawer.setVisible(true);
			progressDrawer.startAnimation();
		} else {
			progressDrawer.setVisible(false);
			progressDrawer.stopAnimation();
		}
	}

}