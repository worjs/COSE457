package cose457.model.object;

import java.awt.Color;
import java.awt.Graphics2D;

public class TextObject extends Object {

	private String text;

	public TextObject(int x1, int y1, int x2, int y2, Color color, String text) {
		super(x1, y1, x2, y2, color);
		this.text = text;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	@Override
	public void draw(Graphics2D g2d) {
		g2d.setColor(this.color);
		g2d.drawString(this.text, this.x1, this.y1);
	}
}
