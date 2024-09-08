import javafx.scene.layout.Pane;

/**
 * @Date 2023/2/28 13:21
 * @Created by 邦邦拒绝魔抗
 * @Description 可重绘的
 */
public interface Redrawable extends Updatable {
    public void redraw(Pane parent);
}