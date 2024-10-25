package cose457.view;

import cose457.controller.CanvasController;
import cose457.model.factory.*;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionListener;

public class LeftSideBar extends JPanel {

  private static final Dimension BUTTON_SIZE = new Dimension(50, 50);
  private static final String ICON_PATH_PREFIX = "/icons/";

  public LeftSideBar(CanvasController controller) {
    setLayout(new GridLayout(5, 1, 0, 0));

    add(createIconButton("line_icon.png", e -> controller.addObjects(LineFactory.getInstance())));
    add(
        createIconButton(
            "rectangle_icon.png", e -> controller.addObjects(RectangleFactory.getInstance())));
    add(
        createIconButton(
            "ellipse_icon.png", e -> controller.addObjects(EllipseFactory.getInstance())));
    add(createIconButton("text_icon.png", e -> controller.addObjects(TextFactory.getInstance())));
    add(createIconButton("image_icon.png", e -> handleImageSelection(controller)));

    setPreferredSize(new Dimension(100, 250));
    setBackground(Color.LIGHT_GRAY);
  }

  private JButton createIconButton(String iconName, ActionListener action) {
    Icon icon = new ImageIcon(getClass().getResource(ICON_PATH_PREFIX + iconName));
    JButton button = new JButton(icon);
    button.setPreferredSize(BUTTON_SIZE);
    button.setMargin(new Insets(0, 0, 0, 0));
    button.setFocusPainted(false);
    button.addActionListener(action);
    return button;
  }

  private void handleImageSelection(CanvasController controller) {
    JFileChooser fileChooser = new JFileChooser();

    FileNameExtensionFilter imageFilter =
        new FileNameExtensionFilter("Image files", "jpg", "jpeg", "png", "gif");
    fileChooser.setFileFilter(imageFilter);

    int result = fileChooser.showOpenDialog(null);
    if (result == JFileChooser.APPROVE_OPTION) {
      ImageIcon imageIcon = new ImageIcon(fileChooser.getSelectedFile().getAbsolutePath());
      controller.addObjects(ImageFactory.getInstance(imageIcon.getImage()));
    }
  }
}
