package cose457.view;

import cose457.model.SelectionListener;
import cose457.model.ObjectSelection;
import cose457.model.object.Object;
import cose457.controller.CanvasController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.util.List;

public class RightSideBar extends JPanel implements SelectionListener {
  private JTextField widthField, heightField, xposField, yposField, zposField;
  private JButton colorButton;
  private JLabel colorCodeLabel;
  private Color selectedColor;
  private ObjectSelection selection;
  private CanvasController controller;

  public RightSideBar(CanvasController controller) {
    this.controller = controller;
    selection = ObjectSelection.getInstance();
    selection.addSelectionListener(this);

    setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    setPreferredSize(new Dimension(200, getHeight()));

    widthField = new JTextField(10);
    heightField = new JTextField(10);
    xposField = new JTextField(10);
    yposField = new JTextField(10);
    zposField = new JTextField(10);

    add(createLabeledFieldWithButtons("Width", widthField));
    add(createLabeledFieldWithButtons("Height", heightField));
    add(createLabeledFieldWithButtons("X Position", xposField));
    add(createLabeledFieldWithButtons("Y Position", yposField));
    add(createLabeledFieldWithButtons("Z Position", zposField));

    add(Box.createRigidArea(new Dimension(0, 10)));
    JLabel colorLabel = new JLabel("Color:");
    colorLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
    add(colorLabel);

    colorButton = new JButton("Select Color");
    colorButton.setBackground(Color.WHITE);
    colorButton.setAlignmentX(Component.LEFT_ALIGNMENT);
    colorButton.setMaximumSize(new Dimension(120, 30));
    colorButton.addActionListener(
        e -> {
          Color color = JColorChooser.showDialog(null, "Choose a color", selectedColor);
          if (color != null) {
            selectedColor = color;
            colorButton.setBackground(selectedColor);
            updateColorCodeLabel();
            updateSelectedObject();
          }
        });
    add(colorButton);

    colorCodeLabel = new JLabel("Color Code: #FFFFFF (RGB: 255, 255, 255)");
    colorCodeLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
    colorCodeLabel.setOpaque(true);
    colorCodeLabel.setPreferredSize(new Dimension(200, 30));
    add(colorCodeLabel);

    clearFields();
    enableFields(false);

    addFieldListeners();
  }

  private void addFieldListeners() {
    FocusAdapter focusListener =
        new FocusAdapter() {
          @Override
          public void focusLost(FocusEvent e) {
            updateSelectedObject();
          }
        };

    widthField.addFocusListener(focusListener);
    heightField.addFocusListener(focusListener);
    xposField.addFocusListener(focusListener);
    yposField.addFocusListener(focusListener);
    zposField.addFocusListener(focusListener);
  }

  private JPanel createLabeledFieldWithButtons(String labelText, JTextField field) {
    JPanel panel = new JPanel();
    panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

    JLabel label = new JLabel(labelText);
    label.setAlignmentX(Component.LEFT_ALIGNMENT);
    panel.add(label);

    JPanel inputPanel = new JPanel();
    inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.X_AXIS));

    field.setMaximumSize(new Dimension(120, 30));
    field.setEditable(true);
    field.setEnabled(true);

    JButton incrementButton = new JButton("+");
    JButton decrementButton = new JButton("-");

    incrementButton.setPreferredSize(new Dimension(30, 30));
    decrementButton.setPreferredSize(new Dimension(30, 30));

    incrementButton.addActionListener(
        e -> {
          adjustValue(field, 1);
          updateSelectedObject();
        });
    decrementButton.addActionListener(
        e -> {
          adjustValue(field, -1);
          updateSelectedObject();
        });

    inputPanel.add(field);
    inputPanel.add(incrementButton);
    inputPanel.add(decrementButton);

    panel.add(inputPanel);
    panel.setAlignmentX(Component.LEFT_ALIGNMENT);

    return panel;
  }

  private void adjustValue(JTextField field, int adjustment) {
    try {
      int currentValue = Integer.parseInt(field.getText());
      field.setText(String.valueOf(currentValue + adjustment));
    } catch (NumberFormatException e) {
      // Handle invalid input
    }
  }

  public void updateFields(
      boolean shapeSelected, int width, int height, int xpos, int ypos, int zpos, Color color) {
    if (shapeSelected) {
      widthField.setText(String.valueOf(width));
      heightField.setText(String.valueOf(height));
      xposField.setText(String.valueOf(xpos));
      yposField.setText(String.valueOf(ypos));
      zposField.setText(String.valueOf(zpos));
      selectedColor = color;
      colorButton.setBackground(color);
      updateColorCodeLabel();
      enableFields(true);
    } else {
      clearFields();
      enableFields(false);
    }
  }

  private void clearFields() {
    widthField.setText("");
    heightField.setText("");
    xposField.setText("");
    yposField.setText("");
    zposField.setText("");
    selectedColor = Color.WHITE;
    colorButton.setBackground(Color.WHITE);
    updateColorCodeLabel();
  }

  private void enableFields(boolean enabled) {
    widthField.setEnabled(enabled);
    heightField.setEnabled(enabled);
    xposField.setEnabled(enabled);
    yposField.setEnabled(enabled);
    zposField.setEnabled(enabled);
    colorButton.setEnabled(enabled);
  }

  private void updateColorCodeLabel() {
    String hexCode =
        String.format(
            "#%02X%02X%02X",
            selectedColor.getRed(), selectedColor.getGreen(), selectedColor.getBlue());

    colorCodeLabel.setText(hexCode);
    colorCodeLabel.setBackground(selectedColor);

    int brightness =
        (selectedColor.getRed() * 299
                + selectedColor.getGreen() * 587
                + selectedColor.getBlue() * 114)
            / 1000;
    colorCodeLabel.setForeground(brightness > 125 ? Color.BLACK : Color.WHITE);
  }

  @Override
  public void selectionChanged() {
    List<Object> selectedObjects = selection.getSelectedObjects();
    if (selectedObjects.size() == 1) {
      Object obj = selectedObjects.get(0);
      int width = obj.getWidth();
      int height = obj.getHeight();
      int xpos = obj.getX1();
      int ypos = obj.getY1();
      int zpos = 0; // Assuming zpos is managed elsewhere
      Color color = obj.getColor();
      updateFields(true, width, height, xpos, ypos, zpos, color);
    } else {
      clearFields();
      enableFields(false);
    }
  }

  private void updateSelectedObject() {
    List<Object> selectedObjects = selection.getSelectedObjects();
    if (selectedObjects.size() == 1) {
      Object obj = selectedObjects.get(0);
      try {
        int width = Integer.parseInt(widthField.getText());
        int height = Integer.parseInt(heightField.getText());
        int xpos = Integer.parseInt(xposField.getText());
        int ypos = Integer.parseInt(yposField.getText());
        // Handle zpos if necessary

        int x2 = xpos + width;
        int y2 = ypos + height;
        obj.resize(xpos, ypos, x2, y2);

        if (selectedColor != null) {
          obj.setColor(selectedColor);
        }

        controller.getView().repaint();
      } catch (NumberFormatException e) {
        // Handle invalid input
      }
    }
  }
}
