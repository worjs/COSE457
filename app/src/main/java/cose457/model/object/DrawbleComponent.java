package cose457.model.object;

import java.awt.Graphics2D;
import java.awt.Color;

public interface DrawbleComponent {
    void draw(Graphics2D g2d);
    void move(int dx, int dy);
    void setColor(Color color);
    boolean containsPoint(int x, int y);
    void add(DrawbleComponent component);
    void remove(DrawbleComponent component);
    DrawbleComponent getChild(int index);
} 