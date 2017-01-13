package a2;

import java.awt.*;
import java.awt.font.*;
import java.awt.geom.*;
import java.awt.image.BufferedImage;
import java.util.*;

import javax.swing.*;


public class PaintPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	
	protected Vector<Object> v;
	protected Stack<Object> stack;
//	private ArrayList<Shape> array;
	private Stack<Shape> stack2;
	private Graphics2D g2;
	private int ignoreInt;
	private int temp;
	/////////////////////////////////////
	private HashMap<Object, Shape> map;
	/////////////////////////////////////

		
	PaintPanel() {
		/////////////////////////////////////
		map = new HashMap<Object, Shape>();
		/////////////////////////////////////
		v = new Vector<Object>();
		stack = new Stack<Object>();
		//array = new ArrayList<Shape>();
		stack2 = new Stack<Shape>();
		ignoreInt = 0;
		temp = ignoreInt;
		this.setBackground(Color.white);
		this.setPreferredSize(new Dimension(900, 900));
	}
	
	@Override
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g); // paint background
		paintEntities(g); //paint entities
	}
	
	private void paintEntities(Graphics g) {
		g2 = (Graphics2D)g;
		temp = ignoreInt;
		
		for (int i = 0; i < v.size(); i++) {
			Entity e = (Entity)v.elementAt(i);
			double xInit = e.getXInit();
			double yInit = e.getYInit();
			double xFinal = e.getXFinal();
			double yFinal = e.getYFinal();
			int thickness = e.getThickness();
			Color color = e.getColor();
			int edgeStyle = e.getEdgeStyle();
			String text = e.getText();
			Font font = e.getFont();
			boolean underlined = e.isUnderlined();
			Color fillColor = e.getFillColor();
			boolean selected = e.isSelected();
			int rotateNum = e.getRotateNum();
			BufferedImage image = e.getImage();
			
			switch (e.getType())
			{
				case Entity.FREEFORM:
					Line2D.Double pen = drawPen(xInit, yInit, xFinal, yFinal, thickness, color, edgeStyle);
					map.put(e, pen);
					break;
				case Entity.ERASER:
					Line2D.Double eraser = erase(xInit, yInit, xFinal, yFinal, thickness);
					map.put(e, eraser);
					break;
				case Entity.LINE:
					Line2D.Double line = drawLine(xInit, yInit, xFinal, yFinal, thickness, color, edgeStyle, selected, rotateNum);
					map.put(e, line);
					break;
				case Entity.RECTANGLE:
					Rectangle2D.Double rect = drawRectangle(xInit, yInit, xFinal, yFinal, thickness, color, fillColor, selected, rotateNum);
					map.put(e, rect);
					break;
				case Entity.CIRCLE:
					Ellipse2D.Double circ = drawCircle(xInit, yInit, xFinal, yFinal, thickness, color, fillColor, selected, rotateNum);
					map.put(e, circ);
					break;
				case Entity.TRIANGLE:
					GeneralPath tri = drawTriangle(xInit, yInit, xFinal, yFinal, thickness, color, fillColor, selected, rotateNum);
					map.put(e, tri);
					break;
				case Entity.STAR:
					GeneralPath star = drawStar(xInit, yInit, xFinal, yFinal, thickness, color, fillColor, selected, rotateNum);
					map.put(e, star);
					break;
				case Entity.TEXT:
					GeneralPath t = drawText(xInit, yInit, color, text, font, underlined);
					map.put(e, t);
					break;
				case Entity.ARROW:
					GeneralPath arrow = drawArrow(xInit, yInit, xFinal, yFinal, thickness, color, fillColor, selected, rotateNum);
					map.put(e, arrow);
					break;
				case Entity.DIAMOND:
					GeneralPath diamond = drawDiamond(xInit, yInit, xFinal, yFinal, thickness, color, fillColor, selected, rotateNum);
					map.put(e, diamond);
					break;
				case Entity.PENTAGON:
					GeneralPath pentagon = drawPentagon(xInit, yInit, xFinal, yFinal, thickness, color, fillColor, selected, rotateNum);
					map.put(e, pentagon);
					break;
				case Entity.IMAGE:
					GeneralPath im = drawImage(image);
					map.put(e, im);
					break;
			}
		}
	}
	
	public Line2D.Double drawPen(double xInitial, double yInitial, double dragX, double dragY, int thickness, Color color,
			int edgeStyle) {
		Line2D.Double pen;
			
		pen = new Line2D.Double(xInitial, yInitial, dragX, dragY);
		g2.setColor(color);
		g2.setStroke(new BasicStroke(thickness, edgeStyle, BasicStroke.JOIN_ROUND));
		g2.draw(pen);
		
		
		return pen;
	}
	
	public Line2D.Double erase(double xInitial, double yInitial, double dragX, double dragY, int thickness) {
		Line2D.Double eraser;
			
		eraser = new Line2D.Double(xInitial, yInitial, dragX, dragY);
		g2.setColor(Color.white);
		g2.setStroke(new BasicStroke(thickness, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
		g2.draw(eraser);
		
	
		return eraser;
			
	}
	
	public Line2D.Double drawLine(double xInitial, double yInitial, double xFinal, double yFinal, int thickness, Color color,
			int edgeStyle, boolean selected, int rotateNum) {
		Line2D.Double line = new Line2D.Double(xInitial, yInitial, xFinal, yFinal);

	    Shape temp2 = rotateShape(line, Math.toRadians(90 * rotateNum), (xInitial + xFinal)/2, (yInitial + yFinal)/2);

		if (selected)
	    {
			g2.setColor(Color.red);
			g2.setStroke(new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{9}, 0)); //dashed line
		    g2.draw(getRectBounds(temp2));
	    }
		
		g2.setColor(color);
		g2.setStroke(new BasicStroke(thickness, edgeStyle, BasicStroke.JOIN_ROUND));
		g2.draw(temp2);

		
		
		return line;
	}
	
	public Rectangle2D.Double drawRectangle(double xInitial, double yInitial, double xFinal, double yFinal, int thickness, Color color, 
			Color fillColor, boolean selected, int rotateNum) {
		Rectangle2D.Double rect = new Rectangle2D.Double(xInitial, yInitial, xFinal - xInitial, yFinal - yInitial);
		
	    Shape temp2 = rotateShape(rect, Math.toRadians(90 * rotateNum), (xInitial + xFinal)/2, (yInitial + yFinal)/2);

		if (selected)
	    {
			g2.setColor(Color.red);
			g2.setStroke(new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{9}, 0)); //dashed line
		    g2.draw(getRectBounds(temp2));
	    }
		
		if (fillColor != null) {
			g2.setColor(fillColor);
			g2.fill(temp2);
		}
		
		g2.setColor(color);
		g2.setStroke(new BasicStroke(thickness));
		g2.draw(temp2);
		

		
		return rect;
	}
	
	public Ellipse2D.Double drawCircle(double xInitial, double yInitial, double xFinal, double yFinal, int thickness, Color color,
			Color fillColor, boolean selected, int rotateNum) {
		Ellipse2D.Double circ = new Ellipse2D.Double(xInitial, yInitial, xFinal - xInitial, yFinal - yInitial);
		
		Shape temp2 = rotateShape(circ, Math.toRadians(90 * rotateNum), (xInitial + xFinal)/2, (yInitial + yFinal)/2);

		if (selected)
	    {
			g2.setColor(Color.red);
			g2.setStroke(new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{9}, 0)); //dashed line
		    g2.draw(temp2.getBounds2D());
	    }
		
		if (fillColor != null) {
			g2.setColor(fillColor);
			g2.fill(temp2);
		}
		g2.setColor(color);
		g2.setStroke(new BasicStroke(thickness));
		g2.draw(temp2);
		
	
		
		return circ;
	}
	
	public GeneralPath drawTriangle(double xInitial, double yInitial, double xFinal, double yFinal, int thickness, Color color,
			Color fillColor, boolean selected, int rotateNum) {
		GeneralPath tri = new GeneralPath();
		tri.moveTo((xInitial + (xFinal - xInitial)/2), yInitial);
	    tri.lineTo(xFinal, yFinal);
	    tri.lineTo(xInitial, yFinal);
	    tri.closePath();
	    
	    Shape temp2 = rotateShape(tri, Math.toRadians(90 * rotateNum), (xInitial + xFinal)/2, (yInitial + yFinal)/2);

	    if (selected)
	    {
			g2.setColor(Color.red);
			g2.setStroke(new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{9}, 0)); //dashed line
		    g2.draw(temp2.getBounds2D());
	    }
	    
	    if (fillColor != null) 
	    {
	    	g2.setColor(fillColor);
	    	g2.fill(temp2);
	    }
	   
	    g2.setColor(color);
		g2.setStroke(new BasicStroke(thickness));
	    g2.draw(temp2);
	    
	
	    return tri;
	}
	
	public GeneralPath drawStar(double xInitial, double yInitial, double xFinal, double yFinal, int thickness, Color color,
			Color fillColor, boolean selected, int rotateNum) {
		GeneralPath star = new GeneralPath();
		double dx = xFinal - xInitial;
		double dy = yFinal - yInitial;
		
		star.moveTo((xInitial + dx/2), yInitial);
		star.lineTo((xInitial + dx*9/14), (yInitial + dy*5/14));
		star.lineTo(xFinal, (yInitial + dy*5.5/14));
		star.lineTo((xInitial + dx*10.5/14), (yInitial + dy*9/14));
		star.lineTo((xInitial + dx*12/14), yFinal);
		star.lineTo((xInitial + dx/2), (yInitial + dy*11.5/14));
		star.lineTo((xInitial + dx*2/14), yFinal);
		star.lineTo((xInitial + dx*3.5/14), (yInitial + dy*9/14));
		star.lineTo(xInitial, (yInitial + dy*5.5/14));
		star.lineTo((xInitial + dx*5/14), (yInitial + dy*5/14));
		star.closePath();
		
		Shape temp2 = rotateShape(star, Math.toRadians(90 * rotateNum), (xInitial + xFinal)/2, (yInitial + yFinal)/2);

		if (selected)
	    {
			g2.setColor(Color.red);
			g2.setStroke(new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{9}, 0)); //dashed line
		    g2.draw(temp2.getBounds2D());
	    }
		
		if (fillColor != null) {
			g2.setColor(fillColor);
			g2.fill(temp2);
		}
		
	    g2.setColor(color);
		g2.setStroke(new BasicStroke(thickness));
		g2.draw(temp2);
		
		return star;
	}
	
	public GeneralPath drawText(double xInitial, double yInitial, Color color, String text, Font font, boolean underlined) {
		Map<TextAttribute,Object> attr=new Hashtable<TextAttribute,Object>();
		attr.put(TextAttribute.UNDERLINE,TextAttribute.UNDERLINE_ON);
		if (underlined) {
			attr.remove(TextAttribute.UNDERLINE); 
			attr.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
		} else {
			attr.remove(TextAttribute.UNDERLINE); 
			attr.remove(TextAttribute.UNDERLINE); attr.put(TextAttribute.UNDERLINE, new Integer(-1));
		}
		font=font.deriveFont(attr);
		g2.setColor(color);
		g2.setFont(font);
		g2.drawString(text, (int)xInitial, (int)yInitial);
		
		GeneralPath tempText = new GeneralPath();
		return tempText;
	}
	
	public GeneralPath drawArrow(double xInitial, double yInitial, double xFinal, double yFinal, int thickness, Color color,
			Color fillColor, boolean selected, int rotateNum) {
		GeneralPath arrow = new GeneralPath();
		double dx = xFinal - xInitial;
		double dy = yFinal - yInitial;
		
		arrow.moveTo(xInitial, (yInitial + dy/3));
		arrow.lineTo((xInitial + dx*5/7), (yInitial + dy/3));
		arrow.lineTo((xInitial + dx*5/7), yInitial);
		arrow.lineTo(xFinal, (yInitial + dy/2));
		arrow.lineTo((xInitial + dx*5/7), yFinal);
		arrow.lineTo((xInitial + dx*5/7), (yInitial + dy*2/3));
		arrow.lineTo(xInitial, (yInitial + dy*2/3));
		arrow.closePath();
		
		Shape temp2 = rotateShape(arrow, Math.toRadians(90 * rotateNum), (xInitial + xFinal)/2, (yInitial + yFinal)/2);

		if (selected)
	    {
			g2.setColor(Color.red);
			g2.setStroke(new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{9}, 0)); //dashed line
		    g2.draw(temp2.getBounds2D());
	    }
		
		if (fillColor != null) {
			g2.setColor(fillColor);
			g2.fill(temp2);
		}
		
	    g2.setColor(color);
		g2.setStroke(new BasicStroke(thickness));
		g2.draw(temp2);
		
		
		return arrow;
	}
	
	public GeneralPath drawDiamond(double xInitial, double yInitial, double xFinal, double yFinal, int thickness, Color color,
			Color fillColor, boolean selected, int rotateNum) {
		GeneralPath diamond = new GeneralPath();
		double xMiddle = (xFinal + xInitial) / 2;
		double yMiddle = (yFinal + yInitial) / 2;
		
		diamond.moveTo(xMiddle, yInitial);
		diamond.lineTo(xFinal, yMiddle);
		diamond.lineTo(xMiddle, yFinal);
		diamond.lineTo(xInitial, yMiddle);
		diamond.closePath();
		
		Shape temp2 = rotateShape(diamond, Math.toRadians(90 * rotateNum), (xInitial + xFinal)/2, (yInitial + yFinal)/2);

		if (selected)
	    {
			g2.setColor(Color.red);
			g2.setStroke(new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{9}, 0)); //dashed line
		    g2.draw(temp2.getBounds2D());
	    }
		
		if (fillColor != null) {
			g2.setColor(fillColor);
			g2.fill(temp2);
		}
		
	    g2.setColor(color);
		g2.setStroke(new BasicStroke(thickness));
		g2.draw(temp2);
		
	
		
		return diamond;
	}
	
	public GeneralPath drawPentagon(double xInitial, double yInitial, double xFinal, double yFinal, int thickness, Color color,
			Color fillColor, boolean selected, int rotateNum) {
		GeneralPath pentagon = new GeneralPath();
		double dx = xFinal - xInitial;
		double dy = yFinal - yInitial;
		
		pentagon.moveTo((xInitial + dx/2), yInitial);
		pentagon.lineTo(xFinal, (yInitial + dy*3/7));
		pentagon.lineTo((xInitial + dx*3/4), yFinal);
		pentagon.lineTo((xInitial + dx*1/4), yFinal);
		pentagon.lineTo(xInitial, (yInitial + dy*3/7));
		pentagon.closePath();
		
		Shape temp2 = rotateShape(pentagon, Math.toRadians(90 * rotateNum), (xInitial + xFinal)/2, (yInitial + yFinal)/2);

		if (selected)
	    {
			g2.setColor(Color.red);
			g2.setStroke(new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{9}, 0)); //dashed line
		    g2.draw(temp2.getBounds2D());
	    }
		
		if (fillColor != null) {
			g2.setColor(fillColor);
			g2.fill(temp2);
		}
		
	    g2.setColor(color);
		g2.setStroke(new BasicStroke(thickness));
		g2.draw(temp2);

		
		
		return pentagon;
	}
	
	public GeneralPath drawImage(BufferedImage image) {
		g2.drawImage(image, null, null);
		
		GeneralPath temp = new GeneralPath();
		return temp;
	}
	
	public void drawEntity(Object e, int type, double xInit, double yInit, double xFi, double yFi, int thickness,
			Color color, int edgeStyle, String text, Font font, boolean underlined, Color fillColor, boolean selected,
			int rotateRight, int rotateLeft, BufferedImage image)
	{
		v.addElement(new Entity(e, type, xInit, yInit, xFi, yFi, thickness, color, edgeStyle, text, font, underlined, fillColor,
				selected, rotateRight, rotateLeft, image));
		this.repaint();
	}
	
	public void undo() {
		if (!v.isEmpty()) {
			Object element = v.get(v.size() - 1);
			stack.push(element);
			v.remove(v.size() - 1);
			this.repaint();
			
			int type = ((Entity)element).getType();
			if (type == 6 || type == 7 || type == 10 || type == 11 || type == 12)
				ignoreInt--;
			
			if (map.containsKey(element)) {
				map.remove(element);

			}
		}
		
	/*	if (!array.isEmpty()) {
			Shape element = array.get(array.size() - 1);
			stack2.push(element);
			array.remove(array.size() - 1);
		}*/
	}
	
	public void redo() {
		if (!stack.isEmpty()) {
			Object element = stack.pop();
			v.add(element);
			this.repaint();
			
			int type = ((Entity)element).getType();
			if (type == 6 || type == 7 || type == 10 || type == 11 || type == 12)
				ignoreInt++;
		}
		/*if (!stack2.isEmpty()) {
			Shape element = stack2.pop();
			array.add(element);
		}*/
	}
	
	public void clear() {
		v.removeAllElements();
		this.repaint();
	}
	
	public void select(int x, int y) {
		boolean noneSelected = true;
		boolean done = false;
		
		for (Object key: map.keySet()) {
		}
		for (int i = (v.size() - 1); i > -1; i--) {
			Object o = v.elementAt(i);
			
			if (o instanceof Entity) 
			{
				switch (((Entity)v.elementAt(i)).getType()) 
				{
					case Entity.RECTANGLE:
						if (!done && map.get(v.elementAt(i)) instanceof Rectangle2D.Double) {
							Rectangle2D.Double rect = (Rectangle2D.Double)map.get(v.elementAt(i));
							if (rect.contains(x,y)) {
								for (Object o2: v) {
									((Entity)o2).setSelected(false);
								}
								((Entity) o).setSelected(true);
								repaint();
								done = true;
								noneSelected = false;
							}
						}
						break;
						
					case Entity.CIRCLE:
						if (!done && map.get(v.elementAt(i)) instanceof Ellipse2D.Double) {
							Ellipse2D.Double circ = (Ellipse2D.Double)map.get(v.elementAt(i));
							if (circ.contains(x,y)) {
								for (Object o2: v) {
									((Entity)o2).setSelected(false);
								}
								((Entity) o).setSelected(true);
								repaint();
								done = true;
								noneSelected = false;
							}
						}
						break;
						
					case Entity.TRIANGLE:
						if (!done && map.get(v.elementAt(i)) instanceof GeneralPath) {
							GeneralPath tri = (GeneralPath)map.get(v.elementAt(i));
							if (tri.contains(x,y)) {
								for (Object o2: v) {
									((Entity)o2).setSelected(false);
								}
								((Entity) o).setSelected(true);								
								repaint();
								done = true;
								noneSelected = false;
							}
						}
						break;
						
					case Entity.STAR:
						if (!done && map.get(v.elementAt(i)) instanceof GeneralPath) {
							GeneralPath star = (GeneralPath)map.get(v.elementAt(i));
							if (star.contains(x,y)) {
								for (Object o2: v) {
									((Entity)o2).setSelected(false);
								}
								((Entity) o).setSelected(true);
								repaint();
								done = true;
								noneSelected = false;
							}
						}
						break;
						
					case Entity.ARROW:
						if (!done && map.get(v.elementAt(i)) instanceof GeneralPath) {
							GeneralPath arrow = (GeneralPath)map.get(v.elementAt(i));
							if (arrow.contains(x,y)) {
								for (Object o2: v) {
									((Entity)o2).setSelected(false);
								}
								((Entity) o).setSelected(true);
								repaint();
								done = true;
								noneSelected = false;
							}
						}
						break;
						
					case Entity.DIAMOND:
						if (!done && map.get(v.elementAt(i)) instanceof GeneralPath) {
							GeneralPath dia = (GeneralPath)map.get(v.elementAt(i));
							if (dia.contains(x,y)) {
								for (Object o2: v) {
									((Entity)o2).setSelected(false);
								}
								((Entity) o).setSelected(true);
								repaint();
								done = true;
								noneSelected = false;
							}
						}
						break;
						
					case Entity.PENTAGON:
						if (!done && map.get(v.elementAt(i)) instanceof GeneralPath) {
							GeneralPath pent = (GeneralPath)map.get(v.elementAt(i));
							if (pent.contains(x,y)) {
								for (Object o2: v) {
									((Entity)o2).setSelected(false);
								}
								((Entity) o).setSelected(true);
								repaint();
								done = true;
								noneSelected = false;
							}
						}
						break;
					
					case Entity.LINE:
						if (!done && map.get(v.elementAt(i)) instanceof Line2D.Double) {
							Line2D.Double line = (Line2D.Double)map.get(v.elementAt(i));
							if (line.getBounds().contains(x,y)) {
								for (Object o2: v) {
									((Entity)o2).setSelected(false);
								}
								((Entity) o).setSelected(true);
								repaint();
								done = true;
								noneSelected = false;
							}
						}
						break;
				}
			}
			if (noneSelected) {
				unselectAll();
				repaint();
			}
		}
	}
	
	public void unselectAll() {
		for (Object o: v) {
			Entity e = (Entity)o;
			e.setSelected(false);
		}
		repaint();
	}
	
	public void fill(int x, int y, Color fillColor) {
		boolean noneSelected = true;
		boolean done = false;

		for (int i = (v.size() - 1); i > -1; i--) {
			Object o = v.elementAt(i);
			
			if (o instanceof Entity) 
			{
				switch (((Entity)v.elementAt(i)).getType()) 
				{
					case Entity.RECTANGLE:
						if (!done && (map.get(v.elementAt(i)) instanceof Rectangle2D.Double)) {
							Rectangle2D.Double rect = (Rectangle2D.Double)map.get(v.elementAt(i));
							if (rect.contains(x,y)) {
								((Entity) o).setFillColor(fillColor);
								repaint();
								done = true;
								noneSelected = false;
							}
						}
						break;
						
					case Entity.CIRCLE:
						if (!done && (map.get(v.elementAt(i)) instanceof Ellipse2D.Double)) {
							Ellipse2D.Double circ = (Ellipse2D.Double)map.get(v.elementAt(i));
							if (circ.contains(x,y)) {
								((Entity) o).setFillColor(fillColor);
								repaint();
								done = true;
								noneSelected = false;
							}
						}
						break;
						
					case Entity.TRIANGLE:
						if (!done && (map.get(v.elementAt(i)) instanceof GeneralPath)) {
							GeneralPath tri = (GeneralPath)map.get(v.elementAt(i));
							if (tri.contains(x,y)) {
								((Entity) o).setFillColor(fillColor);
								repaint();
								done = true;
								noneSelected = false;
							}
						}
						break;
						
					case Entity.STAR:
						if (!done && (map.get(v.elementAt(i)) instanceof GeneralPath)) {
							GeneralPath star = (GeneralPath)map.get(v.elementAt(i));
							if (star.contains(x,y)) {
								((Entity) o).setFillColor(fillColor);
								repaint();
								done = true;
								noneSelected = false;
							}
						}
						break;
						
					case Entity.ARROW:
						if (!done && (map.get(v.elementAt(i)) instanceof GeneralPath)) {
							GeneralPath arrow = (GeneralPath)map.get(v.elementAt(i));
							if (arrow.contains(x,y)) {
								((Entity) o).setFillColor(fillColor);
								repaint();
								done = true;
								noneSelected = false;
							}
						}
						break;
						
					case Entity.DIAMOND:
						if (!done && (map.get(v.elementAt(i)) instanceof GeneralPath)) {
							GeneralPath dia = (GeneralPath)map.get(v.elementAt(i));
							if (dia.contains(x,y)) {
								((Entity) o).setFillColor(fillColor);
								repaint();
								done = true;
								noneSelected = false;
							}
						}
						break;
						
					case Entity.PENTAGON:
						if (!done && (map.get(v.elementAt(i)) instanceof GeneralPath)) {
							GeneralPath pent = (GeneralPath)map.get(v.elementAt(i));
							if (pent.contains(x,y)) {
								((Entity) o).setFillColor(fillColor);
								repaint();
								done = true;
								noneSelected = false;
							}
						}
						break;
				}
			}
		}
		if (noneSelected) {
			this.setBackground(fillColor);
		}
	}
	
	public void rotateRight() {
		for (Object o: v) {
			Entity e = (Entity)o;
			if (e.isSelected()) {
				e.addRotateRight();
				repaint();
				break;
			}
		}
	}
	
	public void rotateLeft() {
		for (Object o: v) {
			Entity e = (Entity)o;
			if (e.isSelected()) {
				e.addRotateLeft();
				repaint();
				break;
			}
		}
	}
	
	private static Shape rotateShape(Shape base, double radian, double x, double y) {
		AffineTransform rotate = AffineTransform.getRotateInstance(radian, x, y);
		Shape result = rotate.createTransformedShape(base);
		return result;
	}
	
	private static Rectangle2D.Double getRectBounds(Shape base) {
		int b = 15;
		Rectangle2D orig = base.getBounds2D();
		Rectangle2D.Double bound = new Rectangle2D.Double(orig.getMinX() - b, orig.getMinY() - b, 
				orig.getWidth() + 2 * b, orig.getHeight() + 2 * b);
		return bound;
	}
	
	/**
	 * private class: ENtity
	 * @author SueBae
	 *
	 */
	protected class Entity
	{
		final static int FREEFORM = 1;
		final static int ERASER = 2;
		final static int LINE = 3;
		final static int RECTANGLE = 4;
		final static int CIRCLE = 5;
		final static int TRIANGLE = 6;
		final static int STAR = 7;
		final static int TEXT = 9;
		final static int ARROW = 10;
		final static int DIAMOND = 11;
		final static int PENTAGON = 12;
		final static int IMAGE = 13;

		private Object ent; // entity to paint (may be null)
		private int type; // type of entity
		private double xInit;
		private double yInit;
		private double xFi;
		private double yFi;
		private int thickness;
		private Color color;
		private int edgeStyle;
		private String text;
		private Font font;
		private boolean underlined;
		private Color fillColor;
		private boolean selected;
		private int rotateRight;
		private int rotateLeft;
		BufferedImage image;

		Entity(Object entArg, int typeArg, double xInit, double yInit, double xFi, double yFi, int thickness, Color color,
				int edgeStyle, String text, Font font, boolean underlined, Color fillColor, boolean selected, int rotateRight,
				int rotateLeft, BufferedImage image)
		{
			ent = entArg;
			type = typeArg;
			this.xInit = xInit;
			this.yInit = yInit;
			this.xFi = xFi;
			this.yFi = yFi;
			this.thickness = thickness;
			this.color = color;
			this.edgeStyle = edgeStyle;
			this.text = text;
			this.font = font;
			this.underlined = underlined;
			this.fillColor = fillColor;
			this.selected = selected;
			this.rotateRight = rotateRight;
			this.rotateLeft = rotateLeft;
			this.image = image;
		}

		public Object getEntity()
		{
			return ent;
		}

		public int getType()
		{
			return type;
		}

		public double getXInit()
		{
			return xInit;
		}

		public double getYInit()
		{
			return yInit;
		}
		
		public double getXFinal()
		{
			return xFi;
		}
		
		public double getYFinal()
		{
			return yFi;
		}
		
		public int getThickness()
		{
			return thickness;
		}
		
		public Color getColor() 
		{
			return color;
		}
		
		public int getEdgeStyle()
		{
			return edgeStyle;
		}
		
		public String getText()
		{
			return text;
		}
		
		public Font getFont()
		{
			return font;
		}
		
		public boolean isUnderlined() 
		{
			return underlined;
		}
		
		public Color getFillColor() 
		{
			return fillColor;
		}
		
		public void setFillColor(Color fillColor)
		{
			this.fillColor = fillColor;
		}
		
		public boolean isSelected() 
		{
			return selected;
		}
		
		public void setSelected(boolean selected) 
		{
			this.selected = selected;
		}
		
		public int getRotateNum() 
		{
			return rotateRight - rotateLeft;
		}
		
		public void addRotateRight() 
		{
			this.rotateRight++;
		}
		
		public void addRotateLeft() 
		{
			this.rotateLeft++;
		}
		
		public BufferedImage getImage()
		{
			return image;
		}
	}
}

