package cose457.controller;

import cose457.model.CanvasState;
import cose457.model.ObjectSelection;
import cose457.model.factory.interfaces.ObjectFactory;
import cose457.model.object.Object;
import cose457.view.CanvasView;

import java.awt.*;

public class CanvasController {
  private final CanvasView view;
  private final CanvasState state;

  public CanvasController(CanvasView view, CanvasState state) {
    this.view = view;
    this.state = state;
  }

  public void addObjects(ObjectFactory factory) {
    Object obj = factory.createObject(100, 100, 300, 200, Color.RED);
    this.getSelection().selectObject(obj);
    state.addObjects(obj);
    view.repaint();
  }

  public ObjectSelection getSelection() {
    return state.getSelections();
  }

  public CanvasView getView() {
    return view;
  }
}
