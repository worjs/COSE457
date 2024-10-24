package cose457.model.factory;

import java.awt.Color;
import java.awt.Image;

import cose457.model.object.ImageObject;
import cose457.model.factory.interfaces.ImageObjectFactory;
import cose457.model.object.Object;

public class ImageFactory implements ImageObjectFactory {
	private ImageFactory() {
	}

	private static ImageFactory instance;

	public static ImageFactory getInstance() {
		if (instance == null) {
			instance = new ImageFactory();
		}
		return instance;
	}

	@Override
	public Object createImageObject(int x1, int y1, int x2, int y2, Color color, Image image) {
		return new ImageObject(x1, y1, x2, y2, color, image);
	}

	@Override
	public Object createObject(int x1, int y1, int x2, int y2, Color color) {
		return new ImageObject(x1, y1, x2, y2, color, null);
	}
}
