package cose457.view;

import cose457.controller.CanvasController;
import cose457.model.canvas.CanvasState;
import java.awt.BorderLayout;
import javax.swing.JFrame;

public class ScreenView extends JFrame {

  public ScreenView(CanvasState canvasState) {
    setTitle("Screen View");
    setLayout(new BorderLayout());

    CanvasView canvasView = new CanvasView(canvasState);
    CanvasController controller = new CanvasController(canvasView, canvasState);
    LeftSideBar leftSidebar = new LeftSideBar(controller);
    RightSideBar rightSidebar = new RightSideBar(controller);

    add(leftSidebar, BorderLayout.WEST);
    add(rightSidebar, BorderLayout.EAST);
    add(canvasView, BorderLayout.CENTER);

    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setSize(800, 600);
  }
}
