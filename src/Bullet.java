import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

/**
 * @Date 2023/2/27 17:25
 * @Created by 邦邦拒绝魔抗
 * @Description 子弹
 */
public class Bullet extends GameUnit {
    private int direction;
    private int speed = 10;
    private double endX;
    private double endY;
    private Line bullet;//子弹

    public Bullet(double x, double y, int direction) {
        this.x = x;
        this.y = y;
        this.direction = direction;
        updateEndXY();
        bullet = new Line(x, y, endX, endY);
        bullet.setStroke(Color.WHITE);
        bullet.setStrokeWidth(2);
    }

    @Override
    public void update() {
        super.update();
        if (!survival) {
            return;
        }
        fly();
        updateEndXY();
        updateSurvival();
    }

    private void updateEndXY() {
        endX = x;
        endY = y;
        switch (direction) {
            case 0:
                endY -= 2;
                break;
            case 1:
                endY += 2;
                break;
            case 2:
                endX -= 2;
                break;
            case 3:
                endX += 2;
                break;
            case 4:
                endX += Math.pow(2, 0.5);
                endY -= Math.pow(2, 0.5);
                break;
            case 5:
                endX -= Math.pow(2, 0.5);
                endY += Math.pow(2, 0.5);
                break;
            case 6:
                endX -= Math.pow(2, 0.5);
                endY -= Math.pow(2, 0.5);
                break;
            case 7:
                endX += Math.pow(2, 0.5);
                endY += Math.pow(2, 0.5);
                break;
        }
    }

    @Override
    public void redraw(Pane parent) {
        bullet.setStartX(x);
        bullet.setStartY(y);
        bullet.setEndX(endX);
        bullet.setEndY(endY);
        parent.getChildren().add(bullet);
    }

    /**
     * 更新子弹存活状态
     */
    private void updateSurvival() {
        if (x < 0 || x > 1000 || y < 0 || y > 600) {
            survival = false;
            return;
        }
        for (GameUnit u : BBTankPane.getInstance().getGameUnitList()) {
            if (u instanceof Tank) {
                Tank t = (Tank) u;
                if (inTank(t)) {
                    survival = false;
                    t.setSurvival(false);
                    return;
                }
            } else if (u instanceof Wall) {
                Wall w = (Wall) u;
                if (inWall(w)) {
                    switch (w.getWallType()) {
                        case 0://不可摧毁
                            survival = false;
                            break;
                        case 1://可摧毁
                            survival = false;
                            w.lifeLoss(5);
                            break;
                        case 2://可跨越
                            break;
                    }
                    return;
                }
            }
        }
    }

    private void fly() {
        switch (direction) {
            case 0://向上飞行
                y -= speed;
                break;
            case 1://向下飞行
                y += speed;
                break;
            case 2://向左飞行
                x -= speed;
                break;
            case 3://向右飞行
                x += speed;
                break;
            case 4://右上飞行
                x += speed * Math.pow(2, 0.5) / 2;
                y -= speed * Math.pow(2, 0.5) / 2;
                break;
            case 5://左下飞行
                x -= speed * Math.pow(2, 0.5) / 2;
                y += speed * Math.pow(2, 0.5) / 2;
                break;
            case 6://左上飞行
                x -= speed * Math.pow(2, 0.5) / 2;
                y -= speed * Math.pow(2, 0.5) / 2;
                break;
            case 7://右下飞行
                x += speed * Math.pow(2, 0.5) / 2;
                y += speed * Math.pow(2, 0.5) / 2;
                break;
        }
    }

    private boolean inTank(Tank tank) {
        double d1 = getDistance(tank.getX(), tank.getY(), x, y);//子弹尾端与坦克的距离
        double d2 = getDistance(tank.getX(), tank.getY(), endX, endY);//子弹顶端与坦克的距离
        if (d1 < tank.getR() || d2 < tank.getR()) {
            return true;
        }
        return false;
    }

    private double getDistance(double x1, double y1, double x2, double y2) {
        return Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
    }

    private boolean inWall(Wall wall) {
        if (endX >= wall.getX() - 8 && endX <= wall.getX() + 8
                && endY >= wall.getY() - 8 && endY <= wall.getY() + 8) {
            return true;
        }
        return false;
    }
}