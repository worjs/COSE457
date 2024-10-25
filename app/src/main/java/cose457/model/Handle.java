package cose457.model;

import lombok.Getter;

import java.awt.*;

@Getter
public class Handle {
  private int x, y;
  private static final int SIZE = 8;
  private final HandleType type;

  public Handle(int x, int y, HandleType type) {
    this.x = x;
    this.y = y;
    this.type = type;
  }

  public boolean containsPoint(int px, int py) {
    return px >= x && px <= x + SIZE && py >= y && py <= y + SIZE;
  }

  public void draw(Graphics2D g2d) {
    g2d.fillRect(x - SIZE / 2, y - SIZE / 2, SIZE, SIZE);
  }
}
