package cose457.controller.command;

import cose457.model.object.DrawbleObject;
import cose457.view.CanvasView;
import java.awt.Rectangle;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ResizeCommand extends Command implements Undoable {

  private Map<DrawbleObject, Rectangle> oldBounds = new HashMap<>();
  private Map<DrawbleObject, Rectangle> newBounds = new HashMap<>();

  public ResizeCommand(CanvasView canvasView, List<DrawbleObject> objects, Map<DrawbleObject, Rectangle> newBounds) {
    super(canvasView, objects);

    for (DrawbleObject obj : objects) {
      Rectangle oldBound = new Rectangle(obj.getX1(), obj.getY1(), obj.getWidth(), obj.getHeight());
      this.oldBounds.put(obj, oldBound);
      this.newBounds.put(obj, newBounds.get(obj));
    }
  }

  @Override
  public void execute() {
    for (DrawbleObject obj : objects) {
      Rectangle bounds = newBounds.get(obj);
      obj.resize(bounds.x, bounds.y, bounds.x + bounds.width, bounds.y + bounds.height);
    }
    canvasView.repaint();
  }

  @Override
  public void undo() {
    for (DrawbleObject obj : objects) {
      Rectangle bounds = oldBounds.get(obj);
      obj.resize(bounds.x, bounds.y, bounds.x + bounds.width, bounds.y + bounds.height);
    }
    canvasView.repaint();
  }
}
