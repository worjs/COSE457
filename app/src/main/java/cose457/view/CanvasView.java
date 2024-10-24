package cose457.view;

import cose457.model.canvas.CanvasState;
import cose457.model.object.Object;

import javax.swing.*;
import java.awt.*;

public class CanvasView extends JPanel {

  private CanvasState canvasState;

  public CanvasView(CanvasState canvasState) {
    this.canvasState = canvasState;
    setBackground(Color.WHITE);
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g); // 부모 클래스의 paintComponent 호출로 캔버스를 초기화

    // CanvasState에 저장된 객체들 그리기
    Graphics2D g2d = (Graphics2D) g;
    for (Object object : canvasState.getObjectList()) {
      object.draw(g2d); // 각 객체의 draw 메서드를 호출해 그리기
    }
  }
}
