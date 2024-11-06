package cose457.model.object;

import lombok.Getter;
import lombok.Setter;

import java.awt.Color;
import java.awt.Graphics2D;

@Getter
public class TextObject extends DrawbleObject {

  @Setter private String text;

  public TextObject(int x1, int y1, int x2, int y2, int z, Color color, String text) {
    super(x1, y1, x2, y2, z, color);
    this.text = text;
  }

  @Override
  public void draw(Graphics2D g2d) {
    g2d.setColor(this.color);
    g2d.drawString(this.text, this.x1, this.y1);
  }
}
