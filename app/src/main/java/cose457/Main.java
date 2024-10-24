package cose457;

import cose457.controller.CanvasController;
import cose457.model.canvas.CanvasState;
import cose457.view.ScreenView;

import javax.swing.*;

public class Main {

  public static void main(String[] args) {
    CanvasState canvasState = new CanvasState();
    ScreenView screenView = new ScreenView(canvasState);

    screenView.setVisible(true);
  }
}
