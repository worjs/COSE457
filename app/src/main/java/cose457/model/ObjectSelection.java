package cose457.model;

import cose457.model.object.Object;
import java.util.ArrayList;
import java.util.List;

public class ObjectSelection {
  private static ObjectSelection instance;
  private ArrayList<Object> selectedObjects = new ArrayList<>();
  private List<SelectionListener> listeners = new ArrayList<>();

  public static ObjectSelection getInstance() {
    if (instance == null) {
      instance = new ObjectSelection();
    }
    return instance;
  }

  public void selectObject(Object object) {
    selectedObjects.add(object);
    notifySelectionChanged();
  }

  public void unselectObject(Object object) {
    selectedObjects.remove(object);
    notifySelectionChanged();
  }

  public void clearObjects() {
    selectedObjects.clear();
    notifySelectionChanged();
  }

  public List<Object> getSelectedObjects() {
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
