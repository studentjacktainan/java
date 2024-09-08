import javafx.scene.paint.Color;

/**
 * @Date 2023/3/1 20:19
 * @Created by 邦邦拒绝魔抗
 * @Description 敌人坦克
 */
public class EnemyTank extends Tank {
    public EnemyTank(double x, double y, int direction, Color tankColor) {
        super(x, y, direction, tankColor);
        speed = 2;
    }
}