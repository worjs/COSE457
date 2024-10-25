package cose457.model.object;

import cose457.model.Handle;
import cose457.model.HandleType;
import cose457.model.ObjectSelection;
import lombok.Getter;

import java.awt.Color;
import java.awt.Graphics2D;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.List;

@Getter
public abstract class Object {
  protected int x1, y1, x2, y2, z;
  protected Color color;
  protected double rotationAngle = 0.0;
  private List<Handle> handles;

  private final PropertyChangeSupport pcs = new PropertyChangeSupport(this);

  protected Object(int x1, int y1, int x2, int y2, int z, Color color) {
    this.color = color;
    this.x1 = x1;
    this.x2 = x2;
    this.y1 = y1;
    this.y2 = y2;
    this.z = z;
    updateHandles();
  }

  public abstract void draw(Graphics2D g2d);

  public void updateHandles() {
    handles = new ArrayList<>();
    handles.add(new Handle(x1, y1, HandleType.TOP_LEFT));
    handles.add(new Handle(x2, y1, HandleType.TOP_RIGHT));
    handles.add(new Handle(x2, y2, HandleType.BOTTOM_RIGHT));
    handles.add(new Handle(x1, y2, HandleType.BOTTOM_LEFT));
    handles.add(new Handle((x1 + x2) / 2, y1, HandleType.TOP_MIDDLE));
    handles.add(new Handle((x1 + x2) / 2, y2, HandleType.BOTTOM_MIDDLE));
    handles.add(new Handle(x1, (y1 + y2) / 2, HandleType.LEFT_MIDDLE));
    handles.add(new Handle(x2, (y1 + y2) / 2, HandleType.RIGHT_MIDDLE));
  }

  public void addPropertyChangeListener(PropertyChangeListener listener) {
    pcs.addPropertyChangeListener(listener);
  }

  public void removePropertyChangeListener(PropertyChangeListener listener) {
    pcs.removePropertyChangeListener(listener);
  }

  public boolean containsPoint(int x, int y) {
    int minX = Math.min(x1, x2);
    int maxX = Math.max(x1, x2);
    int minY = Math.min(y1, y2);
    int maxY = Math.max(y1, y2);
    return x >= minX && x <= maxX && y >= minY && y <= maxY;
  }

  public void drawHandles(Graphics2D g2d) {
    g2d.setColor(Color.BLUE);
    for (Handle handle : handles) {
      handle.draw(g2d);
    }
  }

  public boolean isSelected() {
    return ObjectSelection.getInstance().getSelectedObjects().contains(this);
  }

  public Handle getClickedHandle(int x, int y) {
    for (Handle handle : handles) {
      if (handle.containsPoint(x, y)) {
        return handle;
      }
    }
    return null;
  }

  public void resizeOrRotate(Handle handle, int mouseX, int mouseY) {
    switch (handle.getType()) {
      case TOP_LEFT:
        resize(mouseX, mouseY, x2, y2);
        break;
      case TOP_RIGHT:
        resize(x1, mouseY, mouseX, y2);
        break;
      case BOTTOM_RIGHT:
        resize(x1, y1, mouseX, mouseY);
        break;
      case BOTTOM_LEFT:
        resize(mouseX, y1, x2, mouseY);
        break;
      case TOP_MIDDLE:
        resize(x1, mouseY, x2, y2);
        break;
      case BOTTOM_MIDDLE:
        resize(x1, y1, x2, mouseY);
        break;
      case LEFT_MIDDLE:
        resize(mouseX, y1, x2, y2);
        break;
      case RIGHT_MIDDLE:
        resize(x1, y1, mouseX, y2);
        break;
    }
    updateHandles(); // 크기 조정 후 핸들 위치 갱신
  }

  public void resize(int newX1, int newY1, int newX2, int newY2) {
    this.x1 = newX1;
    this.x2 = newX2;
    this.y1 = newY1;
    this.y2 = newY2;

    updateHandles();
    pcs.firePropertyChange("bounds", null, null);
  }

  public int getWidth() {
    return Math.abs(x2 - x1);
  }

  public int getHeight() {
    return Math.abs(y2 - y1);
  }

  public void setColor(Color color) {
    Color oldColor = this.color;
    this.color = color;
    pcs.firePropertyChange("color", oldColor, this.color);
  }

  public void setZ(int z) {
    int oldZ = this.z;
    this.z = z;
    pcs.firePropertyChange("z", oldZ, this.z);
  }
}
