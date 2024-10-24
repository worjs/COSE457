package cose457.view;

import cose457.controller.CanvasController;
import cose457.model.canvas.CanvasState;
import cose457.model.object.RectangleObject;

import javax.swing.*;
import java.awt.*;

public class LeftSideBar extends JPanel {

  public LeftSideBar(CanvasController controller) {
    // GridLayout: 5행 1열, 가로 및 세로 간격 0
    setLayout(new GridLayout(5, 1, 0, 0));

    // 아이콘 로드 (리소스 경로에 맞게 설정)
    Icon lineIcon = new ImageIcon(getClass().getResource("/icons/line_icon.png"));
    Icon rectangleIcon = new ImageIcon(getClass().getResource("/icons/rectangle_icon.png"));
    Icon ellipseIcon = new ImageIcon(getClass().getResource("/icons/ellipse_icon.png"));
    Icon textIcon = new ImageIcon(getClass().getResource("/icons/text_icon.png"));
    Icon imageIcon = new ImageIcon(getClass().getResource("/icons/image_icon.png"));

    // 버튼 생성 (아이콘 포함)
    JButton lineButton = new JButton(lineIcon);
    JButton rectangleButton = new JButton(rectangleIcon);
    JButton ellipseButton = new JButton(ellipseIcon);
    JButton textButton = new JButton(textIcon);
    JButton imageButton = new JButton(imageIcon);

    // 버튼들을 50x50 정사각형으로 설정
    Dimension buttonSize = new Dimension(50, 50);
    lineButton.setPreferredSize(buttonSize);
    rectangleButton.setPreferredSize(buttonSize);
    ellipseButton.setPreferredSize(buttonSize);
    textButton.setPreferredSize(buttonSize);
    imageButton.setPreferredSize(buttonSize);

    // 버튼의 간격을 없애고 여백 제거
    lineButton.setMargin(new Insets(0, 0, 0, 0));
    rectangleButton.setMargin(new Insets(0, 0, 0, 0));
    ellipseButton.setMargin(new Insets(0, 0, 0, 0));
    textButton.setMargin(new Insets(0, 0, 0, 0));
    imageButton.setMargin(new Insets(0, 0, 0, 0));

    // 버튼을 클릭하면 직사각형을 추가하는 이벤트 추가
    rectangleButton.addActionListener(e -> controller.addObjects());

    lineButton.setFocusPainted(false);
    rectangleButton.setFocusPainted(false);
    ellipseButton.setFocusPainted(false);
    textButton.setFocusPainted(false);
    imageButton.setFocusPainted(false);

    // 패널에 버튼 추가
    add(lineButton);
    add(rectangleButton);
    add(ellipseButton);
    add(textButton);
    add(imageButton);

    // 사이드바의 전체 크기를 버튼 5개에 맞춰 설정 (100x250)
    setPreferredSize(new Dimension(100, 250));
    setBackground(Color.LIGHT_GRAY);
  }
}
