package cose457.view;

import cose457.controller.CanvasController;
import cose457.model.canvas.ObjectSelection;
import cose457.model.canvas.SelectionListener;
import cose457.model.object.DrawbleObject;
import cose457.controller.command.CommandInvoker;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class RightSideBar extends JPanel implements SelectionListener {

  private JTextField widthField, heightField, xposField, yposField, zposField;
  private JButton colorButton;
  private JLabel colorCodeLabel;
  private Color selectedColor;
  private ObjectSelection selection;
  private CanvasController controller;

  private DrawbleObject currentObject; // Currently selected object
  private PropertyChangeListener objectPropertyListener =
      new PropertyChangeListener() {
        @Override
        public void propertyChange(PropertyChangeEvent evt) {
          if (currentObject != null) {
            SwingUtilities.invokeLater(() -> updateFields(currentObject));
          }
        }
      };

  public RightSideBar(CanvasController controller) {
    this.controller = controller;
    selection = controller.getState().getSelections();
    selection.addSelectionListener(this);

    setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    setPreferredSize(new Dimension(200, getHeight()));

    // Add Undo/Redo buttons at the top
    JPanel undoRedoPanel = new JPanel();
    undoRedoPanel.setLayout(new BoxLayout(undoRedoPanel, BoxLayout.X_AXIS));
    undoRedoPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
    undoRedoPanel.setMaximumSize(new Dimension(200, 30));

    JButton undoButton = new JButton("Undo");
    undoButton.addActionListener(e -> {
      CommandInvoker.getInstance().undo();
      controller.getView().repaint();
    });
    
    JButton redoButton = new JButton("Redo");
    redoButton.addActionListener(e -> {
      CommandInvoker.getInstance().redo();
      controller.getView().repaint();
    });

    undoRedoPanel.add(undoButton);
    undoRedoPanel.add(Box.createRigidArea(new Dimension(5, 0)));
    undoRedoPanel.add(redoButton);
    
    add(undoRedoPanel);
    add(Box.createRigidArea(new Dimension(0, 10)));

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
          if (currentObject != null) {
            Color color = JColorChooser.showDialog(null, "Choose a color", selectedColor);
            if (color != null) {
              selectedColor = color;
              colorButton.setBackground(selectedColor);
              updateColorCodeLabel();
              // Change color through controller
              controller.changeColor(Arrays.asList(currentObject), selectedColor);
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

    // 그룹 관련 버튼들을 담을 패널
    JPanel groupPanel = new JPanel();
    groupPanel.setLayout(new BoxLayout(groupPanel, BoxLayout.X_AXIS));
    groupPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

    // 그룹화 버튼
    JButton groupButton = new JButton("Group");
    groupButton.addActionListener(e -> {
      controller.groupSelectedObjects();
    });
    
    // 그룹 해제 버튼
    JButton ungroupButton = new JButton("Ungroup");
    ungroupButton.addActionListener(e -> {
      controller.ungroupSelectedObjects();
    });

    groupPanel.add(groupButton);
    groupPanel.add(Box.createRigidArea(new Dimension(5, 0)));
    groupPanel.add(ungroupButton);
    
    add(groupPanel);
  }

  private void addFieldListeners() {
    ActionListener fieldListener =
        e -> {
          if (currentObject != null) {
            applyResize();
          }
        };

    widthField.addActionListener(fieldListener);
    heightField.addActionListener(fieldListener);
    xposField.addActionListener(fieldListener);
    yposField.addActionListener(fieldListener);
    zposField.addActionListener(
        e -> {
          if (currentObject != null) {
            try {
              int zpos = Integer.parseInt(zposField.getText());
              controller.changeZOrder(Arrays.asList(currentObject), zpos);
              controller.getView().repaint();
            } catch (NumberFormatException ex) {
              // Handle invalid input
            }
          }
        });
  }

  private void adjustValue(JTextField field, int adjustment) {
    try {
      int currentValue = Integer.parseInt(field.getText());
      field.setText(String.valueOf(currentValue + adjustment));
      if (currentObject != null) {
        applyResize();
      }
    } catch (NumberFormatException e) {
      // Handle invalid input
    }
  }

  private void applyResize() {
    try {
      int width = Integer.parseInt(widthField.getText());
      int height = Integer.parseInt(heightField.getText());
      int xpos = Integer.parseInt(xposField.getText());
      int ypos = Integer.parseInt(yposField.getText());

      Map<DrawbleObject, Rectangle> newBounds = new HashMap<>();
      newBounds.put(currentObject, new Rectangle(xpos, ypos, width, height));
      controller.resizeObjects(Arrays.asList(currentObject), newBounds);
      controller.getView().repaint();
    } catch (NumberFormatException ex) {
      // Handle invalid input
    }
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

  public void updateFields(DrawbleObject obj) {
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
    List<DrawbleObject> selectedObjects = selection.getSelectedObjects();
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
}
