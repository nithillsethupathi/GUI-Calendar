
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;

import javax.swing.Icon;

/**
 * Sets up the circle icon that will be used in the application
 * @author Dangerous
 */
public class CircleIcon implements Icon
{
	private int radius;
	private Color color;

	/**
	 * create a CircleIcon with radius and color
	 * @param radius the radius of the circle
	 * @param color  the color of the circle
	 */
	public CircleIcon(int radius, Color color) 
	{
		this.radius = radius;
		this.color 	= color;
	}
	@Override
	public void paintIcon(Component c, Graphics g, int x, int y) {
		Graphics2D g2 = (Graphics2D) g;
		Ellipse2D circle = new Ellipse2D.Double(x,y,radius,radius);
		g2.setColor(color);
		g2.fill(circle);
	}

	@Override
	public int getIconWidth() 
	{
		return radius;
	}

	@Override
	public int getIconHeight() {
		return radius;
	}

	
}
