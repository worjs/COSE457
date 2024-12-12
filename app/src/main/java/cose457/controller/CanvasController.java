package cose457.controller;

import cose457.controller.command.ChangeColorCommand;
import cose457.controller.command.ChangeZOrderCommand;
import cose457.controller.command.CommandInvoker;
import cose457.controller.command.DrawCommand;
import cose457.controller.command.MoveCommand;
import cose457.controller.command.ResizeCommand;
import cose457.controller.command.SelectCommand;
import cose457.model.canvas.CanvasState;
import cose457.model.factory.interfaces.ObjectFactory;
import cose457.model.object.DrawbleObject;
import cose457.view.CanvasView;
import java.awt.Color;
import java.awt.Rectangle;
import java.util.List;
import java.util.Map;
import lombok.Getter;
import cose457.controller.command.GroupCommand;

public class CanvasController {

  @Getter
  private final CanvasView view;

  @Getter
  private final CanvasState state;

  public CanvasController(CanvasView view, CanvasState state) {
    this.view = view;
    this.state = state;
  }

  public void drawObject(ObjectFactory factory) {
    int x1 = 100; // 시작 x좌표
    int y1 = 100; // 시작 y좌표
    int x2 = 300; // 종료 x좌표
    int y2 = 200; // 종료 y좌표
    int z = state.getNextZ(); // 레이어링을 위한 z-인덱스
    Color color = Color.RED; // 기본 색상 (필요에 따라 수정 가능)

    // DrawCommand 생성 및 실행
    DrawCommand command = new DrawCommand(view, x1, y1, x2, y2, z, color, factory);
    CommandInvoker.getInstance().executeCommand(command);
  }

  public void clearSelection() {
    List<DrawbleObject> selectedObjects = state.getSelections().getSelectedObjects();
    if (!selectedObjects.isEmpty()) {
      CommandInvoker.getInstance()
          .executeCommand(new SelectCommand(view, selectedObjects, false));
    }
  }

  public void toggleSelection(List<DrawbleObject> objects, boolean select) {
    CommandInvoker.getInstance().executeCommand(new SelectCommand(view, objects, select));
  }

  public void moveObjects(List<DrawbleObject> objects, int deltaX, int deltaY) {
    CommandInvoker.getInstance()
        .executeCommand(new MoveCommand(view, objects, deltaX, deltaY));
  }

  public void resizeObjects(List<DrawbleObject> objects, Map<DrawbleObject, Rectangle> newBounds) {
    CommandInvoker.getInstance()
        .executeCommand(new ResizeCommand(view, objects, newBounds));
  }

  public void changeColor(List<DrawbleObject> objects, Color color) {
    CommandInvoker.getInstance()
        .executeCommand(new ChangeColorCommand(view, objects, color));
  }

  public void changeZOrder(List<DrawbleObject> objects, int zOrder) {
    CommandInvoker.getInstance()
        .executeCommand(new ChangeZOrderCommand(view, objects, zOrder));
  }

public void groupSelectedObjects() {
    List<DrawbleObject> selectedObjects = state.getSelections().getSelectedObjects();
    if (selectedObjects.size() > 1) {
      CommandInvoker.getInstance().executeCommand(new GroupCommand(view, selectedObjects));
    }
  }
}
