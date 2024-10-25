package cose457.model.object;

import java.awt.*;

public class RectangleObject extends Object {

  public RectangleObject(int x1, int y1, int x2, int y2, Color color) {
    super(x1, y1, x2, y2, color);
  }

  @Override
  public void draw(Graphics2D g2d) {
    g2d.setColor(this.color);
    g2d.drawRect(this.x1, this.y1, this.x2 - this.x1, this.y2 - this.y1);

    if (isSelected()) {
      g2d.setColor(Color.BLUE); // 선택된 객체의 테두리를 파란색으로 표시
      g2d.setStroke(new BasicStroke(2));
      g2d.drawRect(Math.min(x1, x2), Math.min(y1, y2), getWidth(), getHeight());
    }
  }
}
