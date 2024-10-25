package cose457.model.object;

import java.awt.Color;
import java.awt.Graphics2D;

public class LineObject extends Object {

  public LineObject(int x1, int y1, int x2, int y2, int z, Color color) {
    super(x1, y1, x2, y2, z, color);
  }

  @Override
  public void draw(Graphics2D g2d) {
    g2d.setColor(this.color);
    g2d.drawLine(this.x1, this.y1, this.x2, this.y2);
  }
}
