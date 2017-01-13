package a2;

import java.awt.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import a2.PaintPanel.Entity;

import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.swing.event.*;

import java.util.*;

public class Paint {
	public static void main(String[] args) {
		// Feel and Look
		try {
			 UIManager.setLookAndFeel(
			            UIManager.getCrossPlatformLookAndFeelClassName());		
		} catch (Exception e) {}

		// Properties of MainJFrame
		PaintFrame frame = new PaintFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("Paint");
		frame.pack();

		// JFrame Location on Screen
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setLocation(dim.width / 2 - frame.getSize().width / 2, dim.height / 2 - frame.getSize().height / 2);
		//frame.setSize(dim);

		frame.setVisible(true);
	}
}

class PaintFrame extends JFrame implements ActionListener, MouseListener, MouseMotionListener{
	private static final long serialVersionUID = 1L;
	
	private MenuBar menubar = new MenuBar();
	private JScrollPane scrollpane;
	private SideToolBar sideToolBar = new SideToolBar();
	private ColorToolBar colorToolBar = new ColorToolBar();
	private GeneralToolBar generalToolBar = new GeneralToolBar();
	private PaintPanel pp;
	private JToolBar modificationBar;
	private JLabel currentTool; 
	
	private JSlider thickness = new JSlider(SwingConstants.HORIZONTAL, 1, 20, 6);
	private String text = new String("Hi");
	private int fontStyle = Font.PLAIN;
	private String fontFamily = "serif";
	private int fontSize = 10;
	private boolean underlined = false;
	private Font font;
	
	private ThicknessPanel thicknessPanel;
	private LinePanel linePanel;
	private FontPanel fontPanel;

	private int buttonClicked = 0; //pen=1, eraser=2, line=3, rectangle=4, circle=5, triangle=6, star=7, fill=8, text=9
									//arrow=10, diamond=11, pentagon=12

	private boolean select = false;
	private double xInitial;
	private double yInitial;
	private double dragX;
	private double dragY;
	private double xFinal;
	private double yFinal;
	private int edgeStyle = BasicStroke.CAP_ROUND;
	File f; JFileChooser fc; final String TITLE = "Paint";

		
	PaintFrame() {

		font = new Font(fontFamily, fontStyle, fontSize);
		this.generalToolBar.UNDO_BUTTON.setEnabled(false);
		this.generalToolBar.REDO_BUTTON.setEnabled(false);
		
		//Add MenuBar + ToolBar
		this.setJMenuBar(menubar);
		
		//Manage Canvas
		pp = new PaintPanel();
		scrollpane = new JScrollPane(pp);
		
		//Create Components + Set Layout
		currentTool = new JLabel();
		currentTool.setText("CHOOSE A TOOL");
		currentTool.setFont(new Font("Verdana", Font.BOLD, 15));
		currentTool.setHorizontalAlignment(SwingConstants.CENTER);
		currentTool.setForeground(Color.black);
		currentTool.setBorder(BorderFactory.createLineBorder(Color.black));
		currentTool.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));

		
		thicknessPanel = new ThicknessPanel();
		linePanel = new LinePanel();
		fontPanel = new FontPanel();
		
		modificationBar = new JToolBar();
		modificationBar.setLayout(new BoxLayout(modificationBar, BoxLayout.Y_AXIS));
		modificationBar.add(currentTool);
		modificationBar.add(colorToolBar);
		modificationBar.add(thicknessPanel);
		modificationBar.add(linePanel);
		modificationBar.add(fontPanel);
		modificationBar.setBackground(new Color(255, 229, 204));
		modificationBar.setFloatable(true);
		
		thicknessPanel.setVisible(false);
		linePanel.setVisible(false);
		fontPanel.setVisible(false);
		
		JPanel sidePanel = new JPanel();
		sidePanel.setLayout(new BorderLayout());
		sidePanel.add(sideToolBar, BorderLayout.NORTH);
		sidePanel.add(modificationBar, BorderLayout.CENTER);
		
		JPanel centerPanel = new JPanel();
		centerPanel.setLayout(new BorderLayout());
		centerPanel.add(generalToolBar, BorderLayout.NORTH);
		centerPanel.add(scrollpane, BorderLayout.CENTER);
		
		this.getContentPane().setLayout(new BorderLayout());
		this.getContentPane().add(sidePanel, BorderLayout.WEST);
		this.getContentPane().add(centerPanel, BorderLayout.CENTER);
		
		//Add Listeners
		
		
		this.sideToolBar.FREEFORMDRAW_BUTTON.addActionListener(this);
		this.sideToolBar.ERASER_BUTTON.addActionListener(this);
		this.sideToolBar.TEXT_BUTTON.addActionListener(this);
		this.sideToolBar.FILLCOLOR_BUTTON.addActionListener(this);
		this.sideToolBar.STRAIGHTLINE_BUTTON.addActionListener(this);
		this.sideToolBar.CIRCLE_BUTTON.addActionListener(this);
		this.sideToolBar.RECTANGLE_BUTTON.addActionListener(this);
		this.sideToolBar.TRIANGLE_BUTTON.addActionListener(this);
		this.sideToolBar.STAR_BUTTON.addActionListener(this);
		this.sideToolBar.ARROW_BUTTON.addActionListener(this);
		this.sideToolBar.DIAMOND_BUTTON.addActionListener(this);
		this.sideToolBar.PENTAGON_BUTTON.addActionListener(this);
		
		this.colorToolBar.Color_Black.addActionListener(this);
		this.colorToolBar.Color_Blue.addActionListener(this);
		this.colorToolBar.Color_Cyan.addActionListener(this);
		this.colorToolBar.Color_DarkGray.addActionListener(this);
		this.colorToolBar.Color_Gray.addActionListener(this);
		this.colorToolBar.Color_Green.addActionListener(this);
		this.colorToolBar.Color_Magenta.addActionListener(this);
		this.colorToolBar.Color_Orange.addActionListener(this);
		this.colorToolBar.Color_Purple.addActionListener(this);
		this.colorToolBar.Color_Red.addActionListener(this);
		this.colorToolBar.Color_White.addActionListener(this);
		this.colorToolBar.Color_Yellow.addActionListener(this);
		this.colorToolBar.moreColor.addActionListener(this);
		
		this.generalToolBar.UNDO_BUTTON.addActionListener(this);
		this.generalToolBar.REDO_BUTTON.addActionListener(this);
		this.generalToolBar.ZOOMIN_BUTTON.addActionListener(this);
		this.generalToolBar.ZOOMOUT_BUTTON.addActionListener(this);
		this.generalToolBar.SELECT_BUTTON.addActionListener(this);
		this.generalToolBar.DRAG_BUTTON.addActionListener(this);
		this.generalToolBar.ROTATE_RIGHT_BUTTON.addActionListener(this);
		this.generalToolBar.ROTATE_LEFT_BUTTON.addActionListener(this);
		this.generalToolBar.CLEAR_BUTTON.addActionListener(this);
		this.generalToolBar.HELP_BUTTON.addActionListener(this);
		
		
	    this.menubar.MODIFICATIONTOOLBAR_ITEM.addActionListener(this);
		this.menubar.TOOLSTOOLBAR_ITEM.addActionListener(this);
		this.menubar.OPEN_ITEM.addActionListener(this);
		this.menubar.SAVE_ITEM.addActionListener(this);
		this.menubar.SAVE_AS_ITEM.addActionListener(this);
		this.menubar.NEWWINDOW_ITEM.addActionListener(this);
		this.menubar.EXIT_ITEM.addActionListener(this);
		this.menubar.USER_MANUAL_ITEM.addActionListener(this);


		this.pp.addMouseListener(this);
		this.pp.addMouseMotionListener(this);
		
	}
	
	
	private void updateModPane(int buttonClicked) {
		//pen=1, eraser=2, line=3, rectangle=4, circle=5, triangle=6, star=7, fill=8, text=9
		//arrow=10, diamond=11, pentagon=12
		
		if (buttonClicked == 1) { 
			//PENCIL
			currentTool.setText("PENCIL");
			thicknessPanel.setVisible(true);
			linePanel.setVisible(true);
			fontPanel.setVisible(false);
			Image pencilCursor = new ImageIcon("pencil.png").getImage(); 
			int x = 0; 
			int y = pencilCursor.getHeight(this); 
			Point center = new Point(x, y - 1); 
			Toolkit t = Toolkit.getDefaultToolkit(); 
			Cursor c = t.createCustomCursor(pencilCursor, center, "pencil"); 
			this.setCursor(c);
		} else if (buttonClicked == 2) {
			//ERASER
			currentTool.setText("ERASER");
			thicknessPanel.setVisible(true);
			linePanel.setVisible(false);
			fontPanel.setVisible(false);
			Image eraserCursor = new ImageIcon("eraser.png").getImage(); 
			int x = eraserCursor.getWidth(this)/2; 
			int y = eraserCursor.getHeight(this)/2; 
			Point center = new Point(x, y); 
			Toolkit t = Toolkit.getDefaultToolkit(); 
			Cursor c = t.createCustomCursor(eraserCursor, center, "eraser"); 
			this.setCursor(c);
		} else if (buttonClicked == 3) {
			//LINE
			currentTool.setText("LINE");
			thicknessPanel.setVisible(true);
			linePanel.setVisible(true);
			fontPanel.setVisible(false);
			this.setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));
		} else if (buttonClicked == 4) {
			//RECTANGLE
			currentTool.setText("RECTANGLE");
			thicknessPanel.setVisible(true);
			linePanel.setVisible(false);
			fontPanel.setVisible(false);
			this.setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));
		} else if (buttonClicked == 5) {
			//CIRCLE
			currentTool.setText("CIRCLE");
			thicknessPanel.setVisible(true);
			linePanel.setVisible(false);
			fontPanel.setVisible(false);
			this.setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));
		} else if (buttonClicked == 6) {
			//TRIANGLE
			currentTool.setText("TRIANGLE");
			thicknessPanel.setVisible(true);
			linePanel.setVisible(false);
			fontPanel.setVisible(false);
			this.setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));
		} else if (buttonClicked == 7) {
			//STAR
			currentTool.setText("STAR");
			thicknessPanel.setVisible(true);
			linePanel.setVisible(false);
			fontPanel.setVisible(false);
			this.setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));
		} else if (buttonClicked == 8) {
			//FILL
			currentTool.setText("FILL");
			thicknessPanel.setVisible(false);
			linePanel.setVisible(false);
			fontPanel.setVisible(false);
			Image fillCursor = new ImageIcon("fill.png").getImage(); 
			int x = 0; 
			int y = fillCursor.getHeight(this) / 2; 
			Point center = new Point(x, y + 1); 
			Toolkit t = Toolkit.getDefaultToolkit(); 
			Cursor c = t.createCustomCursor(fillCursor, center, "fill"); 
			this.setCursor(c);
		} else if (buttonClicked == 9) {
			//TEXT
			currentTool.setText("TEXT");
			thicknessPanel.setVisible(false);
			linePanel.setVisible(false);
			fontPanel.setVisible(true);
			this.setCursor(new Cursor(Cursor.TEXT_CURSOR));
		} else if (buttonClicked == 10) {
			//ARROW
			currentTool.setText("ARROW");
			thicknessPanel.setVisible(true);
			linePanel.setVisible(false);
			fontPanel.setVisible(false);
			this.setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));
		} else if (buttonClicked == 11) {
			//DIAMOND
			currentTool.setText("DIAMOND");
			thicknessPanel.setVisible(true);
			linePanel.setVisible(false);
			fontPanel.setVisible(false);
			this.setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));
		} else if (buttonClicked == 12) {
			//PENTAGON
			currentTool.setText("PENTAGON");
			thicknessPanel.setVisible(true);
			linePanel.setVisible(false);
			fontPanel.setVisible(false);
			this.setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));
		}
	}
	private void openFile(File fArg) {

		try {
			BufferedImage selectedFile = ImageIO.read(new File(fc.getSelectedFile().getAbsolutePath()));

			// for (String x : ImageIO.getReaderFileSuffixes()) {
			// if (fc.getSelectedFile().getName().endsWith(x)) {
			BufferedImage resized = resizeImage(selectedFile, (int) pp.getSize().getWidth(),
					(int) pp.getSize().getHeight());
			JLabel image = new JLabel("", new ImageIcon(resized), JLabel.CENTER);
			this.setTitle(fc.getSelectedFile().getName());
			pp.setSize(image.getWidth(), image.getHeight());
			pp.revalidate();
			pp.repaint();
			pp.add(image, BorderLayout.CENTER);
			pp.revalidate();
			pp.repaint();
			// }
			// }
		} catch (IOException e) {
			popupError("Error reading file '" + fArg.getName() + "'!");
			return;
		}

		this.setTitle(fArg.getName() + " - " + TITLE);
		menubar.EXIT_ITEM.setEnabled(true);
		menubar.SAVE_ITEM.setEnabled(true);
		menubar.SAVE_AS_ITEM.setEnabled(true);
		return;
	}

	private void saveToFile(File fArg) {

		BufferedImage image = new BufferedImage(pp.getWidth(), pp.getHeight(), BufferedImage.TYPE_INT_ARGB);

		Graphics g = image.getGraphics();
		pp.paint(g);
		try {
			ImageIO.write(image, "png", fArg);
		} catch (IOException ex) {

			popupError("Can't open file '" + fArg.getName() + "' for writing");
			return;

		}
		JOptionPane.showMessageDialog(null, "File has been saved", "File Saved", JOptionPane.INFORMATION_MESSAGE);
		this.setTitle(fArg.getName() + " - " + TITLE);

	}

	private void popupError(String s) {
		// Toolkit.getDefaultToolkit().beep();
		System.out.print("\07");
		System.out.flush();
		JOptionPane.showMessageDialog(this, s, "Error", JOptionPane.ERROR_MESSAGE);
		return;
	}

	private boolean okToReplace(File f) {
		final Object[] options = { "Yes", "No", "Cancel" };
		return JOptionPane.showOptionDialog(this,
				"The file '" + f.getName() + "' already exists.  " + "Replace existing file?", "Warning",
				JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE, null, options,
				options[2]) == JOptionPane.YES_OPTION;
	}

	private int changesShouldBeSaved() {
		final Object[] options = { "Yes", "No", "Cancel" };
		return JOptionPane.showOptionDialog(this, "Save changes before closing?", "Close",
				JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[2]);
	}

	@Override
	public void actionPerformed(ActionEvent ae) {
		
			this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));

		Object source = ae.getSource();

		if (source == menubar.OPEN_ITEM) {
			JFileChooser fileChooser = new JFileChooser();

			// Filters the files that can be chosen: bmp, jpg, jpeg, wbmp, png, gif
			fc = new JFileChooser(new File("."));
			FileFilter imageFilter = new FileNameExtensionFilter("Image files", ImageIO.getReaderFileSuffixes());

			fc.addChoosableFileFilter(imageFilter);
			
			int returnValue = fileChooser.showOpenDialog(null);
			if (returnValue == JFileChooser.APPROVE_OPTION) {
				try {

					BufferedImage selectedFile = ImageIO.read(new File(fileChooser.getSelectedFile().getAbsolutePath()));
					JLabel image = new JLabel("", new ImageIcon(selectedFile), JLabel.CENTER);
					pp.drawEntity(null, Entity.IMAGE, 0, 0, 0, 0, 0, null, 0, "", null, false, null, false, 0, 0, selectedFile);
					this.generalToolBar.UNDO_BUTTON.setEnabled(true);
					pp.revalidate();
					pp.repaint();

				} catch (IOException e2) {
					e2.printStackTrace();
				}
			}

		} else if (source == menubar.USER_MANUAL_ITEM) {
			new UserManual();
		}

		else if (source == menubar.SAVE_ITEM) {

			if (f != null) {

				saveToFile(f);
			} else
				menubar.SAVE_AS_ITEM.doClick();

		} else if (source == menubar.SAVE_AS_ITEM) {
			if (fc.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
				f = fc.getSelectedFile();

				// check if file exists before overwriting
				// (Note: Dialog only pops up if file exists)

				if (!f.exists() || okToReplace(f))
					saveToFile(f);
			}

		}  else if (source == menubar.NEWWINDOW_ITEM) {
			menubar.EXIT_ITEM.doClick();
		}

		else if (source == menubar.TOOLSTOOLBAR_ITEM) {
			if (this.sideToolBar.isVisible()) {
				this.menubar.TOOLSTOOLBAR_ITEM.setIcon(new ImageIcon(""));
				this.sideToolBar.setVisible(false);
			} else {
				this.menubar.TOOLSTOOLBAR_ITEM.setIcon(new ImageIcon("checkmark.png"));
				this.sideToolBar.setVisible(true);
			}
		}

		else if (source == menubar.MODIFICATIONTOOLBAR_ITEM) {
			if (this.modificationBar.isVisible()) {
				this.menubar.MODIFICATIONTOOLBAR_ITEM.setIcon(new ImageIcon(""));
				this.modificationBar.setVisible(false);
			} else {
				this.menubar.MODIFICATIONTOOLBAR_ITEM.setIcon(new ImageIcon("checkmark.png"));
				this.modificationBar.setVisible(true);
			}
		}

		else if (source == menubar.EXIT_ITEM) {
			int blah = changesShouldBeSaved();
			if (blah == JOptionPane.YES_OPTION)
				if (f != null)
					saveToFile(f);
				else
					menubar.SAVE_AS_ITEM.doClick();
			else if (blah == JOptionPane.CANCEL_OPTION)
				return;

			f = null;
			this.setTitle(TITLE);

			menubar.EXIT_ITEM.setEnabled(false);
			menubar.SAVE_ITEM.setEnabled(false);
			menubar.SAVE_AS_ITEM.setEnabled(false);
			System.exit(0);
		} else if (source == generalToolBar.UNDO_BUTTON) {
			if (pp.v.size() == 1) {
				pp.undo();
				this.generalToolBar.UNDO_BUTTON.setEnabled(false);
			} else {
				pp.undo();
			}
		} else if (source == generalToolBar.REDO_BUTTON) {
			if (pp.stack.isEmpty()) {
				this.generalToolBar.REDO_BUTTON.setEnabled(false);
			} else {
				pp.redo();
				this.generalToolBar.REDO_BUTTON.setEnabled(true);
			}
		} else if (source == generalToolBar.ZOOMIN_BUTTON) {
			/////////////////////
		} else if (source == generalToolBar.ZOOMOUT_BUTTON) {
			/////////////////////
		} else if (source == generalToolBar.SELECT_BUTTON) {
			select = true;
			buttonClicked = 0;
		} else if (source == generalToolBar.DRAG_BUTTON) {
			///////////////////////
		} else if (source == generalToolBar.ROTATE_RIGHT_BUTTON) {
			pp.rotateRight();
		} else if (source == generalToolBar.ROTATE_LEFT_BUTTON) {
			pp.rotateLeft();
		} else if (source == generalToolBar.CLEAR_BUTTON) {
			pp.clear();
		} else if (source == generalToolBar.HELP_BUTTON) {
			/////////////////////
		} else if (source == colorToolBar.Color_Black || source == colorToolBar.Color_Blue || source == colorToolBar.Color_Cyan
				|| source == colorToolBar.Color_DarkGray || source == colorToolBar.Color_Gray || source == colorToolBar.Color_Green
				|| source == colorToolBar.Color_Magenta || source == colorToolBar.Color_Orange || source == colorToolBar.Color_Purple
				|| source == colorToolBar.Color_Red || source == colorToolBar.Color_White || source == colorToolBar.Color_Yellow) {
			this.colorToolBar.changeCurrentColor(((JButton)source).getBackground());
			
		} else if (source == colorToolBar.moreColor) {
			Color color = JColorChooser.showDialog(null, "Please select a color", colorToolBar.getCurrentColor());
			
			if (color != null) 
				colorToolBar.changeCurrentColor(color);
		} else {
			if (source == sideToolBar.FREEFORMDRAW_BUTTON) {
				buttonClicked = 1;
			} else if (source == sideToolBar.ERASER_BUTTON) {
				buttonClicked = 2;
			} else if (source == sideToolBar.TEXT_BUTTON) {
				buttonClicked = 9;
			} else if (source == sideToolBar.FILLCOLOR_BUTTON) {
				buttonClicked = 8;
			} else if (source == sideToolBar.STRAIGHTLINE_BUTTON) {
				buttonClicked = 3;
			} else if (source == sideToolBar.CIRCLE_BUTTON) {
				buttonClicked = 5;
			} else if (source == sideToolBar.RECTANGLE_BUTTON) {
				buttonClicked = 4;
			} else if (source == sideToolBar.TRIANGLE_BUTTON) {
				buttonClicked = 6;
			} else if (source == sideToolBar.STAR_BUTTON) {
				buttonClicked = 7;
			} else if (source == sideToolBar.ARROW_BUTTON) {
				buttonClicked = 10;
			} else if (source == sideToolBar.DIAMOND_BUTTON) {
				buttonClicked = 11;
			} else if (source == sideToolBar.PENTAGON_BUTTON) {
				buttonClicked = 12;
			}
			updateModPane(buttonClicked);
		}
		
	}  
	
	/**
	 * Resizes the specified Image
	 * 
	 * @param image
	 *            - The image
	 * @param width
	 *            - The width of the panel in which the image is to be displayed
	 * @param height
	 *            - The height of the panel in which the image is to be
	 *            displayed
	 * @return
	 */
	private BufferedImage resizeImage(BufferedImage image, int width, int height) {
		int resizedWidth = width;
		int resizedHeight = height;
		double f = 1.0d;
		if (image.getWidth() > image.getHeight()) {
			f = ((double) image.getHeight() / (double) image.getWidth());
			resizedHeight = (int) (resizedWidth * f);
		} else {
			f = ((double) image.getWidth() / (double) image.getHeight());
			resizedWidth = (int) (resizedHeight * f);
		}

		BufferedImage resizedImg = new BufferedImage(resizedWidth, resizedHeight, BufferedImage.TRANSLUCENT);
		Graphics2D g2D = resizedImg.createGraphics();
		g2D.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		g2D.drawImage(image, 0, 0, resizedWidth, resizedHeight, null);
		g2D.dispose();
		return resizedImg;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		int x = e.getX();
		int y = e.getY();
		
		if (buttonClicked == 8) {
			//FILL
			pp.fill(x, y, colorToolBar.getCurrentColor());
		}
		if (select) {
			pp.select(x, y);
			select = false;
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		xInitial = e.getX();
		yInitial = e.getY();
	}

	@Override
	public void mouseReleased(MouseEvent me) {
		xFinal = me.getX();
		yFinal = me.getY();
		
		if(buttonClicked == 3) {
			pp.drawEntity(null, Entity.LINE, xInitial, yInitial, xFinal, yFinal, thickness.getValue(), colorToolBar.getCurrentColor(),
					edgeStyle, text, font, underlined, null, false, 0, 0, null);
			this.generalToolBar.UNDO_BUTTON.setEnabled(true);

		} else if (buttonClicked == 9){
			pp.drawEntity(null, Entity.TEXT, xInitial, yInitial, xFinal, yFinal, thickness.getValue(), colorToolBar.getCurrentColor().darker(),
					edgeStyle, text, font, underlined, null, false, 0, 0, null);
		} else {
			//Shapes
			if (!(xInitial == xFinal && yInitial == yFinal)) {
				if (xFinal < xInitial) {
					double temp;
					temp = xInitial;
					xInitial = xFinal;
					xFinal = temp;
				} 
				if (yFinal < yInitial) {
					double temp;
					temp = yInitial;
					yInitial = yFinal;
					yFinal = temp;
				}
				
				if (buttonClicked == 4) {
					pp.drawEntity(null, Entity.RECTANGLE, xInitial, yInitial, xFinal, yFinal, thickness.getValue(), colorToolBar.getCurrentColor(),
							edgeStyle, text, font, underlined, null, false, 0, 0, null);
				} else if (buttonClicked == 5) {
					pp.drawEntity(null, Entity.CIRCLE, xInitial, yInitial, xFinal, yFinal, thickness.getValue(), colorToolBar.getCurrentColor(),
							edgeStyle, text, font, underlined, null, false, 0, 0, null);
				} else if (buttonClicked == 6) {
					pp.drawEntity(null, Entity.TRIANGLE, xInitial, yInitial, xFinal, yFinal, thickness.getValue(), colorToolBar.getCurrentColor(),
							edgeStyle, text, font, underlined, null, false, 0, 0, null);
				} else if (buttonClicked == 7) {
					pp.drawEntity(null, Entity.STAR, xInitial, yInitial, xFinal, yFinal, thickness.getValue(), colorToolBar.getCurrentColor(),
							edgeStyle, text, font, underlined, null, false, 0, 0, null);
				} else if (buttonClicked == 10) {
					pp.drawEntity(null, Entity.ARROW, xInitial, yInitial, xFinal, yFinal, thickness.getValue(), colorToolBar.getCurrentColor(),
							edgeStyle, text, font, underlined, null, false, 0, 0, null);
				} else if (buttonClicked == 11) {
					pp.drawEntity(null, Entity.DIAMOND, xInitial, yInitial, xFinal, yFinal, thickness.getValue(), colorToolBar.getCurrentColor(),
							edgeStyle, text, font, underlined, null, false, 0, 0, null);
				} else if (buttonClicked == 12) {
					pp.drawEntity(null, Entity.PENTAGON, xInitial, yInitial, xFinal, yFinal, thickness.getValue(), colorToolBar.getCurrentColor(),
							edgeStyle, text, font, underlined, null, false, 0, 0, null);
				}
			}
		}
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		dragX = e.getX();
		dragY = e.getY();
		
		if(buttonClicked == 1) {
			pp.drawEntity(null, Entity.FREEFORM, xInitial, yInitial, dragX, dragY, thickness.getValue(), colorToolBar.getCurrentColor(),
					edgeStyle, text, font, underlined, null, false, 0, 0, null);
			xInitial = dragX;
			yInitial = dragY;
		} else if(buttonClicked == 2) {
			pp.drawEntity(null, Entity.ERASER, xInitial, yInitial, dragX, dragY, thickness.getValue(), colorToolBar.getCurrentColor(),
					edgeStyle, text, font, underlined, null, false, 0, 0, null);
			xInitial = dragX;
			yInitial = dragY;
		} 
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	
	//PRIVATE CLASSES
	private class ThicknessPanel extends JPanel implements ChangeListener {
		private static final long serialVersionUID = 1L;

		ThicknessPanel() {
			this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			thickness.setMajorTickSpacing(19);
			thickness.setMinorTickSpacing(5);
			thickness.setPaintTicks(true);
			thickness.setPaintLabels(true);
			thickness.setBorder(BorderFactory.createTitledBorder(new EtchedBorder(), "Thickness:  " + thickness.getValue()));
			thickness.setBackground(Color.white);
			thickness.setOpaque(true);
			
			this.add(thickness);
			this.setBackground(Color.white);
			
			thickness.addChangeListener(this);
		}

		@Override
		public void stateChanged(ChangeEvent e) {
			// TODO Auto-generated method stub
			JSlider source = (JSlider) e.getSource();
			if(source == thickness) {
				thickness.setBorder(BorderFactory.createTitledBorder(new EtchedBorder(), "Thickness: " + thickness.getValue()));
			}
			
		}
	}
	
	private class LinePanel extends JPanel implements ActionListener {
		private static final long serialVersionUID = 1L;
		
		private JRadioButton sharpButton;
		private JRadioButton roundButton;
		private final int ROUND = BasicStroke.CAP_ROUND;
		private final int SHARP = BasicStroke.CAP_SQUARE;
		
		LinePanel() {
			this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			//LINE RELATED MODIFICATIONS
			this.setBackground(Color.white);
			sharpButton = new JRadioButton("Sharp Edge");
			roundButton = new JRadioButton("Round Edge");
			roundButton.setSelected(true);
			this.add(sharpButton);
			this.add(roundButton);
			ButtonGroup edgeStyle = new ButtonGroup();
			edgeStyle.add(sharpButton);
			edgeStyle.add(roundButton);
			
			//Add Listeners
			sharpButton.addActionListener(this);
			roundButton.addActionListener(this);
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			JRadioButton source = (JRadioButton) e.getSource();
			if (source == sharpButton) {
				edgeStyle = SHARP;
			} else if (source == roundButton) {
				edgeStyle = ROUND;
			}
		}
	}
	
	
	private class FontPanel extends JPanel implements ActionListener, FocusListener {
		private static final long serialVersionUID = 1L;
		
		private JComboBox<String> fontCombo;
		private String[] fontList;
		private JComboBox<String> sizeCombo;
		final String[] SZ = { "10", "14", "18", "22", "26", "32", "38", "48" };
		private JToggleButton boldButton;
		private JToggleButton italicButton;
		private JToggleButton underlineButton;
		private JTextField textField;
		
		FontPanel() {
			this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			//TEXT RELATED MODIFICATIONS
			textField = new JTextField();
			textField.setText("Hi");
			textField.setFont(new Font(fontFamily, Font.PLAIN, 14));
			textField.setColumns(10);
			
			JPanel text = new JPanel();
			text.add(new JLabel("Set text: (press enter)"));
			text.add(this.textField);
			
			GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
			fontList = ge.getAvailableFontFamilyNames();
			fontCombo = new JComboBox<String>(fontList);
			fontCombo.setPreferredSize(new Dimension(60, 25));
			fontCombo.setRenderer(new CustomRenderer());
			
			int defaultFamilyIndex = -1;
			for (int i = 0; i < fontList.length; ++i)
			{
				if (fontList[i].toLowerCase().equals("serif"))
				{
					defaultFamilyIndex = i;
					break;
				}
			}

			if (defaultFamilyIndex == -1) // not found!
			{
				JOptionPane.showMessageDialog(this, "Default font family ('serif') not found!\n" + "Will use '"
						+ fontList[0] + "' as default.", "Information Message", JOptionPane.INFORMATION_MESSAGE);
				defaultFamilyIndex = 0;
			}
			fontCombo.setSelectedIndex(defaultFamilyIndex);
			
			sizeCombo = new JComboBox<String>(SZ);
			sizeCombo.setEditable(true);
			sizeCombo.setPreferredSize(new Dimension(45, 45));
			sizeCombo.setSelectedIndex(0);
			
			boldButton = new JToggleButton("B");
			italicButton = new JToggleButton("I");
			underlineButton = new JToggleButton("<html><u>U</u></html>");
			boldButton.setFont(new Font("Verdana", Font.BOLD, 10));
			italicButton.setFont(new Font("Verdana", Font.ITALIC, 10));
			underlineButton.setFont(new Font("Verdana", Font.PLAIN, 10));
			boldButton.setBackground(Color.lightGray);
			boldButton.setOpaque(true);
			italicButton.setBackground(Color.lightGray);
			italicButton.setOpaque(true);
			underlineButton.setBackground(Color.lightGray);
			underlineButton.setOpaque(true);
			
			boldButton.setPreferredSize(new Dimension(45, 45));
			italicButton.setPreferredSize(new Dimension(45, 45));
			underlineButton.setPreferredSize(new Dimension(45, 45));
			
			JPanel buttons = new JPanel();
			buttons.setLayout(new FlowLayout());
			buttons.add(boldButton);
			buttons.add(italicButton);
			buttons.add(underlineButton);
			buttons.setBackground(Color.white);
			
			JPanel font1 = new JPanel();
			font1.setLayout(new BorderLayout());
			font1.setBackground(Color.white);
			font1.add(buttons, BorderLayout.NORTH);
			font1.add(sizeCombo, BorderLayout.SOUTH);
			
			JPanel font2 = new JPanel();
			font2.setLayout(new BorderLayout());
			font2.setBackground(Color.white);
			font2.add(text, BorderLayout.NORTH);
			font2.add(fontCombo, BorderLayout.SOUTH);
			
			this.setLayout(new BorderLayout());
			this.setBackground(Color.white);
			this.add(font2, BorderLayout.NORTH);
			this.add(font1, BorderLayout.SOUTH);
			this.setBorder(BorderFactory.createTitledBorder(new EtchedBorder(), "Font"));
			
			//Add Listeners
			boldButton.addActionListener(this);
			italicButton.addActionListener(this);
			underlineButton.addActionListener(this);
			fontCombo.addActionListener(this);
			sizeCombo.addActionListener(this);
			textField.addActionListener(this);
			textField.addFocusListener(this);
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			Object source =  e.getSource();
			
			if (source == boldButton) {
				if (((JToggleButton)boldButton).isSelected()) 
					fontStyle = fontStyle | Font.BOLD; // turn bold on
				else 
					fontStyle = fontStyle & ~Font.BOLD; // turn bold off
			} else if (source == italicButton) {
				if (((JToggleButton)italicButton).isSelected())
					fontStyle = fontStyle | Font.ITALIC; // turn italic on
				else
					fontStyle = fontStyle & ~Font.ITALIC; // turn italic off
			} else if (source == underlineButton) {
				if (((JToggleButton)underlineButton).isSelected())
					underlined = true;
				else
					underlined = false;
			} else if (source == fontCombo) {
				fontFamily = fontList[fontCombo.getSelectedIndex()];
			} else if (source == sizeCombo) {
				int tmp = this.getFontSize(sizeCombo);
				if (tmp == -1)
					return;
				else
					fontSize = tmp;
			} else if (source == textField) {
				text = textField.getText();
			}
			textField.setFont(new Font(fontFamily, Font.PLAIN, 14));
			font = new Font(fontFamily, fontStyle, fontSize);
		}
		
		private int getFontSize(JComboBox<String> cb)
		{
			String userInput = (String) cb.getSelectedItem();
			boolean ok = true;
			int fntSize = -1;
			try
			{
				fntSize = Integer.parseInt(userInput);
			} catch (NumberFormatException nfe)
			{
				ok = false;
			}

			if (fntSize < 1 || fntSize > 500)
			{
				ok = false;
				fntSize = -1; // indicates invalid
				cb.setSelectedItem("" + fontSize);
			}

			if (!ok)
			{
				System.out.print("\07");
				System.out.flush();
				JOptionPane.showMessageDialog(pp.getParent(), "Please enter an integer in the range 1-500!", "Invalid Input",
						JOptionPane.ERROR_MESSAGE);

				// keep focus on combobox editor until input is corrected
				cb.setSelectedItem("" + fontSize);
			}
			return fntSize; // returns -1 if invalid
		}

		@Override
		public void focusGained(FocusEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void focusLost(FocusEvent e) {
			Object source = e.getSource();

			if(source == textField) {
				text = textField.getText();
			}
		}
	}
	
	private class CustomRenderer extends JTextField implements ListCellRenderer
	{
		// the following avoids a "warning" with Java 1.5.0 complier (?)
		static final long serialVersionUID = 42L;
		public CustomRenderer()
		{
			this.setOpaque(true);
			this.setHorizontalAlignment(LEFT);
			this.setBorder(null);
		}
		@Override
		public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected,
				boolean cellHasFocus)
		{
			if (isSelected)
			{
				setBackground(list.getSelectionBackground());
				setForeground(list.getSelectionForeground());
			} else
			{
				setBackground(list.getBackground());
				setForeground(list.getForeground());
			}
			String fontFamily = (String) value;
			this.setText(fontFamily);
			// The following line is the 'clincher'. This will cause
			// the font family name to be rendered in the named font.
			this.setFont(new Font(fontFamily, Font.PLAIN, 14));
			return this;
		}
}
}



