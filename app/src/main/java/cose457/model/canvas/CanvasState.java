package cose457.model.canvas;

import cose457.model.object.DrawbleObject;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class CanvasState {

  private final List<DrawbleObject> objectList = new ArrayList<>();

  public ObjectSelection getSelections() {
    return ObjectSelection.getInstance();
  }

  public List<DrawbleObject> getObjectList() {
    return objectList.stream()
        .sorted(Comparator.comparingInt(DrawbleObject::getZ))
        .collect(Collectors.toList());
  }

  public int getNextZ() {
    return objectList.stream().mapToInt(DrawbleObject::getZ).max().orElse(0) + 1;
  }

  public void addObject(DrawbleObject object) {
    objectList.add(object);
  }

  public void removeObject(DrawbleObject object) {
    objectList.remove(object);
  }
}
