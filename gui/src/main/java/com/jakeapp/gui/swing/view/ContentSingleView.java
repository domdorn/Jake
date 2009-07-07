package com.jakeapp.gui.swing.view;

import com.jakeapp.gui.swing.model.ContentSingleViewModel;
import com.jakeapp.gui.swing.controller.ContentSingleViewController;

import javax.swing.*;
import java.awt.*;

/**
 * Created by IntelliJ IDEA.
 * User: domdorn
 * Date: Jul 6, 2009
 * Time: 8:02:32 PM
 * To change this template use File | Settings | File Templates.
 */
public class ContentSingleView extends JPanel {

	private final ContentSingleViewModel model;
	private final ContentSingleViewController controller;


	public ContentSingleView(ContentSingleViewModel model, ContentSingleViewController controller) {
		this.model = model;
		this.controller = controller;

		this.setBackground(new Color(255,188,8));
		this.validate();

	}

}
