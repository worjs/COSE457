package cose457.model.canvas;

import cose457.model.object.DrawbleObject;
import java.util.ArrayList;
import java.util.List;

public class ObjectSelection {

  private static ObjectSelection instance;
  private ArrayList<DrawbleObject> selectedObjects = new ArrayList<>();
  private List<SelectionListener> listeners = new ArrayList<>();

  public static ObjectSelection getInstance() {
    if (instance == null) {
      instance = new ObjectSelection();
    }
    return instance;
  }

  public void selectObjects(List<DrawbleObject> object) {
    selectedObjects.addAll(object);
    notifySelectionChanged();
  }

  public void unselectObjects(List<DrawbleObject> object) {
    selectedObjects.removeAll(object);
    notifySelectionChanged();
  }

  public void clearObjects() {
    selectedObjects.clear();
    notifySelectionChanged();
  }

  public List<DrawbleObject> getSelectedObjects() {
    return selectedObjects;
  }

  public void addSelectionListener(SelectionListener listener) {
    listeners.add(listener);
  }

  public void removeSelectionListener(SelectionListener listener) {
    listeners.remove(listener);
  }

  private void notifySelectionChanged() {
    for (SelectionListener listener : listeners) {
      listener.selectionChanged();
    }
  }
}
