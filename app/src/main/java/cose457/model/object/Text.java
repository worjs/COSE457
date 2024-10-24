package cose457.model.object;

import java.awt.Color;

public class Text extends Object {

	private String text;

	public Text(int x1, int y1, int x2, int y2, Color color, String text) {
		super(x1, y1, x2, y2, color);
		this.text = text;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

}
