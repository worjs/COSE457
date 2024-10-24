package cose457.model.object;

import java.awt.Color;
import java.awt.Graphics2D;

public class RectangleObject extends Object {

	public RectangleObject(int x1, int y1, int x2, int y2, Color color) {
		super(x1, y1, x2, y2, color);
	}

	@Override
	public void draw(Graphics2D g2d) {
		g2d.setColor(this.color);
		g2d.drawRect(this.x1, this.y1, this.x2 - this.x1, this.y2 - this.y1);
	}
}
