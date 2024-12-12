package cose457.controller.command;

import cose457.model.object.DrawbleObject;
import cose457.model.object.GroupObject;
import cose457.view.CanvasView;
import java.util.ArrayList;
import java.util.List;

public class GroupCommand extends Command implements Undoable {
    private GroupObject groupObject;
    private final List<DrawbleObject> originalObjects;

    public GroupCommand(CanvasView canvasView, List<DrawbleObject> objects) {
        super(canvasView, new ArrayList<>(objects));
        this.originalObjects = new ArrayList<>(objects);
    }

    @Override
    public void execute() {
        // 새로운 그룹 객체 생성
        groupObject = new GroupObject(originalObjects, canvasView.getState().getNextZ());
        
        // 원래 객체들을 캔버스에서 제거
        for (DrawbleObject obj : originalObjects) {
            canvasView.getState().removeObject(obj);
        }
        
        // 그룹 객체를 캔버스에 추가
        canvasView.getState().addObject(groupObject);
        
        // 선택 상태 업데이트
        canvasView.getState().getSelections().clearObjects();
        canvasView.getState().getSelections().selectObjects(List.of(groupObject));
        
        canvasView.repaint();
    }

    @Override
    public void undo() {
        // 그룹 객체를 캔버스에서 제거
        canvasView.getState().removeObject(groupObject);
        
        // 원래 객체들을 다시 캔버스에 추가
        for (DrawbleObject obj : originalObjects) {
            canvasView.getState().addObject(obj);
        }
        
        // 선택 상태 업데이트
        canvasView.getState().getSelections().clearObjects();
        canvasView.getState().getSelections().selectObjects(originalObjects);
        
        canvasView.repaint();
    }
} 