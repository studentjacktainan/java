import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;

/**
 * @Date 2023/3/1 20:38
 * @Created by 邦邦拒绝魔抗
 * @Description 墙
 */
public class Wall extends GameUnit {
    private int wallLife;
    private int wallType;
    /* 0    不可摧毁
     * 1    可摧毁
     * 2    可跨越
     */
    private Rectangle wallOuter;
    private Rectangle wallInner;
    private Line wallInnerLine1;
    private Line wallInnerLine2;
    private Circle wallInnerCircle;
    private Circle wallInnerInnerCircle;

    public Wall(double x, double y, int wallType) {
        this.x = x;
        this.y = y;
        this.wallType = wallType;
        wallOuter = new Rectangle(x - 8, y - 8, 16, 16);
        wallInner = new Rectangle(x - 6, y - 6, 12, 12);
        wallInnerLine1 = new Line(x - 6, y - 6, x + 6, y + 6);
        wallInnerLine1.setStrokeWidth(2);
        wallInnerLine2 = new Line(x - 6, y + 6, x + 6, y - 6);
        wallInnerLine2.setStrokeWidth(2);
        wallInnerCircle = new Circle(x, y, 8);
        wallInnerInnerCircle = new Circle(x, y, 6);
        switch (wallType) {
            case 0://不可摧毁
                wallLife = 10;
                wallOuter.setFill(Color.AZURE);
                wallInner.setFill(Color.BLACK);
                wallInnerLine1.setStroke(Color.LIGHTCYAN);
                wallInnerLine2.setStroke(Color.LIGHTCYAN);
                wallInnerCircle.setVisible(false);
                wallInnerInnerCircle.setVisible(false);
                break;
            case 1://可摧毁
                wallLife = 10;
                wallOuter.setFill(Color.LIGHTCYAN);
                wallInner.setFill(Color.LIGHTCYAN.darker().darker());
                wallInnerLine1.setVisible(false);
                wallInnerLine2.setVisible(false);
                wallInnerCircle.setVisible(false);
                wallInnerInnerCircle.setVisible(false);
                break;
            case 2://可跨越
                wallLife = 10;
                wallInnerCircle.setFill(Color.AZURE);
                wallInnerInnerCircle.setFill(Color.BLACK);
                wallInnerLine1.setStartX(x - 6);
                wallInnerLine1.setEndX(x + 6);
                wallInnerLine1.setStartY(y);
                wallInnerLine1.setEndY(y);
                wallInnerLine2.setStartX(x);
                wallInnerLine2.setEndX(x);
                wallInnerLine2.setStartY(y - 6);
                wallInnerLine2.setEndY(y + 6);
                wallInnerLine1.setStroke(Color.AZURE);
                wallInnerLine2.setStroke(Color.AZURE);
                wallOuter.setVisible(false);
                wallInner.setVisible(false);
                break;
        }
    }

    public int getWallType() {
        return wallType;
    }

    /**
     * 墙的生命损失
     */
    public void lifeLoss(int life) {
        wallLife -= life;
    }

    @Override
    public void update() {
        super.update();
        if (wallLife <= 0) {
            survival = false;
        }
    }

    @Override
    public void redraw(Pane parent) {
        if (wallType == 1 && wallLife == 5) {
            wallOuter.setFill(Color.LIGHTCYAN.darker().darker());
            wallInner.setFill(Color.LIGHTCYAN.darker().darker().darker().darker());
            wallInnerLine1.setStrokeWidth(4);
            wallInnerLine2.setStrokeWidth(4);
            wallInnerLine1.setStroke(Color.LIGHTCYAN.darker().darker().darker().darker());
            wallInnerLine2.setStroke(Color.LIGHTCYAN.darker().darker().darker().darker());

            wallInnerLine1.setStartX(x - 6);
            wallInnerLine1.setEndX(x + 6);
            wallInnerLine1.setStartY(y);
            wallInnerLine1.setEndY(y);
            wallInnerLine2.setStartX(x);
            wallInnerLine2.setEndX(x);
            wallInnerLine2.setStartY(y - 6);
            wallInnerLine2.setEndY(y + 6);

            wallInnerLine1.setVisible(true);
            wallInnerLine2.setVisible(true);
        }
        parent.getChildren().addAll(wallOuter, wallInner,
                wallInnerCircle, wallInnerInnerCircle,
                wallInnerLine1, wallInnerLine2);
    }
}