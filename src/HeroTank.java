import javafx.scene.paint.Color;

/**
 * @Date 2023/2/27 17:44
 * @Created by 邦邦拒绝魔抗
 * @Description 玩家坦克
 */
public class HeroTank extends Tank {
    private boolean needUp = false;
    private boolean needDown = false;
    private boolean needLeft = false;
    private boolean needRight = false;
    private boolean needShot = false;

    public HeroTank(double x, double y, int direction, Color tankColor) {
        super(x, y, direction, tankColor);
        speed = 2;
    }

    public void setNeedUp(boolean needUp) {
        this.needUp = needUp;
    }

    public void setNeedDown(boolean needDown) {
        this.needDown = needDown;
    }

    public void setNeedLeft(boolean needLeft) {
        this.needLeft = needLeft;
    }

    public void setNeedRight(boolean needRight) {
        this.needRight = needRight;
    }

    public void setNeedShot(boolean needShot) {
        this.needShot = needShot;
    }

    @Override
    public void update() {
        super.update();
        if (!survival) {
            return;
        }
        if (needUp) {
            if (needLeft) {
                direction = 6;//左上
                move(direction);
            } else if (needRight) {
                direction = 4;//右上
                move(direction);
            } else {
                direction = 0;//向上
                move(direction);
            }
        } else if (needDown) {
            if (needLeft) {
                direction = 5;//左下
                move(direction);
            } else if (needRight) {
                direction = 7;//右下
                move(direction);
            } else {
                direction = 1;//向下
                move(direction);
            }
        } else if (needLeft) {
            direction = 2;//向左
            move(direction);
        } else if (needRight) {
            direction = 3;//向右
            move(direction);
        }
        if (needShot) {
            shot();
        }
    }
}