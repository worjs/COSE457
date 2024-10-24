package cose457.model.factory.interfaces;

import java.awt.Color;
import java.awt.Image;

public interface ImageObjectFactory extends ObjectFactory {
	public Object createImageObject(int x1, int y1, int x2, int y2, Color color, Image image);
}
