package cose457.controller.command;

import cose457.model.object.DrawbleObject;
import cose457.view.CanvasView;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChangeZOrderCommand extends Command implements Undoable {

  private Map<DrawbleObject, Integer> oldZOrders = new HashMap<>();
  private int newZOrder;

  public ChangeZOrderCommand(CanvasView canvasView, List<DrawbleObject> objects, int newZOrder) {
    super(canvasView, objects);
    this.newZOrder = newZOrder;

    for (DrawbleObject obj : objects) {
      oldZOrders.put(obj, obj.getZ());
    }
  }

  @Override
  public void execute() {
    for (DrawbleObject obj : objects) {
      obj.setZ(newZOrder);
    }
    canvasView.repaint();
  }

  @Override
  public void undo() {
    for (DrawbleObject obj : objects) {
      obj.setZ(oldZOrders.get(obj));
    }
    canvasView.repaint();
  }
}
