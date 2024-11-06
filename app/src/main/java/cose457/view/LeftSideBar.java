package cose457.view;

import cose457.controller.CanvasController;
import cose457.model.factory.EllipseFactory;
import cose457.model.factory.ImageFactory;
import cose457.model.factory.LineFactory;
import cose457.model.factory.RectangleFactory;
import cose457.model.factory.TextFactory;
import cose457.model.factory.interfaces.ObjectFactory;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionListener;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;

public class LeftSideBar extends JPanel {

  private static final Dimension BUTTON_SIZE = new Dimension(50, 50);
  private static final String ICON_PATH_PREFIX = "/icons/";

  public LeftSideBar(CanvasController controller) {
    setLayout(new GridLayout(5, 1, 0, 0));

    add(createIconButton("line_icon.png", e -> controller.drawObject(LineFactory.getInstance())));
    add(createIconButton("rectangle_icon.png", e -> controller.drawObject(RectangleFactory.getInstance())));
    add(createIconButton("ellipse_icon.png", e -> controller.drawObject(EllipseFactory.getInstance())));
    add(createIconButton("text_icon.png", e -> controller.drawObject(TextFactory.getInstance())));
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

    FileNameExtensionFilter imageFilter = new FileNameExtensionFilter("Image files", "jpg", "jpeg", "png", "gif");
    fileChooser.setFileFilter(imageFilter);

    int result = fileChooser.showOpenDialog(null);
    if (result == JFileChooser.APPROVE_OPTION) {
      ImageIcon imageIcon = new ImageIcon(fileChooser.getSelectedFile().getAbsolutePath());
      Image image = imageIcon.getImage();

      // Create ImageFactory instance with selected image
      ObjectFactory imageFactory = ImageFactory.getInstance(image);

      // Delegate drawing action to controller
      controller.drawObject(imageFactory);
    }
  }
}
