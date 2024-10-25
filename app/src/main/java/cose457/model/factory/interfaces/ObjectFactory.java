package cose457.model.factory.interfaces;

import java.awt.Color;

import cose457.model.object.Object;

public interface ObjectFactory {
  public Object createObject(int x1, int y1, int x2, int y2, int z, Color color);
}
