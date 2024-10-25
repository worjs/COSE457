package cose457.model.factory;

import java.awt.Color;

import cose457.model.factory.interfaces.ObjectFactory;
import cose457.model.object.Object;
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
  public Object createObject(int x1, int y1, int x2, int y2, Color color) {
    return new RectangleObject(x1, y1, x2, y2, color);
  }
}
