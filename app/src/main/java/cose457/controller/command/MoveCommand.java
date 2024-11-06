package cose457.controller.command;

import cose457.model.object.DrawbleObject;
import cose457.view.CanvasView;
import java.awt.Point;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MoveCommand extends Command implements Undoable {

  private Map<DrawbleObject, Point> oldPositions = new HashMap<>();
  private Map<DrawbleObject, Point> newPositions = new HashMap<>();

  public MoveCommand(CanvasView canvasView, List<DrawbleObject> objects, int deltaX, int deltaY) {
    super(canvasView, objects);

    for (DrawbleObject obj : objects) {
      Point oldPos = new Point(obj.getX1(), obj.getY1());
      Point newPos = new Point(oldPos.x + deltaX, oldPos.y + deltaY);
      oldPositions.put(obj, oldPos);
      newPositions.put(obj, newPos);
    }
  }

  @Override
  public void execute() {
    for (DrawbleObject obj : objects) {
      Point newPos = newPositions.get(obj);
      int width = obj.getWidth();
      int height = obj.getHeight();
      obj.resize(newPos.x, newPos.y, newPos.x + width, newPos.y + height);
    }
    canvasView.repaint();
  }

  @Override
  public void undo() {
    for (DrawbleObject obj : objects) {
      Point oldPos = oldPositions.get(obj);
      int width = obj.getWidth();
      int height = obj.getHeight();
      obj.resize(oldPos.x, oldPos.y, oldPos.x + width, oldPos.y + height);
    }
    canvasView.repaint();
  }
}
