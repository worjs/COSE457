package cose457.model.object;

import cose457.model.ObjectSelection;
import lombok.Getter;

import java.awt.Color;
import java.awt.Graphics2D;

@Getter
public abstract class Object {

  // x1, y1: top-left
  // x2, y2: bottom-right
  protected int x1, y1, x2, y2;
  protected Color color;

  // 생성
  protected Object(int x1, int y1, int x2, int y2, Color color) {
    this.color = color;
    this.x1 = x1;
    this.x2 = x2;
    this.y1 = y1;
    this.y2 = y2;
  }

  // CanvasState 그리기
  public abstract void draw(Graphics2D g2d);

  public boolean isSelected() {
    return ObjectSelection.getInstance().getSelectedObjects().contains(this);
  }

  // 위치 조절
  public void move(int newX1, int newY1) {
    int dx = newX1 - x1;
    int dy = newY1 - y1;
    x1 += dx;
    x2 += dx;
    y1 += dy;
    y2 += dy;
  }

  public boolean containsPoint(int x, int y) {
    int minX = Math.min(x1, x2);
    int maxX = Math.max(x1, x2);
    int minY = Math.min(y1, y2);
    int maxY = Math.max(y1, y2);
    return x >= minX && x <= maxX && y >= minY && y <= maxY;
  }

  // 크기 조절
  public void resize(int x1, int y1, int x2, int y2) {
    this.x1 = x1;
    this.x2 = x2;
    this.y1 = y1;
    this.y2 = y2;
  }

  public int getWidth() {
    return Math.abs(x2 - x1);
  }

  public int getHeight() {
    return Math.abs(y2 - y1);
  }

  // 색상 조절
  public void setColor(Color color) {
    this.color = color;
  }
}
