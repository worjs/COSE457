package cose457.controller.command;

import cose457.model.factory.interfaces.ObjectFactory;
import cose457.model.object.DrawbleObject;
import cose457.view.CanvasView;
import java.awt.Color;
import java.util.ArrayList;

public class DrawCommand extends Command implements Undoable {

  private final int x1, y1, x2, y2, z;
  private final Color color;
  private final ObjectFactory objectFactory;

  public DrawCommand(CanvasView canvasView, int x1, int y1, int x2, int y2, int z, Color color,
      ObjectFactory objectFactory) {
    super(canvasView, new ArrayList<DrawbleObject>());
    this.x1 = x1;
    this.y1 = y1;
    this.x2 = x2;
    this.y2 = y2;
    this.z = z;
    this.color = color;
    this.objectFactory = objectFactory;
  }

  @Override
  public void execute() {
    // 객체 생성
    DrawbleObject drawableObject = objectFactory.createObject(x1, y1, x2, y2, z, color);
    // objects 리스트에 추가 (Command 클래스의 멤버 변수)
    objects.add(drawableObject);
    // 캔버스에 객체 추가
    canvasView.getState().addObject(drawableObject);
    canvasView.repaint();
  }

  @Override
  public void undo() {
    // 생성된 객체를 캔버스에서 제거
    for (DrawbleObject obj : objects) {
      canvasView.getState().removeObject(obj);
    }
    canvasView.repaint();
  }
}
