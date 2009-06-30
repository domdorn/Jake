package com.jakeapp.gui.swing.controls;

import com.jakeapp.gui.swing.JakeMainApp;

import javax.swing.*;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;

/**
 * The JXTrayIcon class from swinghelper
 * https://swinghelper.dev.java.net/
 * <p/>
 * Supports displaying of a JPopupMenu insted of the plain' old awt menu.
 */
// TODO: Use this on win, lin, stick with awt on mac (looks perfect there)
public class JXTrayIcon extends TrayIcon {
	private JPopupMenu menu;
	private static JDialog dialog;

	static {
		dialog = new JDialog((Frame) null);
		dialog.setUndecorated(true);
		dialog.setAlwaysOnTop(true);
	}

	private static PopupMenuListener popupListener = new PopupMenuListener() {
		public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
		}

		public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
			dialog.setVisible(false);
		}

		public void popupMenuCanceled(PopupMenuEvent e) {
			dialog.setVisible(false);
		}
	};


	public JXTrayIcon(Image image) {
		super(image);
		addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				showJPopupMenu(e);
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				showJPopupMenu(e);
			}
		});
	}

	protected void showJPopupMenu(MouseEvent e) {
		if (e.isPopupTrigger() && menu != null) {
			Dimension size = menu.getPreferredSize();
			showJPopupMenu(e.getX(), e.getY() - size.height);
		}
	}

	protected void showJPopupMenu(int x, int y) {
		dialog.setLocation(x, y);
		dialog.setVisible(true);
		menu.show(dialog.getContentPane(), 0, 0);
		// popup works only for focused windows
		dialog.toFront();
	}

	public JPopupMenu getJPopupMenu() {
		return menu;
	}

	public void setJPopupMenu(JPopupMenu menu) {
		if (this.menu != null) {
			this.menu.removePopupMenuListener(popupListener);
		}
		this.menu = menu;
		menu.addPopupMenuListener(popupListener);
	}

	private static void createGui() {
		JXTrayIcon tray = new JXTrayIcon(createImage());
		tray.setJPopupMenu(createJPopupMenu());
		try {
			SystemTray.getSystemTray().add(tray);
		} catch (AWTException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws Exception {

		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				createGui();
			}
		});
	}

	static Image createImage() {
		BufferedImage i = new BufferedImage(32, 32, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2 = (Graphics2D) i.getGraphics();
		g2.setColor(Color.RED);
		g2.fill(new Ellipse2D.Float(0, 0, i.getWidth(), i.getHeight()));
		g2.dispose();
		return i;
	}

	static JPopupMenu createJPopupMenu() {
		final JPopupMenu m = new JPopupMenu();
		m.add(new JMenuItem("Item 1"));
		m.add(new JMenuItem("Item 2"));
		JMenu submenu = new JMenu("Submenu");
		submenu.add(new JMenuItem("item 1"));
		submenu.add(new JMenuItem("item 2"));
		submenu.add(new JMenuItem("item 3"));
		m.add(submenu);
		JMenuItem exitItem = new JMenuItem("Exit");
		exitItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JakeMainApp.getInstance().saveQuit();
			}
		});
		m.add(exitItem);
		return m;
	}
}