package com.jakeapp.gui.swing.view;

import com.jakeapp.gui.swing.model.MainWindowModel;
import com.jakeapp.gui.swing.controller.MainWindowController;
import com.jakeapp.gui.swing.JakeMainApp;

import java.util.Observer;
import java.util.Observable;
import java.awt.*;

import org.jdesktop.application.FrameView;
import org.apache.log4j.Logger;

import javax.swing.*;


public class MainWindowView extends FrameView implements Observer {
	private static Logger log = Logger.getLogger(MainWindowView.class);

	private final MainWindowModel model;
	private final MainWindowController controller;
	private final JakeMainApp app;
	private final ToolbarView toolbarView;
	private final StatusBarView statusBarView;

	private JPanel mainView = new JPanel(new BorderLayout());

	private final ContentView contentView;

	public MainWindowView(JakeMainApp app, MainWindowModel model, MainWindowController controller,
						  ContentView contentView,
						  ToolbarView toolbarView, StatusBarView statusBarView) {
		super(app);
		this.app = app;
		this.model = model;
		this.controller = controller;
		this.contentView = contentView;
		this.toolbarView = toolbarView;
		this.statusBarView = statusBarView;

		System.out.println("created mainWindowView");


		model.addObserver(this);
		System.out.println("added observer");


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




		// initialize view dimension properties
		model.setMinimumHeight(600);
		model.setMinimumWidth(600);
		model.setCurrentHeight(800);
		model.setCurrentWidth(800);


		this.setComponent(mainView);



	}

	@Override
	public void update(Observable o, Object arg) {

		System.out.println("o = " + o);
		System.out.println("arg = " + arg);


		updateUI();
		//To change body of implemented methods use File | Settings | File Templates.
	}

	private void updateUI()
	{

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


		mainView.removeAll();

		if(model.isShowToolbar())
			mainView.add(toolbarView, BorderLayout.NORTH);

		mainView.add(contentView, BorderLayout.CENTER);

		if(model.isShowStatusbar())
			mainView.add(statusBarView, BorderLayout.SOUTH);


		mainView.validate();
	}





}
