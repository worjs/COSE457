package cose457.model;

import cose457.model.object.Object;
import lombok.Getter;

import java.util.ArrayList;

public class CanvasState {
  public CanvasState() {}

  @Getter private ArrayList<Object> objectList = new ArrayList<>();

  private ObjectSelection selection;

  public ObjectSelection getSelections() {
    return ObjectSelection.getInstance();
  }

  public void addObjects(Object object) {
    objectList.add(object);
  }

  public void removeObjects(Object object) {
    objectList.remove(object);
  }
}
