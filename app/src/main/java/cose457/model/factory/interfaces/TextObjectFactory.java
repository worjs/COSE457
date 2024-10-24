package cose457.model.factory.interfaces;

import java.awt.Color;

public interface TextObjectFactory extends ObjectFactory {
	public Object createTextObject(int x1, int y1, int x2, int y2, Color color, String text);
}
