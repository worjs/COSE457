package cose457.model.factory;

import java.awt.Color;
import java.awt.Image;

import cose457.model.factory.interfaces.ObjectFactory;
import cose457.model.object.ImageObject;
import cose457.model.object.DrawbleObject;

public class ImageFactory implements ObjectFactory {
  private static ImageFactory instance;
  private Image image;

  private ImageFactory(Image image) {
    this.image = image;
  }

  public static ImageFactory getInstance(Image image) {
    if (instance == null) {
      instance = new ImageFactory(image);
    }

    instance.image = image;
    return instance;
  }

  @Override
  public DrawbleObject createObject(int x1, int y1, int x2, int y2, int z, Color color) {
    return new ImageObject(x1, y1, x2, y2, z, color, image);
  }
}
