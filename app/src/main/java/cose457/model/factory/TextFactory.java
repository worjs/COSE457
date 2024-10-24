package cose457.model.factory;

import java.awt.Color;

import cose457.model.object.Object;
import cose457.model.object.TextObject;
import cose457.model.factory.interfaces.TextObjectFactory;

public class TextFactory implements TextObjectFactory {

	private TextFactory() {
	}

	private static TextFactory instance;

	public static TextFactory getInstance() {
		if (instance == null) {
			instance = new TextFactory();
		}
		return instance;
	}

	@Override
	public Object createObject(int x1, int y1, int x2, int y2, Color color) {
		return new TextObject(x1, y1, x2, y2, color, "");
	}

	@Override
	public Object createTextObject(int x1, int y1, int x2, int y2, Color color, String text) {
		return new TextObject(x1, y1, x2, y2, color, text);
	}
}