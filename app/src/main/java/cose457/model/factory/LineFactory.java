package cose457.model.factory;

import java.awt.Color;

import cose457.model.object.Object;
import cose457.model.factory.interfaces.ObjectFactory;
import cose457.model.object.LineObject;

public class LineFactory implements ObjectFactory {

  private static LineFactory instance;

  public static LineFactory getInstance() {
    if (instance == null) {
      instance = new LineFactory();
    }
    return instance;
  }

  @Override
  public Object createObject(int x1, int y1, int x2, int y2, Color color) {
    return new LineObject(x1, y1, x2, y2, color);
  }
}
