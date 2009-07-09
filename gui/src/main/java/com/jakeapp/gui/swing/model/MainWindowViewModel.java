package com.jakeapp.gui.swing.model;

import com.jakeapp.gui.swing.view.ViewEnum;

import java.util.Observable;


public class MainWindowViewModel extends Observable {


	/**
	 * Toggle displaying of main window
	 */
	private boolean showMainWindow = false;

	private boolean showToolbar = true;

	private boolean showStatusbar = false;

	private boolean showMenubar = false;


	private boolean coreInitialized;

	private ViewEnum currentView;

	/**
	 * Defining minimum dimensions of the mainWindow
	 */
	private int minimumWidth;
	private int minimumHeight;


	/**
	 * Storing current dimensions of the mainWindow
	 */
	private int currentWidth;
	private int currentHeight;

	/**
	 * Storing the title of the mainWindow
	 */
	private String windowTitle;

	public MainWindowViewModel() {
	}


	public boolean isShowMainWindow() {
		return showMainWindow;
	}

	public void setShowMainWindow(boolean showMainWindow) {
		this.showMainWindow = showMainWindow;
		setChanged();
		notifyObservers(MainWindowViewModelEnum.showMainWindow);
	}


	public int getMinimumWidth() {
		return minimumWidth;
	}

	public void setMinimumWidth(int minimumWidth) {
		this.minimumWidth = minimumWidth;
		setChanged();
		notifyObservers(MainWindowViewModelEnum.minimumWidth);
	}

	public int getMinimumHeight() {
		return minimumHeight;
	}

	public void setMinimumHeight(int minimumHeight) {
		this.minimumHeight = minimumHeight;
		setChanged();
		notifyObservers(MainWindowViewModelEnum.minimumHeight);
	}

	public int getCurrentWidth() {
		return currentWidth;
	}

	public void setCurrentWidth(int currentWidth) {
		this.currentWidth = currentWidth;
		setChanged();
		notifyObservers(MainWindowViewModelEnum.currentWidth);
	}

	public int getCurrentHeight() {
		return currentHeight;
	}

	public void setCurrentHeight(int currentHeight) {
		this.currentHeight = currentHeight;
		setChanged();
		notifyObservers(MainWindowViewModelEnum.currentHeight);
	}


	public String getWindowTitle() {
		return windowTitle;
	}

	public void setWindowTitle(String windowTitle) {
		this.windowTitle = windowTitle;
		setChanged();
		notifyObservers(MainWindowViewModelEnum.windowTitle);
	}


	public boolean isShowToolbar() {
		return showToolbar;
	}

	public void setShowToolbar(boolean showToolbar) {
		this.showToolbar = showToolbar;
		setChanged();
		notifyObservers(MainWindowViewModelEnum.showToolbar);
	}

	public boolean isShowStatusbar() {
		return showStatusbar;
	}

	public void setShowStatusbar(boolean showStatusbar) {
		this.showStatusbar = showStatusbar;
		setChanged();
		notifyObservers(MainWindowViewModelEnum.showStatusbar);
	}

	public boolean isShowMenubar() {
		return showMenubar;
	}

	public void setShowMenubar(boolean showMenubar) {
		this.showMenubar = showMenubar;
		setChanged();
		notifyObservers(MainWindowViewModelEnum.showMenubar);
	}

	public ViewEnum getCurrentView() {
		return currentView;
	}

	public void setCurrentView(ViewEnum currentView) {
		this.currentView = currentView;
		setChanged();
		notifyObservers(MainWindowViewModelEnum.currentView);
	}

	public boolean isCoreInitialized() {
		return coreInitialized;
	}

	public void setCoreInitialized(boolean coreInitialized) {
		this.coreInitialized = coreInitialized;
		setChanged();
		notifyObservers(MainWindowViewModelEnum.coreInitialized);
	}
}
