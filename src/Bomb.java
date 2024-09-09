import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Bomb extends GameUnit {
    private int tankR;
    private int bombR;
    private int animationLife = 6;//炸弹动画生命
    private Circle bombOuter;
    private Circle bombInner;

    public Bomb(double x, double y, int r, Color tankColor) {
        this.x = x;
        this.y = y;
        tankR = r;
        bombR = tankR;
        bombOuter = new Circle(x, y, bombR);
        bombOuter.setFill(Color.WHITE);
        bombInner = new Circle(x, y, bombR - 2);
        bombInner.setFill(tankColor.darker().darker().darker().darker());
    }

    @Override
    public void update() {
        super.update();
        if (!survival) {
            return;
        }
        switch (animationLife) {
            case 6:
            case 5:
                break;
            case 4:
            case 3:
                bombR = tankR - 2;
                break;
            case 2:
            case 1:
                bombR = tankR - 4;
                break;
        }
        animationLife--;
        if (animationLife == 0) {
            survival = false;
        }
    }

    @Override
    public void redraw(Pane parent) {//炸弹重绘
        bombOuter.setRadius(bombR);
        bombInner.setRadius(bombR - 2);
        parent.getChildren().addAll(bombOuter, bombInner);
    }
}