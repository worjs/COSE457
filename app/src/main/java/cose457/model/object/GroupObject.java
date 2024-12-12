package cose457.model.object;

import java.awt.Graphics2D;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public class GroupObject implements DrawbleComponent {
    private List<DrawbleComponent> children = new ArrayList<>();
    private Color color;

    @Override
    public void draw(Graphics2D g2d) {
        for (DrawbleComponent child : children) {
            child.draw(g2d);
        }
    }

    @Override
    public void move(int dx, int dy) {
        for (DrawbleComponent child : children) {
            child.move(dx, dy);
        }
    }

    @Override
    public void setColor(Color color) {
        this.color = color;
        for (DrawbleComponent child : children) {
            child.setColor(color);
        }
    }

    @Override
    public boolean containsPoint(int x, int y) {
        for (DrawbleComponent child : children) {
            if (child.containsPoint(x, y)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void add(DrawbleComponent component) {
        children.add(component);
    }

    @Override
    public void remove(DrawbleComponent component) {
        children.remove(component);
    }

    @Override
    public DrawbleComponent getChild(int index) {
        return children.get(index);
    }

    public List<DrawbleComponent> getChildren() {
        return new ArrayList<>(children);
    }
} 