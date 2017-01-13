package a2;

import java.awt.Cursor;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

public class MenuBar extends JMenuBar implements MouseListener {

	/**
	 * Default serialVerisonUID = 1L
	 */
	private static final long serialVersionUID = 1L;

	// Menus
	protected final JMenu FILE_MENU;
	protected final JMenu EDIT_MENU;
	protected final JMenu IMAGE_MENU;
	protected final JMenu WINDOW_MENU;
	protected final JMenu HELP_MENU;

	// Menu items
	protected final JMenuItem NEWWINDOW_ITEM;
	protected final JMenuItem OPEN_ITEM;
	protected final JMenuItem SAVE_ITEM;
	protected final JMenuItem SAVE_AS_ITEM;
	protected final JMenuItem EXIT_ITEM;
	protected final JMenuItem USER_MANUAL_ITEM;

	protected final JMenuItem UNDO_ITEM;
	protected final JMenuItem REDO_ITEM;
	protected final JMenuItem CUT_ITEM;
	protected final JMenuItem COPY_ITEM;
	protected final JMenuItem PASTE_ITEM;
	
	protected final JMenuItem TOOLSTOOLBAR_ITEM;
	protected final JMenuItem MODIFICATIONTOOLBAR_ITEM;
	protected final JMenuItem RULER_ITEM;
	
	

	protected final JMenu SubFlip_Menu;
	protected final JMenu SubRotate_Menu;
	protected final JMenuItem FLIPHOR_ITEM;
	protected final JMenuItem FLIPVER_ITEM;
	protected final JMenuItem ROTATERIGHT_ITEM;
	protected final JMenuItem ROTATELEFT_ITEM;
	
	

	public MenuBar() {

		super();

		
		this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));

		
		// FILE MENU ITEMS
		this.FILE_MENU = new JMenu("File");
		FILE_MENU.setMnemonic(KeyEvent.VK_F);
		this.add(FILE_MENU);
		
		this.OPEN_ITEM = createMenuItem("folder-open.png", "Open");
		this.OPEN_ITEM.setMnemonic(KeyEvent.VK_O);
		this.FILE_MENU.add(this.OPEN_ITEM);
		KeyStroke keyStrokeToOpen = KeyStroke.getKeyStroke(KeyEvent.VK_O, KeyEvent.CTRL_DOWN_MASK);
		this.OPEN_ITEM.setAccelerator(keyStrokeToOpen);

		this.SAVE_ITEM = createMenuItem("document-save.png", "Save");
		this.SAVE_ITEM.setMnemonic(KeyEvent.VK_S);
		this.FILE_MENU.add(this.SAVE_ITEM );
		KeyStroke ksSave = KeyStroke.getKeyStroke(KeyEvent.VK_S, KeyEvent.CTRL_DOWN_MASK);
		this.SAVE_ITEM.setAccelerator(ksSave);
		
		this.SAVE_AS_ITEM = createMenuItem("document-saveas.png", "Save as");
		this.SAVE_AS_ITEM.setMnemonic(KeyEvent.VK_A);
		this.FILE_MENU.add(this.SAVE_AS_ITEM);
		KeyStroke ksSaveAs = KeyStroke.getKeyStroke(KeyEvent.VK_A, KeyEvent.CTRL_DOWN_MASK);
		this.SAVE_AS_ITEM.setAccelerator(ksSaveAs);
		
		this.FILE_MENU.addSeparator();
		

		
		this.FILE_MENU.add(this.NEWWINDOW_ITEM = createMenuItem("new-window menuitem.png", "New Window"));
		this.FILE_MENU.addSeparator();
		
		this.FILE_MENU.add(this.EXIT_ITEM = createMenuItem("exit.png", "Exit"));

		// EDIT MENU ITEMS
		this.EDIT_MENU = new JMenu("Edit");	
		this.UNDO_ITEM = createMenuItem("Undo menuitem.png", "Undo");
		this.UNDO_ITEM.setMnemonic(KeyEvent.VK_Z);
		this.EDIT_MENU.add(this.UNDO_ITEM );
		KeyStroke ksUndo = KeyStroke.getKeyStroke(KeyEvent.VK_Z, KeyEvent.CTRL_DOWN_MASK);
		this.UNDO_ITEM.setAccelerator(ksUndo);
		
	
		
		this.REDO_ITEM = createMenuItem("Redo menuitem.png", "Redo");
		this.REDO_ITEM.setMnemonic(KeyEvent.VK_Y);
		this.EDIT_MENU.add(this.REDO_ITEM );
		KeyStroke ksRedo = KeyStroke.getKeyStroke(KeyEvent.VK_Y, KeyEvent.CTRL_DOWN_MASK);
		this.REDO_ITEM.setAccelerator(ksRedo);
		
		
		this.EDIT_MENU.addSeparator();
		this.CUT_ITEM = createMenuItem("cut.png", "Cut");
		this.CUT_ITEM.setMnemonic(KeyEvent.VK_X);
		this.EDIT_MENU.add(this.CUT_ITEM );
		KeyStroke ksCut = KeyStroke.getKeyStroke(KeyEvent.VK_X, KeyEvent.CTRL_DOWN_MASK);
		this.CUT_ITEM.setAccelerator(ksCut);

		
		this.COPY_ITEM = createMenuItem("copy menuitem.png", "Copy");
		this.COPY_ITEM.setMnemonic(KeyEvent.VK_C);
		this.EDIT_MENU.add(this.COPY_ITEM );
		KeyStroke ksCopy = KeyStroke.getKeyStroke(KeyEvent.VK_C, KeyEvent.CTRL_DOWN_MASK);
		this.COPY_ITEM.setAccelerator(ksCopy);
		

		this.PASTE_ITEM = createMenuItem("paste menuitem.png", "Paste");
		this.PASTE_ITEM.setMnemonic(KeyEvent.VK_V);
		this.EDIT_MENU.add(this.PASTE_ITEM );
		KeyStroke ksPaste = KeyStroke.getKeyStroke(KeyEvent.VK_V, KeyEvent.CTRL_DOWN_MASK);
		this.PASTE_ITEM.setAccelerator(ksPaste);
		
		

		//IMAGE MENU ITEMS
		this.SubFlip_Menu = new JMenu("Flip ");
		this.SubFlip_Menu.setIcon(new ImageIcon("flip-horizontal menuitem.png"));
		this.SubFlip_Menu.add(this.FLIPHOR_ITEM = createMenuItem("flip-horizontal menuitem.png", "Flip Image Horizontal"));
		this.SubFlip_Menu.add(this.FLIPVER_ITEM = createMenuItem("flip-vertical menuitem.png", "Flip Image Vertically"));
		this.IMAGE_MENU = new JMenu("Image");
		this.IMAGE_MENU.add(SubFlip_Menu);
		
		
		this.SubRotate_Menu = new JMenu("Rotate ");
		this.SubRotate_Menu.add(this.ROTATELEFT_ITEM = createMenuItem("Rotate left menuitem.png", "Rotate left 90degrees"));
		this.SubRotate_Menu.add(this.ROTATERIGHT_ITEM = createMenuItem("Rotate right menuitem.png", "Rotate right 90degrees"));
		this.IMAGE_MENU.add(SubRotate_Menu);
		

		
		
		//WINDOW MENU ITEMS
		this.WINDOW_MENU = new JMenu("Window");
		
		this.RULER_ITEM = createMenuItem("checkmark.png", "Ruler");
		this.WINDOW_MENU.add(this.RULER_ITEM);
		this.WINDOW_MENU.addSeparator();
		this.TOOLSTOOLBAR_ITEM = createMenuItem("checkmark.png", "Tools Toolbar");
		
		this.WINDOW_MENU.add(this.TOOLSTOOLBAR_ITEM);
		this.MODIFICATIONTOOLBAR_ITEM = createMenuItem("checkmark.png", "Modification Toolbar");
		this.WINDOW_MENU.add(this.MODIFICATIONTOOLBAR_ITEM);
		

		// Menu items in Help Menu
		this.HELP_MENU = new JMenu("Help");
		this.HELP_MENU.add(this.USER_MANUAL_ITEM = createMenuItem("help.png", "User Manual"));

		// Add Menu to JMenuBar

		EDIT_MENU.setMnemonic(KeyEvent.VK_E);
		//KeyStroke ksEditMenu = KeyStroke.getKeyStroke(KeyEvent.VK_E, KeyEvent.CTRL_DOWN_MASK);
		//this.EDIT_MENU.setAccelerator(ksEditMenu);
		this.add(EDIT_MENU);
		
		IMAGE_MENU.setMnemonic(KeyEvent.VK_I);
	//	KeyStroke ksImageMenu = KeyStroke.getKeyStroke(KeyEvent.VK_I, KeyEvent.CTRL_DOWN_MASK);
	//	this.IMAGE_MENU.setAccelerator(ksImageMenu);
		this.add(IMAGE_MENU);
		
		WINDOW_MENU.setMnemonic(KeyEvent.VK_W);
	//	KeyStroke ksWindowMenu = KeyStroke.getKeyStroke(KeyEvent.VK_W, KeyEvent.CTRL_DOWN_MASK);
	//	this.WINDOW_MENU.setAccelerator(ksWindowMenu);
		this.add(WINDOW_MENU);
				
		HELP_MENU.setMnemonic(KeyEvent.VK_H);
	//	KeyStroke ksHelpMenu = KeyStroke.getKeyStroke(KeyEvent.VK_H, KeyEvent.CTRL_DOWN_MASK);
	//	this.HELP_MENU.setAccelerator(ksHelpMenu);
		this.add(HELP_MENU);
		
	}

	
	

	
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	private JMenuItem createMenuItem(String rawImage, String text) {

		final JMenuItem item = new JMenuItem(text);

		try {
			item.setIcon(new ImageIcon(ImageIO.read(ClassLoader.getSystemResource(rawImage))));

		} catch (Exception e) {
		}

		return item;
	}

}