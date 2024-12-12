package cose457.model.object;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.List;
import lombok.Getter;

@Getter
public class GroupObject extends DrawbleObject {
    private final List<DrawbleObject> groupedObjects;

    public GroupObject(List<DrawbleObject> objects, int z) {
        super(
            calculateBounds(objects).x,
            calculateBounds(objects).y,
            calculateBounds(objects).x + calculateBounds(objects).width,
            calculateBounds(objects).y + calculateBounds(objects).height,
            z,
            Color.BLACK
        );
        this.groupedObjects = objects;
    }

    private static Rectangle calculateBounds(List<DrawbleObject> objects) {
        if (objects.isEmpty()) {
            return new Rectangle(0, 0, 0, 0);
        }

        Rectangle bounds = objects.get(0).getBounds();
        for (int i = 1; i < objects.size(); i++) {
            bounds = bounds.union(objects.get(i).getBounds());
        }
        return bounds;
    }

    @Override
    public void draw(Graphics2D g2d) {
        // 그룹 내의 모든 객체를 그림
        for (DrawbleObject obj : groupedObjects) {
            obj.draw(g2d);
        }
        
        // 그룹의 경계를 점선으로 표시
        if (isSelected()) {
            g2d.setColor(Color.BLUE);
            float[] dash = {5.0f};
            g2d.setStroke(new java.awt.BasicStroke(1.0f, java.awt.BasicStroke.CAP_BUTT,
                    java.awt.BasicStroke.JOIN_MITER, 10.0f, dash, 0.0f));
            g2d.drawRect(x1, y1, x2 - x1, y2 - y1);
        }
    }

    @Override
    public void resize(int newX1, int newY1, int newX2, int newY2) {
        int deltaX = newX1 - x1;
        int deltaY = newY1 - y1;
        
        // 모든 그룹 객체를 같이 이동
        for (DrawbleObject obj : groupedObjects) {
            obj.resize(
                obj.getX1() + deltaX,
                obj.getY1() + deltaY,
                obj.getX2() + deltaX,
                obj.getY2() + deltaY
            );
        }
        
        super.resize(newX1, newY1, newX2, newY2);
    }
} 