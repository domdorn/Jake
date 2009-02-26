package com.jakeapp.gui.swing.renderer;

import com.jakeapp.core.domain.LogEntry;
import com.jakeapp.core.domain.NoteObject;
import com.jakeapp.core.domain.Project;
import com.jakeapp.gui.swing.JakeMainApp;
import com.jakeapp.gui.swing.helpers.TimeUtilities;
import com.jakeapp.gui.swing.helpers.Translator;
import com.jakeapp.gui.swing.helpers.UserHelper;
import com.jakeapp.gui.swing.panels.NewsPanel;
import org.apache.log4j.Logger;
import org.jdesktop.application.ResourceMap;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

/**
 * The PeopleListCellRenderer.
 * Renders People info with Status Icon.
 */
public class EventCellRenderer extends DefaultTableCellRenderer {
	private static final Logger log = Logger.getLogger(EventCellRenderer.class);

	// file actions
	private final static ImageIcon fileAddIcon = new ImageIcon(
					Toolkit.getDefaultToolkit().getImage(
									JakeMainApp.class.getResource("/icons/file-add.png")));
	private final static ImageIcon fileRemoveIcon = new ImageIcon(
					Toolkit.getDefaultToolkit().getImage(
									JakeMainApp.class.getResource("/icons/file-remove.png")));
	private final static ImageIcon fileMoveIcon = new ImageIcon(
					Toolkit.getDefaultToolkit().getImage(
									JakeMainApp.class.getResource("/icons/file-moved.png")));
	private final static ImageIcon fileUpdateIcon = new ImageIcon(
					Toolkit.getDefaultToolkit().getImage(
									JakeMainApp.class.getResource("/icons/file-updated.png")));
	private final static ImageIcon fileLockIcon = new ImageIcon(
					Toolkit.getDefaultToolkit().getImage(
									JakeMainApp.class.getResource("/icons/file-lock.png")));
	private final static ImageIcon fileUnlockIcon = new ImageIcon(
					Toolkit.getDefaultToolkit().getImage(
									JakeMainApp.class.getResource("/icons/file-unlock.png")));

	// project actions
	private final static ImageIcon projectCreatedIcon = new ImageIcon(
					Toolkit.getDefaultToolkit().getImage(
									JakeMainApp.class.getResource("/icons/" + "project-created.png")));

	// people actions
	private final static ImageIcon peopleAddIcon = new ImageIcon(
					Toolkit.getDefaultToolkit().getImage(
									JakeMainApp.class.getResource("/icons/user-add.png")));
	private final static ImageIcon peopleRemoveIcon = new ImageIcon(
					Toolkit.getDefaultToolkit().getImage(
									JakeMainApp.class.getResource("/icons/user-remove.png")));
	private final static ImageIcon peopleInviteIcon = new ImageIcon(
					Toolkit.getDefaultToolkit().getImage(
									JakeMainApp.class.getResource("/icons/user-invited.png")));
	private final static ImageIcon peopleAcceptInvitationIcon = new ImageIcon(
					Toolkit.getDefaultToolkit().getImage(
									JakeMainApp.class.getResource("/icons/user-inviteok.png")));
	private final static ImageIcon peopleChangeTrustIcon = new ImageIcon(
					Toolkit.getDefaultToolkit().getImage(
									JakeMainApp.class.getResource("/icons/user-trust.png")));

	// tag actions
	private final static ImageIcon tagAddIcon = new ImageIcon(
					Toolkit.getDefaultToolkit().getImage(
									JakeMainApp.class.getResource("/icons/" + "tags-add.png")));
	private final static ImageIcon tagRemovecon = new ImageIcon(
					Toolkit.getDefaultToolkit().getImage(
									JakeMainApp.class.getResource("/icons/" + "tags-remove.png")));

	// note actions
	private final static ImageIcon noteAddIcon = new ImageIcon(
					Toolkit.getDefaultToolkit().getImage(
									JakeMainApp.class.getResource("/icons/" + "note-add.png")));
	private final static ImageIcon noteRemoveIcon = new ImageIcon(
					Toolkit.getDefaultToolkit().getImage(
									JakeMainApp.class.getResource("/icons/" + "note-remove.png")));
	private final static ImageIcon noteUpdateIcon = new ImageIcon(
					Toolkit.getDefaultToolkit().getImage(
									JakeMainApp.class.getResource("/icons/" + "note-updated.png")));

	// get notes resource map
	private static final ResourceMap newsResourceMap =
					org.jdesktop.application.Application
									.getInstance(com.jakeapp.gui.swing.JakeMainApp.class).getContext()
									.getResourceMap(NewsPanel.class);

	public EventCellRenderer() {
		log.debug("Init EventCellRenderer.");
	}

	/* This is the only method defined by DefaultTableCellRenderer.  We just
		 * reconfigure the Jlabel each time we're called.
		 */
	@Override
	public Component getTableCellRendererComponent(JTable table, Object value,
					boolean isSelected, boolean hasFocus, int row, int column) {

		LogEntry loge = (LogEntry) value;
		String msg = "";

		// begin the string with e.g. "You" or "Peter" (Nicknames/FullNames)
		msg += UserHelper.getLocalizedUserNick(loge.getMember()) + " ";

		/* Build the String and set the operation Icon.
					*/
		boolean isNote = loge.getBelongsTo() instanceof NoteObject;
		String type = (isNote ? "Note" : "File");


		switch (loge.getLogAction()) {
			/*case JAKE_OBJECT_NEW_VERSION: {
				setIcon(fileAddIcon);
				msg += Translator.get(newsResourceMap, "eventsAddedFile", loge.getBelongsTo().toString());
			}
			break;*/

			case JAKE_OBJECT_DELETE: {
				setIcon((isNote ? noteRemoveIcon : fileRemoveIcon));
				msg += Translator.get(newsResourceMap, "eventsRemoved" + type,
								loge.getBelongsTo().toString());
			}
			break;

			case JAKE_OBJECT_NEW_VERSION: {
				setIcon((isNote ? noteUpdateIcon : fileUpdateIcon));
				msg += Translator.get(newsResourceMap, "eventsUpdated" + type,
								loge.getBelongsTo().toString());
			}
			break;

			case PROJECT_CREATED: {
				setIcon(projectCreatedIcon);
				msg += Translator.get(newsResourceMap, "eventsProjectCreated",
								((Project) loge.getBelongsTo()).getName());
			}
			break;

			case JAKE_OBJECT_LOCK: {
				setIcon(fileLockIcon);
				msg += Translator.get(newsResourceMap, "eventsObjectLock",
								loge.getBelongsTo().toString());
			}
			break;

			case JAKE_OBJECT_UNLOCK: {
				setIcon(fileUnlockIcon);
				msg += Translator.get(newsResourceMap, "eventsObjectUnlock",
								loge.getBelongsTo().toString());
			}
			break;

			case START_TRUSTING_PROJECTMEMBER: {
				setIcon(peopleAddIcon);
				msg += Translator.get(newsResourceMap, "eventsProjectMemberAdded",
								loge.getBelongsTo().toString());
			}
			break;

			case NOOP: {
				setIcon(peopleAcceptInvitationIcon);
				msg += Translator
								.get(newsResourceMap, "eventsProjectMemberInvitationAccepted");
			}
			break;

			// TODO: POSSIBLE???
			case STOP_TRUSTING_PROJECTMEMBER: {
				setIcon(peopleRemoveIcon);
				msg += Translator.get(newsResourceMap, "eventsProjectMemberRemoved",
								loge.getBelongsTo().toString());
			}
			break;

			case TAG_ADD: {
				setIcon(tagAddIcon);
				msg += Translator.get(newsResourceMap, "eventsTagsAdd",
								loge.getBelongsTo().toString());
			}
			break;

			case TAG_REMOVE: {
				setIcon(tagRemovecon);
				msg += Translator.get(newsResourceMap, "eventsTagsRemove",
								loge.getBelongsTo().toString());
			}
			break;

			default: {
				log.warn("Unsupported action: " + loge.getLogAction());
				setIcon(null);
				msg += loge.getLogAction();
			}
		}

		// do not insert html as this auto-wraps messages (not wanted)
		String valStr = msg; //"<html>" + msg + "</html>";

		/* The DefaultListCellRenderer class will take care of
				  * the JLabels text property, it's foreground and background
				  * colors, and so on.
				  */
		super.getTableCellRendererComponent(table, valStr, isSelected, hasFocus, row,
						column);

		String comment = "";
		if (loge.getComment() != null && loge.getComment().length() > 0) {
			comment = "<br><b>Comment: " + loge.getComment() + "</b>";
		}

		// set the tooltip text
		setToolTipText(
						"<html><font size=4>" + this.getText() + "</font><br>" + TimeUtilities
										.getRelativeTime(loge.getTimestamp()) + " (" + loge
										.getTimestamp().toGMTString() + ")" + comment + "</html>");

		return this;
	}
}