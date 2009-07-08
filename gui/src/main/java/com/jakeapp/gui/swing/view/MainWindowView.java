package com.jakeapp.gui.swing.view;

import com.jakeapp.gui.swing.model.MainWindowViewModel;
import com.jakeapp.gui.swing.model.MainWindowViewModelEnum;
import com.jakeapp.gui.swing.controller.MainWindowViewController;
import com.jakeapp.gui.swing.JakeMainApp;
import com.jakeapp.gui.swing.view.menuBar.MenuBarView;

import java.util.Observer;
import java.util.Observable;
import java.awt.*;

import org.jdesktop.application.FrameView;
import org.apache.log4j.Logger;

import javax.swing.*;


public class MainWindowView extends FrameView implements Observer {
	private static Logger log = Logger.getLogger(MainWindowView.class);

	private final MainWindowViewModel model;
	private final MainWindowViewController controller;
	private final JakeMainApp app;
	private final ToolbarView toolbarView;
	private final StatusBarView statusBarView;
	private final MenuBarView menuBarView;

	private JPanel mainView = new JPanel(new BorderLayout());

	private final ContentView contentView;

	public MainWindowView(JakeMainApp app, MainWindowViewModel model, MainWindowViewController controller,
						  ContentView contentView,
						  ToolbarView toolbarView, StatusBarView statusBarView, MenuBarView menuBarView) {
		super(app);
		this.app = app;
		this.model = model;
		this.controller = controller;
		this.contentView = contentView;
		this.toolbarView = toolbarView;
		this.statusBarView = statusBarView;
		this.menuBarView = menuBarView;

		this.setMenuBar(menuBarView);
		this.setStatusBar(statusBarView);

		mainView.add(toolbarView, BorderLayout.NORTH);

		mainView.add(contentView, BorderLayout.CENTER);

		mainView.setVisible(true);

		this.setComponent(mainView);


		// initialize icons
//		private Image IconAppSmall;
//		private Image IconAppLarge;
//		this.getFrame().setIconImage(IconAppSmall);
//		this.getFrame().setIconImages(Arrays.asList(IconAppSmall, IconAppLarge));


		// adapt the menu if we live on a mac
//		if (Platform.isMac()) {
		// install the close handler (meta-w)
//			GuiUtilities.installMacCloseHandler(getFrame());
//		}



		model.addObserver(this);

		// initialize view dimension properties


		// TODO
		model.setMinimumHeight(600);
		model.setMinimumWidth(600);
		model.setCurrentHeight(800);
		model.setCurrentWidth(800);
		model.setShowMenubar(true);
		model.setShowStatusbar(true);
		model.setShowToolbar(true);


		// TODO thats a hack
		model.setShowMenubar(model.isShowMenubar());
		model.setShowStatusbar(model.isShowStatusbar());
		model.setShowToolbar(model.isShowToolbar());
		model.setCurrentView(model.getCurrentView());


//		model.setCurrentView(ViewEnum.PROJECT_EVENTS);




	}

	@Override
	public void update(Observable o, Object arg) {

		if (o instanceof MainWindowViewModel && arg instanceof MainWindowViewModelEnum) {
			MainWindowViewModelEnum changed = (MainWindowViewModelEnum) arg;


			switch (changed) {

				case currentHeight:
					this.getFrame().setSize(this.getFrame().getWidth(), model.getCurrentHeight());
					break;
				case currentView:
					// this gets handled by child components
					break;
				case currentWidth:
					this.getFrame().setSize(model.getCurrentWidth(), this.getFrame().getHeight());
					break;
				case minimumHeight:
					this.getFrame().setMinimumSize(new Dimension(model.getCurrentWidth(), model.getCurrentHeight()));
					break;
				case minimumWidth:
					this.getFrame().setMinimumSize(new Dimension(model.getCurrentWidth(), model.getCurrentHeight()));
					break;
				case showMainWindow:
					this.getFrame().setVisible(model.isShowMainWindow());
					break;
				case showMenubar:
					if(this.getMenuBar() != null)
						this.getMenuBar().setVisible(model.isShowMenubar());
					break;
				case showStatusbar:
					if(this.getStatusBar() != null)
						this.getStatusBar().setVisible(model.isShowStatusbar());
					break;
				case showToolbar:
					this.toolbarView.setVisible(model.isShowToolbar());
					break;
				case windowTitle:
					this.getFrame().setTitle(model.getWindowTitle());
					break;
			}


		}

		if (!(o instanceof MainWindowViewModel)) {
			new Exception("Unhandled observer invocation, please fix me").printStackTrace();
			System.exit(1);
		}


		System.out.println("o = " + o);
		System.out.println("arg = " + arg);


		 this.getFrame().validate();
//		updateUI();
		//To change body of implemented methods use File | Settings | File Templates.
	}

	private void reloadUIfromModel()
	{
		this.getFrame().setSize(model.getCurrentWidth(), model.getCurrentHeight());


	}


	private void initializeUI() {

		if (!model.isShowMainWindow()) {
			this.getFrame().setVisible(false);
			return;
		}

		// this gets executed if the area should be visible

		this.getFrame().setVisible(true);
		this.getFrame().toFront();


		// set dimensions
		this.getFrame().setMinimumSize(new Dimension(model.getMinimumWidth(), model.getMinimumHeight()));
		this.getFrame().setSize(model.getCurrentWidth(), model.getCurrentHeight());

		// set window title
		this.getFrame().setTitle(model.getWindowTitle());

		this.setComponent(mainView);

		mainView.setBackground(Color.YELLOW);

//		mainView.removeAll();

//		if (model.isShowToolbar())

		mainView.add(toolbarView, BorderLayout.NORTH);

		mainView.add(contentView, BorderLayout.CENTER);

//		if (model.isShowStatusbar())
			mainView.add(statusBarView, BorderLayout.SOUTH);


		mainView.validate();
		mainView.updateUI();
	}


}
