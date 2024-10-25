package cose457;

import cose457.model.CanvasState;
import cose457.view.ScreenView;

public class Main {

  public static void main(String[] args) {
    CanvasState canvasState = new CanvasState();
    ScreenView screenView = new ScreenView(canvasState);

    screenView.setVisible(true);
  }
}
