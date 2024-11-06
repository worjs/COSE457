package cose457.controller.command;

import cose457.model.object.DrawbleObject;
import cose457.view.CanvasView;
import java.util.List;

public abstract class Command {

  protected CanvasView canvasView;
  protected List<DrawbleObject> objects;

  public Command(CanvasView canvasView, List<DrawbleObject> objects) {
    this.canvasView = canvasView;
    this.objects = objects;
  }

  public abstract void execute();


}
