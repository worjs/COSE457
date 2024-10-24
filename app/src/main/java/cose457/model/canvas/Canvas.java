package cose457.model.canvas;

import cose457.model.object.Object;
import java.util.ArrayList;

public class Canvas {
	public Canvas() {
	}

	// 전체 객체들을 저장하는 리스트
	private ArrayList<Object> objectList = new ArrayList<>();

	// 현재 선택된 객체들을 저장하는 리스트 => Composite Pattern
	private ArrayList<Object> selectedList = new ArrayList<>();

	public ArrayList<Object> getSelections() {
		return selectedList;
	}
}
