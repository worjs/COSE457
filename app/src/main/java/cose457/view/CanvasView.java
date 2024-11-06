package cose457.view;

import cose457.model.canvas.CanvasState;
import cose457.model.canvas.Handle;
import cose457.model.object.DrawbleObject;
import lombok.Getter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.*;
import java.util.List;

public class CanvasView extends JPanel {
  private final CanvasState state;
  private final DragState dragState;

  public CanvasView(CanvasState state) {
    this.state = state;
    this.dragState = new DragState();

    addMouseListener(
        new MouseAdapter() {
          @Override
          public void mousePressed(MouseEvent e) {
            handleMousePressed(e);
          }

          @Override
          public void mouseReleased(MouseEvent e) {
            dragState.reset();
          }
        });

    addMouseMotionListener(
        new MouseMotionAdapter() {
          @Override
          public void mouseDragged(MouseEvent e) {
            handleMouseDragged(e);
          }
        });
  }

  private void handleMousePressed(MouseEvent e) {
    int x = e.getX();
    int y = e.getY();

    // 1. 선택된 객체들의 핸들을 검사하여 핸들을 클릭했는지 확인합니다.
    if (selectHandleIfClicked(x, y)) {
      return;
    }

    // 2. 선택된 객체 자체를 클릭했는지 확인합니다.
    if (selectObjectIfClicked(x, y)) {
      // 객체 드래그를 시작합니다.
      List<DrawbleObject> selectedObjects = state.getSelections().getSelectedObjects();
      dragState.startObjectDrag(e.getPoint(), selectedObjects);
      return;
    }

    // 3. 객체를 클릭하지 않았다면, 객체 선택 로직을 처리합니다.
    if (!selectObjectAtPoint(x, y, e.isShiftDown())) {
      // 빈 공간을 클릭한 경우 선택을 해제합니다.
      state.getSelections().clearObjects();
    }
    repaint();
  }

  private boolean selectHandleIfClicked(int x, int y) {
    // 다중 선택 상태인 경우 핸들 드래그 비활성화
    if (state.getSelections().getSelectedObjects().size() > 1) {
      return false;
    }

    for (DrawbleObject obj : state.getObjectList()) {
      if (obj.isSelected()) {
        Handle handle = obj.getClickedHandle(x, y);
        if (handle != null) {
          dragState.startHandleDrag(handle);
          return true;
        }
      }
    }
    return false;
  }

  private boolean selectObjectIfClicked(int x, int y) {
    for (DrawbleObject obj : state.getSelections().getSelectedObjects()) {
      if (obj.containsPoint(x, y)) {
        return true;
      }
    }
    return false;
  }

  private boolean selectObjectAtPoint(int x, int y, boolean isShiftDown) {
    List<DrawbleObject> objectsAtPoint = new ArrayList<>();
    for (DrawbleObject obj : state.getObjectList()) {
      if (obj.containsPoint(x, y)) {
        objectsAtPoint.add(obj);
      }
    }

    if (!objectsAtPoint.isEmpty()) {
      DrawbleObject topMostObject =
          objectsAtPoint.stream().max(Comparator.comparingInt(DrawbleObject::getZ)).get();

      if (isShiftDown) {
        toggleObjectSelection(topMostObject);
      } else {
        state.getSelections().clearObjects();
        state.getSelections().selectObject(topMostObject);
      }
      return true;
    }
    return false;
  }

  private void toggleObjectSelection(DrawbleObject obj) {
    if (obj.isSelected()) {
      state.getSelections().unselectObject(obj);
    } else {
      state.getSelections().selectObject(obj);
    }
  }

  private void handleMouseDragged(MouseEvent e) {
    if (dragState.isDragging()) {
      switch (dragState.getDragType()) {
        case HANDLE:
          // 핸들 드래그 처리
          for (DrawbleObject obj : state.getObjectList()) {
            if (obj.isSelected()) {
              obj.resizeOrRotate(dragState.getActiveHandle(), e.getX(), e.getY());
            }
          }
          break;
        case OBJECT:
          // 객체 드래그 처리
          int deltaX = e.getX() - dragState.getInitialMousePoint().x;
          int deltaY = e.getY() - dragState.getInitialMousePoint().y;

          for (DrawbleObject obj : state.getSelections().getSelectedObjects()) {
            Point initialPosition = dragState.getInitialObjectPositions().get(obj);
            if (initialPosition != null) {
              int newX1 = initialPosition.x + deltaX;
              int newY1 = initialPosition.y + deltaY;
              int newX2 = newX1 + obj.getWidth();
              int newY2 = newY1 + obj.getHeight();

              obj.resize(newX1, newY1, newX2, newY2);
            }
          }
          break;
        default:
          break;
      }
      repaint();
    }
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    for (DrawbleObject obj : state.getObjectList()) {
      obj.draw((Graphics2D) g);
      if (obj.isSelected()) {
        obj.drawHandles((Graphics2D) g);
      }
    }
  }

  @Getter
  private static class DragState {
    private DragType dragType = DragType.NONE;
    private Handle activeHandle = null;
    private Point initialMousePoint = null;
    private Map<DrawbleObject, Point> initialObjectPositions = new HashMap<>();

    public void startHandleDrag(Handle handle) {
      this.dragType = DragType.HANDLE;
      this.activeHandle = handle;
    }

    public void startObjectDrag(Point initialPoint, List<DrawbleObject> selectedObjects) {
      this.dragType = DragType.OBJECT;
      this.initialMousePoint = initialPoint;
      for (DrawbleObject obj : selectedObjects) {
        initialObjectPositions.put(obj, new Point(obj.getX1(), obj.getY1()));
      }
    }

    public void reset() {
      this.dragType = DragType.NONE;
      this.activeHandle = null;
      this.initialMousePoint = null;
      this.initialObjectPositions.clear();
    }

    public boolean isDragging() {
      return dragType != DragType.NONE;
    }
  }

  private enum DragType {
    HANDLE,
    OBJECT,
    NONE
  }
}
