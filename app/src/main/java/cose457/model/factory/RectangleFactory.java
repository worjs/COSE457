package cose457.model.factory;

import java.awt.Color;

import cose457.model.factory.interfaces.ObjectFactory;
import cose457.model.object.DrawbleObject;
import cose457.model.object.RectangleObject;

public class RectangleFactory implements ObjectFactory {

  private static RectangleFactory instance;

  public static RectangleFactory getInstance() {
    if (instance == null) {
      instance = new RectangleFactory();
    }
    return instance;
  }

  @Override
  public DrawbleObject createObject(int x1, int y1, int x2, int y2, int z, Color color) {
    return new RectangleObject(x1, y1, x2, y2, z, color);
  }
}
