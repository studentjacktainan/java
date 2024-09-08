/**
 * @Date 2023/2/27 16:23
 * @Created by 邦邦拒绝魔抗
 * @Description 游戏单元，可更新，可重绘
 */
public abstract class GameUnit implements Redrawable {
    protected double x;//x坐标
    protected double y;//y坐标
    protected int tier = 0;//绘制层级
    protected boolean survival = true;//是否存活

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public boolean isSurvival() {
        return survival;
    }

    public void setSurvival(boolean survival) {
        this.survival = survival;
    }

    /**
     * 将单元的存活状态体现在列表中，实际的删除会在重绘前施行
     */
    @Override
    public void update() {
        if (!survival) {
            BBTankPane.getInstance().delUnitLater(this);
        }
    }
}