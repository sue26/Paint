package a2;

import javax.imageio.ImageIO;
import javax.swing.*;

import java.awt.*;

public class SideToolBar extends JToolBar{
	private static final long serialVersionUID = 1L;
	
	// Tool elements
	protected final JButton FREEFORMDRAW_BUTTON = createButton("pencil.png", null, "Draw a free-form line");
	protected final JButton ERASER_BUTTON = createButton("eraser.png", null, "Eraser");
	protected final JButton TEXT_BUTTON = createButton("text-box.png", null, "Insert text into the picture");
	protected final JButton FILLCOLOR_BUTTON = createButton("fill.png", null, "Fill with color");
	protected final JButton STRAIGHTLINE_BUTTON = createButton("straight-line.png", null, "Line");
	protected final JButton CIRCLE_BUTTON = createButton("circle.png", null, "circle");
	protected final JButton RECTANGLE_BUTTON = createButton("rectangle.png", null, "Rectangle");
	protected final JButton TRIANGLE_BUTTON = createButton("triangle.png", null, "Triangle");
	protected final JButton STAR_BUTTON = createButton("star.png", null, "Star");
	protected final JButton ARROW_BUTTON = createButton("arrow.png", null, "Arrow");
	protected final JButton DIAMOND_BUTTON = createButton("diamond.png", null , "Diamond");
	protected final JButton PENTAGON_BUTTON = createButton("pentagon.png", null, "Pentagon");
		
	public SideToolBar() {
		this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		this.setFloatable(true);
		
		//Set preferred size
		FREEFORMDRAW_BUTTON.setPreferredSize(new Dimension(95,60));
		STRAIGHTLINE_BUTTON.setPreferredSize(new Dimension(95,60));

		
		//Set Layout
			//Tools
		JPanel tool1 = new JPanel();
		tool1.setLayout(new GridLayout(0,2));
		tool1.add(FREEFORMDRAW_BUTTON);
		tool1.add(ERASER_BUTTON);
		tool1.add(TEXT_BUTTON);
		tool1.add(FILLCOLOR_BUTTON);
		
		JPanel tool2 = new JPanel();
		tool2.setLayout(new GridLayout(0,2));
		tool2.add(STRAIGHTLINE_BUTTON);
		tool2.add(CIRCLE_BUTTON);
		tool2.add(RECTANGLE_BUTTON);
		tool2.add(TRIANGLE_BUTTON);
		tool2.add(STAR_BUTTON);
		tool2.add(ARROW_BUTTON);
		tool2.add(DIAMOND_BUTTON);
		tool2.add(PENTAGON_BUTTON);
		
		this.setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
		this.add(tool1);
		JSeparator separator = new JSeparator(SwingConstants.HORIZONTAL);
		this.add(separator);
		this.add(Box.createVerticalStrut(5));
		this.add(tool2);
	}
	
	/**
	 * 
	 * @param rawImage image of the icon
	 * @param text text appears if the image of the icon is not found
	 * @param tooltip 
	 * @return a button with image icon of rawImage
	 */
	private JButton createButton(String rawImage, String text, String tooltip) {
		
		final JButton button = new JButton(text);
		button.setToolTipText(tooltip);

		try {
			button.setIcon(new ImageIcon(ImageIO.read(ClassLoader.getSystemResource(rawImage)), tooltip));
		} catch (Exception e) {
		}
		
		//button.setBorderPainted(false);
		//button.setContentAreaFilled(false);

		return button;
	}
}
