package com.jakeapp.gui.swing.model;

import java.util.Observable;


public class MainWindowModel extends Observable {


	/**
	 * Toggle displaying of main window
	 */
	private boolean showMainWindow = false;

	private boolean showToolbar = false;

	private boolean showStatusbar = false;

	private boolean showMenubar = false;

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

	public MainWindowModel() {
	}


	public boolean isShowMainWindow() {
		return showMainWindow;
	}

	public void setShowMainWindow(boolean showMainWindow) {
		this.showMainWindow = showMainWindow;
		setChanged();
		notifyObservers();
	}


	public int getMinimumWidth() {
		return minimumWidth;
	}

	public void setMinimumWidth(int minimumWidth) {
		this.minimumWidth = minimumWidth;
	}

	public int getMinimumHeight() {
		return minimumHeight;
	}

	public void setMinimumHeight(int minimumHeight) {
		this.minimumHeight = minimumHeight;
	}

	public int getCurrentWidth() {
		return currentWidth;
	}

	public void setCurrentWidth(int currentWidth) {
		this.currentWidth = currentWidth;
	}

	public int getCurrentHeight() {
		return currentHeight;
	}

	public void setCurrentHeight(int currentHeight) {
		this.currentHeight = currentHeight;
	}


	public String getWindowTitle() {
		return windowTitle;
	}

	public void setWindowTitle(String windowTitle) {
		this.windowTitle = windowTitle;
	}


	public boolean isShowToolbar() {
		return showToolbar;
	}

	public void setShowToolbar(boolean showToolbar) {
		this.showToolbar = showToolbar;
	}

	public boolean isShowStatusbar() {
		return showStatusbar;
	}

	public void setShowStatusbar(boolean showStatusbar) {
		this.showStatusbar = showStatusbar;
	}

	public boolean isShowMenubar() {
		return showMenubar;
	}

	public void setShowMenubar(boolean showMenubar) {
		this.showMenubar = showMenubar;
	}
}
