package cose457.controller.command;

import cose457.model.object.DrawbleObject;
import cose457.view.CanvasView;
import java.awt.Color;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChangeColorCommand extends Command implements Undoable {

  private Map<DrawbleObject, Color> oldColors = new HashMap<>();
  private Color newColor;

  public ChangeColorCommand(CanvasView canvasView, List<DrawbleObject> objects, Color newColor) {
    super(canvasView, objects);
    this.newColor = newColor;

    for (DrawbleObject obj : objects) {
      oldColors.put(obj, obj.getColor());
    }
  }

  @Override
  public void execute() {
    for (DrawbleObject obj : objects) {
      obj.setColor(newColor);
    }
    canvasView.repaint();
  }

  @Override
  public void undo() {
    for (DrawbleObject obj : objects) {
      obj.setColor(oldColors.get(obj));
    }
    canvasView.repaint();
  }
}
