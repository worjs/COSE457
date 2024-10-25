package cose457.view;

import cose457.model.CanvasState;
import cose457.model.ObjectSelection;
import cose457.model.object.Object;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class CanvasView extends JPanel {

  private final CanvasState state;

  public CanvasView(CanvasState canvasState) {
    this.state = canvasState;
    setBackground(Color.WHITE);

    addMouseListener(
        new MouseAdapter() {
          @Override
          public void mousePressed(MouseEvent e) {
            handleMouseClick(e);
          }
        });
  }

  private void handleMouseClick(MouseEvent e) {
    boolean shiftPressed = e.isShiftDown();
    int x = e.getX();
    int y = e.getY();

    ArrayList<Object> objects = state.getObjectList();
    ObjectSelection selection = state.getSelections();
    boolean objectClicked = false;

    // 역순으로 순회하여 상위에 그려진 객체를 우선 선택
    for (int i = objects.size() - 1; i >= 0; i--) {
      Object obj = objects.get(i);
      if (obj.containsPoint(x, y)) {
        objectClicked = true;
        if (!shiftPressed) {
          selection.clearObjects();
        }
        if (selection.getSelectedObjects().contains(obj)) {
          selection.unselectObject(obj);
        } else {
          selection.selectObject(obj);
        }
        break;
      }
    }

    if (!objectClicked && !shiftPressed) {
      selection.clearObjects();
    }

    repaint();
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);

    Graphics2D g2d = (Graphics2D) g;
    for (Object object : state.getObjectList()) {
      object.draw(g2d);
    }
  }
}
