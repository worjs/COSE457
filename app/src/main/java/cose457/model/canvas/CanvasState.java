package cose457.model.canvas;

import cose457.model.object.Object;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class CanvasState {

  private final List<Object> objectList = new ArrayList<>();

  public ObjectSelection getSelections() {
    return ObjectSelection.getInstance();
  }

  public List<Object> getObjectList() {
    return objectList.stream()
        .sorted(Comparator.comparingInt(Object::getZ))
        .collect(Collectors.toList());
  }

  public int getNextZ() {
    return objectList.stream().mapToInt(Object::getZ).max().orElse(0) + 1;
  }

  public void addObjects(Object object) {
    objectList.add(object);
  }

  public void removeObjects(Object object) {
    objectList.remove(object);
  }
}
