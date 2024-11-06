package cose457.view;

import cose457.controller.command.CommandInvoker;
import cose457.controller.command.MoveCommand;
import cose457.controller.command.ResizeCommand;
import cose457.controller.command.SelectCommand;
import cose457.model.canvas.CanvasState;
import cose457.model.canvas.Handle;
import cose457.model.object.DrawbleObject;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JPanel;
import lombok.Getter;

@Getter
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
            handleMouseReleased(e);
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

    // Check if a handle was clicked on a selected object
    if (selectHandleIfClicked(x, y)) {
      return;
    }

    // Check if a selected object was clicked
    if (selectObjectIfClicked(x, y)) {
      // Start dragging the selected objects
      List<DrawbleObject> selectedObjects = state.getSelections().getSelectedObjects();
      dragState.startObjectDrag(e.getPoint(), selectedObjects);
      return;
    }

    // If no object was clicked, handle object selection logic
    if (!selectObjectAtPoint(x, y, e.isShiftDown())) {
      // If clicked on empty space, clear selection
      if (!e.isShiftDown()) {
        CommandInvoker.getInstance().executeCommand(
            new SelectCommand(this, state.getSelections().getSelectedObjects(), false));
      }
    }
    repaint();
  }

  private boolean selectHandleIfClicked(int x, int y) {
    // Disable handle drag when multiple objects are selected
    if (state.getSelections().getSelectedObjects().size() > 1) {
      return false;
    }

    for (DrawbleObject obj : state.getObjectList()) {
      if (obj.isSelected()) {
        Handle handle = obj.getClickedHandle(x, y);
        if (handle != null) {
          dragState.startHandleDrag(handle, obj);
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
        // Toggle selection
        List<DrawbleObject> objs = Arrays.asList(topMostObject);
        CommandInvoker.getInstance().executeCommand(
            new SelectCommand(this, objs, !topMostObject.isSelected()));
      } else {
        // Clear previous selection and select the new object
        List<DrawbleObject> previousSelection = new ArrayList<>(state.getSelections().getSelectedObjects());
        if (!previousSelection.isEmpty()) {
          CommandInvoker.getInstance().executeCommand(
              new SelectCommand(this, previousSelection, false));
        }
        CommandInvoker.getInstance().executeCommand(
            new SelectCommand(this, Arrays.asList(topMostObject), true));
      }
      return true;
    }
    return false;
  }

  private void handleMouseDragged(MouseEvent e) {
    if (dragState.isDragging()) {
      switch (dragState.getDragType()) {
        case HANDLE:
          // Handle drag
          dragState.updateHandleDrag(e.getX(), e.getY());
          break;
        case OBJECT:
          // Object drag
          dragState.updateObjectDrag(e.getPoint());
          break;
        default:
          break;
      }
      repaint();
    }
  }

  private void handleMouseReleased(MouseEvent e) {
    if (dragState.isDragging()) {
      switch (dragState.getDragType()) {
        case HANDLE:
          // Execute ResizeCommand
          Map<DrawbleObject, Rectangle> newBounds = new HashMap<>();
          newBounds.put(dragState.getActiveObject(), dragState.getActiveObject().getBounds());
          ResizeCommand resizeCommand = new ResizeCommand(this, Arrays.asList(dragState.getActiveObject()), newBounds);
          CommandInvoker.getInstance().executeCommand(resizeCommand);
          break;
        case OBJECT:
          // Execute MoveCommand
          int deltaX = e.getX() - dragState.getInitialMousePoint().x;
          int deltaY = e.getY() - dragState.getInitialMousePoint().y;
          MoveCommand moveCommand = new MoveCommand(this,
              new ArrayList<>(dragState.getInitialObjectPositions().keySet()), deltaX, deltaY);
          CommandInvoker.getInstance().executeCommand(moveCommand);
          break;
        default:
          break;
      }
      dragState.reset();
    }
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    // Sort objects by z-index before drawing
    List<DrawbleObject> sortedObjects = new ArrayList<>(state.getObjectList());
    sortedObjects.sort(Comparator.comparingInt(DrawbleObject::getZ));
    for (DrawbleObject obj : sortedObjects) {
      obj.draw((Graphics2D) g);
      if (obj.isSelected()) {
        obj.drawHandles((Graphics2D) g);
      }
    }
  }

  private enum DragType {
    HANDLE,
    OBJECT,
    NONE
  }

  @Getter
  private static class DragState {

    private DragType dragType = DragType.NONE;
    private Handle activeHandle = null;
    private DrawbleObject activeObject = null;
    private Point initialMousePoint = null;
    private Map<DrawbleObject, Point> initialObjectPositions = new HashMap<>();

    public void startHandleDrag(Handle handle, DrawbleObject obj) {
      this.dragType = DragType.HANDLE;
      this.activeHandle = handle;
      this.activeObject = obj;
      // Store initial bounds if needed for undo functionality
    }

    public void updateHandleDrag(int x, int y) {
      if (activeObject != null && activeHandle != null) {
        activeObject.resizeOrRotate(activeHandle, x, y);
      }
    }

    public void startObjectDrag(Point initialPoint, List<DrawbleObject> selectedObjects) {
      this.dragType = DragType.OBJECT;
      this.initialMousePoint = initialPoint;
      for (DrawbleObject obj : selectedObjects) {
        initialObjectPositions.put(obj, new Point(obj.getX1(), obj.getY1()));
      }
    }

    public void updateObjectDrag(Point currentPoint) {
      int deltaX = currentPoint.x - initialMousePoint.x;
      int deltaY = currentPoint.y - initialMousePoint.y;

      for (DrawbleObject obj : initialObjectPositions.keySet()) {
        Point initialPosition = initialObjectPositions.get(obj);
        int newX1 = initialPosition.x + deltaX;
        int newY1 = initialPosition.y + deltaY;
        int newX2 = newX1 + obj.getWidth();
        int newY2 = newY1 + obj.getHeight();

        obj.resize(newX1, newY1, newX2, newY2);
      }
    }

    public void reset() {
      this.dragType = DragType.NONE;
      this.activeHandle = null;
      this.activeObject = null;
      this.initialMousePoint = null;
      this.initialObjectPositions.clear();
    }

    public boolean isDragging() {
      return dragType != DragType.NONE;
    }
  }
}
