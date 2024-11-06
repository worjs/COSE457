package cose457.controller.command;

import cose457.model.object.DrawbleObject;
import cose457.view.CanvasView;
import java.util.List;

public class SelectCommand extends Command {

  private boolean select; // true면 선택, false면 선택 해제

  public SelectCommand(CanvasView canvasView, List<DrawbleObject> objects, boolean select) {
    super(canvasView, objects);
    this.select = select;
  }

  @Override
  public void execute() {
    if (select) {
      canvasView.getState().getSelections().selectObjects(objects);
    } else {
      canvasView.getState().getSelections().unselectObjects(objects);
    }
    canvasView.repaint();
  }
}
