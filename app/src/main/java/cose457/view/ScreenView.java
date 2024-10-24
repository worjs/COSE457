package cose457.view;

import javax.swing.*;
import java.awt.*;

public class ScreenView extends JFrame {

  public ScreenView() {
    setTitle("Screen View");
    setLayout(new BorderLayout());

    LeftSideBar leftSidebar = new LeftSideBar();
    RightSideBar rightSidebar = new RightSideBar();

    JPanel canvas = new JPanel();
    canvas.setBackground(Color.WHITE);

    add(leftSidebar, BorderLayout.WEST);
    add(rightSidebar, BorderLayout.EAST);
    add(canvas, BorderLayout.CENTER);

    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setSize(800, 600);
  }
}
