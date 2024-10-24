package cose457.controller;

import cose457.model.canvas.CanvasState;
import cose457.model.object.RectangleObject;
import cose457.view.CanvasView;

import java.awt.*;

public class CanvasController {
  private CanvasView view;
  private CanvasState stage;

  public CanvasController(CanvasView view, CanvasState stage) {
    this.view = view;
    this.stage = stage;
  }

  public void addObjects() {
    {
      RectangleObject rect = new RectangleObject(100, 100, 300, 200, Color.RED);
      stage.addObjects(rect); // CanvasState에 직사각형 추가
      view.repaint(); // 캔버스 다시 그리기 요청
    }
  }
}
