package cose457.controller;

import cose457.model.canvas.CanvasState;
import cose457.model.canvas.ObjectSelection;
import cose457.model.factory.interfaces.ObjectFactory;
import cose457.model.object.Object;
import cose457.view.CanvasView;
import lombok.Getter;

import java.awt.*;

public class CanvasController {
  @Getter private final CanvasView view;
  private final CanvasState state;

  public CanvasController(CanvasView view, CanvasState state) {
    this.view = view;
    this.state = state;
  }

  public void addObjects(ObjectFactory factory) {
    int nextZ = state.getNextZ();
    Object obj = factory.createObject(100, 100, 300, 200, nextZ, Color.RED);
    this.state.addObjects(obj);
    view.repaint();
  }
}
