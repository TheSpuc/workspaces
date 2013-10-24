package recursivesorting;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Polygon;

import javax.swing.JPanel;

public class TegnePanel extends JPanel {

	public void paintComponent(Graphics g) {
		setBackground(Color.WHITE);
		super.paintComponent(g);


		Point p1 = new Point(getWidth() / 2, 10);
		Point p2 = new Point(10, getHeight() - 10);
		Point p3 = new Point(getWidth() - 10, getHeight() - 10);

		displayTriangles(g, 13, p1, p2, p3);
	}

	private static void displayTriangles(Graphics g, int order, Point p1, Point p2, Point p3) {
		if (order == 0) {
			//one triangle to draw
			g.drawLine(p1.x, p1.y, p2.x, p2.y);
			g.drawLine(p1.x, p1.y, p3.x, p3.y);
			g.drawLine(p2.x, p2.y, p3.x, p3.y);
		}
		else {
			// Get the midpoint on each edge in the triangle
			Point p12 = midpoint(p1, p2);
			Point p23 = midpoint(p2, p3);
			Point p31 = midpoint(p3, p1);

			// Recursively display three triangles
			displayTriangles(g, order - 1, p1, p12, p31);
			displayTriangles(g, order - 1, p12, p2, p23);
			displayTriangles(g, order - 1, p31, p23, p3);
		}
	}

	private static Point midpoint(Point p1, Point p2) {
		return new Point((p1.x + p2.x) / 2, (p1.y + p2.y) / 2);
	}
}
