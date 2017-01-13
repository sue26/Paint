package a2;

import java.awt.Cursor;

import javax.imageio.ImageIO;
import javax.swing.*;

public class GeneralToolBar extends JToolBar {
	private static final long serialVersionUID = 1L;
	
	protected final JButton UNDO_BUTTON = createButton("undo.png", null, "Undo the previous action");
	protected final JButton REDO_BUTTON = createButton("redo.png", null, "Redo the previous undone action");
	protected final JButton ZOOMIN_BUTTON = createButton("zoom-in.png", null, "Zoom in on current picture");
	protected final JButton ZOOMOUT_BUTTON = createButton("zoom-out.png", null, "Zoom out on current picture");
	protected final JButton SELECT_BUTTON = createButton("selection2.png", null, "Select a part of the picture");
	protected final JButton DRAG_BUTTON = createButton("drag.png", null, "Drag the selection from the picture");
	protected final JButton ROTATE_RIGHT_BUTTON = createButton("flip-horizontal.png", null, "Flip the picture");
	protected final JButton ROTATE_LEFT_BUTTON = createButton("flip-vertical.png", null, "Flip the picture vertical");
	protected final JButton CLEAR_BUTTON = createButton("trash.png", null, "Delete the File");
	protected final JButton HELP_BUTTON = createButton("help.png", null, "Opens the User Manual");
	
	GeneralToolBar() {
		this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		this.add(UNDO_BUTTON);
		this.add(REDO_BUTTON);
		this.addSeparator();
		this.add(ZOOMIN_BUTTON);
		this.add(ZOOMOUT_BUTTON);
		this.addSeparator();
		this.add(SELECT_BUTTON);
		this.add(DRAG_BUTTON);
		this.add(ROTATE_RIGHT_BUTTON);
		this.add(ROTATE_LEFT_BUTTON);
		this.addSeparator();
		this.add(CLEAR_BUTTON);
		this.addSeparator();
		this.add(HELP_BUTTON);
		
	}
	
	private JButton createButton(String rawImage, String text, String tooltip) {
		
		final JButton button = new JButton(text);
		button.setToolTipText(tooltip);

		try {
			button.setIcon(new ImageIcon(ImageIO.read(ClassLoader.getSystemResource(rawImage)), tooltip));
		} catch (Exception e) {
		}
		
		button.setBorderPainted(false);
		button.setContentAreaFilled(false);

		return button;
	}
}
