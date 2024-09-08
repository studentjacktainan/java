import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;
import javafx.scene.shape.ArcType;
import javafx.scene.shape.Circle;

import java.io.Serializable;

/**
 * @Date 2023/2/27 17:15
 * @Created by 邦邦拒绝魔抗
 * @Description 坦克
 */
public abstract class Tank extends GameUnit implements Serializable {
    protected static int r = 8;//坦克半径
    protected Color tankColor;//坦克颜色
    protected int direction = 0;//坦克方向
    protected int speed = 1;//移动速度
    protected Circle chassis;//底盘
    protected Circle battery;//炮台
    protected Arc barrel;//炮管
    protected Circle cap;//炮顶

    public static int getR() {
        return r;
    }

    public Tank(double x, double y, int direction, Color tankColor) {
        this.x = x;
        this.y = y;
        this.direction = direction;
        this.tankColor = tankColor;
        chassis = new Circle(x, y, r);
        chassis.setFill(tankColor);
        battery = new Circle(x, y, r - 2);
        battery.setFill(tankColor.darker().darker());
        barrel = new Arc();
        barrel.setFill(tankColor.darker().darker());
        barrel.setType(ArcType.ROUND);
        barrel.setRadiusX(r);
        barrel.setRadiusY(r);
        barrel.setLength(40);
        cap = new Circle(x, y, r - 5);
        cap.setFill(tankColor);
    }

    /**
     * 坦克移动
     */
    public void move(int direction) {
        this.direction = direction;
        for (int i = 0; i < speed; i++) {
            if (!testOnly1()) {
                return;
            }
            moveOnly1();
        }
    }

    private boolean testOnly1() {
        double tempX = x;
        double tempY = y;
        switch (direction) {
            case 0://向上移动
                tempY--;
                break;
            case 1://向下移动
                tempY++;
                break;
            case 2://向左移动
                tempX--;
                break;
            case 3://向右移动
                tempX++;
                break;
            case 4://右上移动
                tempX += Math.pow(2, 0.5) / 2;
                tempY -= Math.pow(2, 0.5) / 2;
                break;
            case 5://左下移动
                tempX -= Math.pow(2, 0.5) / 2;
                tempY += Math.pow(2, 0.5) / 2;
                break;
            case 6://左上移动
                tempX -= Math.pow(2, 0.5) / 2;
                tempY -= Math.pow(2, 0.5) / 2;
                break;
            case 7://右下移动
                tempX += Math.pow(2, 0.5) / 2;
                tempY += Math.pow(2, 0.5) / 2;
                break;
        }
        for (GameUnit u : BBTankPane.getInstance().getGameUnitList()) {
            if (u instanceof Wall) {
                Wall w = (Wall) u;
                if (inWall(tempX, tempY, w)) {
                    return false;
                }
            } else if (u instanceof Tank && u != this) {
                Tank t = (Tank) u;
                if (inTank(t)) {
                    survival = false;
                    t.setSurvival(false);
                    return false;
                }
            }
        }
        return true;
    }

    private boolean inWall(double tankX, double tankY, Wall wall) {
        switch (wall.getWallType()) {
            case 0:
            case 1:
                if (tankX >= wall.getX() - 16 && tankX <= wall.getX() + 16
                        && tankY >= wall.getY() - 16 && tankY <= wall.getY() + 16) {
                    return true;
                }
            case 2:
                if (getDistance(tankX, tankY, wall.getX(), wall.getY()) <= 16) {
                    return true;
                }
        }
        return false;
    }

    private boolean inTank(Tank tank) {
        if (getDistance(tank.getX(), tank.getY(), x, y) < r + tank.getR()) {
            return true;
        }
        return false;
    }

    private double getDistance(double x1, double y1, double x2, double y2) {
        return Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
    }

    /**
     * 坦克仅移动一步
     */
    private void moveOnly1() {
        switch (direction) {
            case 0://向上移动
                y--;
                break;
            case 1://向下移动
                y++;
                break;
            case 2://向左移动
                x--;
                break;
            case 3://向右移动
                x++;
                break;
            case 4://右上移动
                x += Math.pow(2, 0.5) / 2;
                y -= Math.pow(2, 0.5) / 2;
                break;
            case 5://左下移动
                x -= Math.pow(2, 0.5) / 2;
                y += Math.pow(2, 0.5) / 2;
                break;
            case 6://左上移动
                x -= Math.pow(2, 0.5) / 2;
                y -= Math.pow(2, 0.5) / 2;
                break;
            case 7://右下移动
                x += Math.pow(2, 0.5) / 2;
                y += Math.pow(2, 0.5) / 2;
                break;
        }
    }

    public void shot() {
        double bulletX = x;
        double bulletY = y;
        switch (direction) {
            case 0:
                bulletY -= 9;
                break;
            case 1:
                bulletY += 9;
                break;
            case 2:
                bulletX -= 9;
                break;
            case 3:
                bulletX += 9;
                break;
            case 4:
                bulletX += 9 * Math.pow(2, 0.5) / 2;
                bulletY -= 9 * Math.pow(2, 0.5) / 2;
                break;
            case 5:
                bulletX -= 9 * Math.pow(2, 0.5) / 2;
                bulletY += 9 * Math.pow(2, 0.5) / 2;
                break;
            case 6:
                bulletX -= 9 * Math.pow(2, 0.5) / 2;
                bulletY -= 9 * Math.pow(2, 0.5) / 2;
                break;
            case 7:
                bulletX += 9 * Math.pow(2, 0.5) / 2;
                bulletY += 9 * Math.pow(2, 0.5) / 2;
                break;
        }
        BBTankPane.getInstance().addUnitLater(new Bullet(bulletX, bulletY, direction));
    }

    /**
     * 若坦克已消亡，生成爆炸
     */
    @Override
    public void update() {
        super.update();
        if (!survival) {
            BBTankPane.getInstance().addUnitLater(new Bomb(x, y, r, tankColor));
            return;
        }
    }

    @Override
    public void redraw(Pane parent) {
        //同步坦克
        chassis.setCenterX(x);
        chassis.setCenterY(y);
        battery.setCenterX(x);
        battery.setCenterY(y);
        cap.setCenterX(x);
        cap.setCenterY(y);
        //修改炮管
        barrel.setCenterX(x);
        barrel.setCenterY(y);
        switch (direction) {
            case 0://炮管向上
                barrel.setStartAngle(70);
                break;
            case 1://炮管向下
                barrel.setStartAngle(70 + 180);
                break;
            case 2://炮管向左
                barrel.setStartAngle(70 + 90);
                break;
            case 3://炮管向右
                barrel.setStartAngle(70 + 270);
                break;
            case 4://炮管右上
                barrel.setStartAngle(25);
                break;
            case 5://炮管左下
                barrel.setStartAngle(25 + 180);
                break;
            case 6://炮管左上
                barrel.setStartAngle(25 + 90);
                break;
            case 7://炮管右下
                barrel.setStartAngle(25 + 270);
                break;
        }
        parent.getChildren().addAll(chassis, battery, barrel, cap);
    }
}