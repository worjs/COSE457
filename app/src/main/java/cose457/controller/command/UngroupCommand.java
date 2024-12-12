package cose457.controller.command;

import cose457.model.object.DrawbleObject;
import cose457.model.object.GroupObject;
import cose457.view.CanvasView;
import java.util.ArrayList;
import java.util.List;

public class UngroupCommand extends Command implements Undoable {
    private final List<GroupObject> groupObjects;
    private final List<List<DrawbleObject>> originalGroupedObjects;

    public UngroupCommand(CanvasView canvasView, List<DrawbleObject> objects) {
        super(canvasView, objects);
        this.groupObjects = new ArrayList<>();
        this.originalGroupedObjects = new ArrayList<>();
        
        // 선택된 객체들 중 GroupObject만 필터링
        for (DrawbleObject obj : objects) {
            if (obj instanceof GroupObject) {
                GroupObject groupObj = (GroupObject) obj;
                groupObjects.add(groupObj);
                originalGroupedObjects.add(new ArrayList<>(groupObj.getGroupedObjects()));
            }
        }
    }

    @Override
    public void execute() {
        for (GroupObject groupObj : groupObjects) {
            // 그룹 객체를 캔버스에서 제거
            canvasView.getState().removeObject(groupObj);
            
            // 그룹에 속한 객체들을 캔버스에 추가
            List<DrawbleObject> ungroupedObjects = groupObj.getGroupedObjects();
            for (DrawbleObject obj : ungroupedObjects) {
                canvasView.getState().addObject(obj);
            }
            
            // 그룹 해제된 객체들을 선택 상태로 만듦
            canvasView.getState().getSelections().selectObjects(ungroupedObjects);
        }
        
        canvasView.repaint();
    }

    @Override
    public void undo() {
        for (int i = 0; i < groupObjects.size(); i++) {
            GroupObject groupObj = groupObjects.get(i);
            List<DrawbleObject> originalObjects = originalGroupedObjects.get(i);
            
            // 그룹 해제된 객체들을 캔버스에서 제거
            for (DrawbleObject obj : originalObjects) {
                canvasView.getState().removeObject(obj);
            }
            
            // 그룹 객체를 다시 캔버스에 추가
            canvasView.getState().addObject(groupObj);
            
            // 그룹 객체를 선택 상태로 만듦
            canvasView.getState().getSelections().selectObjects(List.of(groupObj));
        }
        
        canvasView.repaint();
    }
} 