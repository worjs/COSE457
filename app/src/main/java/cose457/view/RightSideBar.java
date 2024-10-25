package cose457.view;

import cose457.model.SelectionListener;
import cose457.model.ObjectSelection;
import cose457.model.object.Object;
import cose457.controller.CanvasController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;

public class RightSideBar extends JPanel implements SelectionListener {
  private JTextField widthField, heightField, xposField, yposField, zposField;
  private JButton colorButton;
  private JLabel colorCodeLabel;
  private Color selectedColor;
  private ObjectSelection selection;
  private CanvasController controller;

  private Object currentObject; // 현재 선택된 객체를 저장

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
            if (currentObject != null) {
              currentObject.setColor(selectedColor);
              controller.getView().repaint();
            }
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
    ActionListener fieldListener =
        e -> {
          if (currentObject != null) {
            try {
              int width = Integer.parseInt(widthField.getText());
              int height = Integer.parseInt(heightField.getText());
              int xpos = Integer.parseInt(xposField.getText());
              int ypos = Integer.parseInt(yposField.getText());

              int x2 = xpos + width;
              int y2 = ypos + height;
              currentObject.resize(xpos, ypos, x2, y2);
              controller.getView().repaint();
            } catch (NumberFormatException ex) {
              // 잘못된 입력 처리
            }
          }
        };

    widthField.addActionListener(fieldListener);
    heightField.addActionListener(fieldListener);
    xposField.addActionListener(fieldListener);
    yposField.addActionListener(fieldListener);
    zposField.addActionListener(fieldListener);
    // zposField에 대한 처리 필요 시 추가
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

    incrementButton.addActionListener(e -> adjustValue(field, 1));
    decrementButton.addActionListener(e -> adjustValue(field, -1));

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
      if (currentObject != null) {
        int width = Integer.parseInt(widthField.getText());
        int height = Integer.parseInt(heightField.getText());
        int xpos = Integer.parseInt(xposField.getText());
        int ypos = Integer.parseInt(yposField.getText());
        int zpos = Integer.parseInt(zposField.getText());

        int x2 = xpos + width;
        int y2 = ypos + height;
        currentObject.resize(xpos, ypos, x2, y2);
        currentObject.setZ(zpos); // z값 업데이트
        controller.getView().repaint();
      }
    } catch (NumberFormatException e) {
      // 잘못된 입력 처리
    }
  }

  public void updateFields(Object obj) {
    widthField.setText(String.valueOf(obj.getWidth()));
    heightField.setText(String.valueOf(obj.getHeight()));
    xposField.setText(String.valueOf(obj.getX1()));
    yposField.setText(String.valueOf(obj.getY1()));
    zposField.setText(String.valueOf(obj.getZ()));
    selectedColor = obj.getColor();
    colorButton.setBackground(selectedColor);
    updateColorCodeLabel();
    enableFields(true);
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
      if (currentObject != null) {
        currentObject.removePropertyChangeListener(objectPropertyListener);
      }
      currentObject = selectedObjects.get(0);
      currentObject.addPropertyChangeListener(objectPropertyListener);
      updateFields(currentObject);
    } else {
      if (currentObject != null) {
        currentObject.removePropertyChangeListener(objectPropertyListener);
        currentObject = null;
      }
      clearFields();
      enableFields(false);
    }
  }

  private PropertyChangeListener objectPropertyListener =
      new PropertyChangeListener() {
        @Override
        public void propertyChange(PropertyChangeEvent evt) {
          if (currentObject != null) {
            SwingUtilities.invokeLater(() -> updateFields(currentObject));
          }
        }
      };
}
