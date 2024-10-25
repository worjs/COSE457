package cose457.model.factory;

import java.awt.Color;

import cose457.model.object.Object;
import cose457.model.factory.interfaces.ObjectFactory;
import cose457.model.object.EllipseObject;

public class EllipseFactory implements ObjectFactory {
  private static EllipseFactory instance;

  public static EllipseFactory getInstance() {
    if (instance == null) {
      instance = new EllipseFactory();
    }
    return instance;
  }

  @Override
  public Object createObject(int x1, int y1, int x2, int y2, int z, Color color) {
    return new EllipseObject(x1, y1, x2, y2, z, color);
  }
}
