package cose457;

import javax.swing.*;

public class App {

  public static void main(String[] args) {
    JFrame frame = new JFrame("COSE457");

    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    JLabel label = new JLabel("Hello, World!", JLabel.CENTER);

    frame.add(label);

    frame.setSize(300, 200);

    frame.setVisible(true);
  }
}
