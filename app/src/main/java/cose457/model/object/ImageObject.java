package cose457.model.object;

import lombok.Setter;

import java.awt.Color;
import java.awt.Image;
import java.awt.Graphics2D;

public class ImageObject extends Object {

  @Setter private Image image;

  public ImageObject(int x1, int y1, int x2, int y2, int z, Color color, Image image) {
    super(x1, y1, x2, y2, z, color);
    this.image = image;
  }

  @Override
  public void draw(Graphics2D g2d) {
    g2d.setColor(this.color);
    g2d.drawImage(this.image, this.x1, this.y1, this.x2 - this.x1, this.y2 - this.y1, null);
  }
}
