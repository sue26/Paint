package a2;

import java.awt.*;

import javax.swing.*;

public class ColorToolBar extends JToolBar {
	private static final long serialVersionUID = 1L;
	
	private JPanel currentColor = new JPanel();
	protected final JButton Color_Black = createButton(Color.BLACK, "Black");
	protected final JButton Color_White = createButton(Color.WHITE, "White");
	protected final JButton Color_Red = createButton(Color.RED, "Red");
	protected final JButton Color_Blue = createButton(Color.BLUE, "Blue");
	protected final JButton Color_Green = createButton(Color.GREEN, "Green");
	protected final JButton Color_Yellow = createButton(Color.YELLOW, "Yellow");
	protected final JButton Color_Purple = createButton(new Color(75, 0, 130), "Purple");
	protected final JButton Color_Gray = createButton(Color.GRAY, "Gray");
	protected final JButton Color_Orange = createButton(Color.ORANGE, "Orange");
	protected final JButton Color_Cyan = createButton(Color.CYAN, "Cyan");
	protected final JButton Color_Magenta = createButton(Color.MAGENTA, "Magenta");
	protected final JButton Color_DarkGray = createButton(Color.DARK_GRAY, "Dark Gray");
	protected final JButton moreColor = new JButton("More Colors..");

	ColorToolBar() {
		//Panel 1
		this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		currentColor.setBackground(Color.BLACK);
		currentColor.setPreferredSize(new Dimension(60,60));
		
		//Panel 2
		JPanel colors = new JPanel();
		colors.setLayout(new GridLayout(2,0));
		colors.add(Color_White);
		colors.add(Color_Magenta);
		colors.add(Color_Orange);
		colors.add(Color_Green);
		colors.add(Color_Blue);
		colors.add(Color_Gray);
		colors.add(Color_Black);
		colors.add(Color_Red);
		colors.add(Color_Yellow);
		colors.add(Color_Cyan);
		colors.add(Color_Purple);
		colors.add(Color_DarkGray);
		Color_Black.setPreferredSize(new Dimension(30,30));
		
		//Panel 3
		JPanel top = new JPanel();
		top.setLayout(new FlowLayout());
		top.add(currentColor);
		top.add(colors);
		
		//Final Panel
		moreColor.setPreferredSize(new Dimension(100,20));
		moreColor.setMinimumSize(new Dimension(100,20));
		moreColor.setMaximumSize(new Dimension(100,20));
		this.setLayout(new BorderLayout());
		this.add(top, BorderLayout.NORTH);
		this.add(moreColor, BorderLayout.SOUTH);
		
		this.setFloatable(false); //////////////////////
		colors.setBackground(new Color(255, 229, 204));
		top.setBackground(new Color(255, 229, 204));
		this.setBackground(new Color(255, 229, 204));
	}
	
	public void changeCurrentColor(Color color) {
		currentColor.setBackground(color);
	}
	
	public Color getCurrentColor() {
		return currentColor.getBackground();
	}
	
	private JButton createButton(Color color, String tooltip) {
			final JButton button = new JButton();
			button.setToolTipText(tooltip);
			button.setBackground(color);
			button.setOpaque(true);
			button.setBorder(BorderFactory.createLineBorder(Color.black));
			//button.setBorderPainted(false);
			//button.setContentAreaFilled(false);

			return button;
	}
}
