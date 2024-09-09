import javafx.application.Platform;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class BBTankPane extends Pane implements Runnable {
    private static BBTankPane instance = new BBTankPane();
    private List<GameUnit> gameUnitList;//游戏单元列表
    private List<GameUnit> unitListAddT;//单元添加暂存
    private List<GameUnit> unitListDelT;//单元删除暂存
    private HeroTank heroA;//玩家坦克A
    private HeroTank heroB;//玩家坦克B
    private boolean needRun = true;//是否仍需运行

    public List<GameUnit> getGameUnitList() {
        return gameUnitList;
    }

    public static BBTankPane getInstance() {
        return instance;
    }

    protected BBTankPane() {
        //背景
        setBackground(new Background(new BackgroundFill(
                Color.BLACK, null, null)));//颜色、圆角、插图
        //元素
        gameUnitList = new Vector<>();
        unitListAddT = new Vector<>();
        unitListDelT = new Vector<>();

        launchDefaultMap();
        redrawUnits(this);
    }

    private void launchDefaultMap() {
        heroA = new HeroTank(300, 300, 0, Color.YELLOW);
        heroB = new HeroTank(700, 300, 0, Color.GREEN.brighter().brighter());
        gameUnitList.add(heroA);
        gameUnitList.add(heroB);
        //添加敌人坦克
        List<EnemyTank> enemyTanks = new ArrayList<>();
        Color enemyTankColor = Color.AQUA;
        for (int i = 0; i < 9; i++) {
            enemyTanks.add(new EnemyTank(100 * i + 100, 100, 1, enemyTankColor));
            enemyTanks.add(new EnemyTank(100 * i + 100, 500, 0, enemyTankColor));
        }
        gameUnitList.addAll(enemyTanks);
        //添加墙
        List<Wall> walls = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            //不可摧毁墙
            walls.add(new Wall(100 * i + 100, 150, 0));
            walls.add(new Wall(100 * i + 100, 450, 0));
            if (i == 0 || i == 4 || i == 8) {
                walls.add(new Wall(100 * i + 100, 300, 0));
            }
            //可摧毁墙
            walls.add(new Wall(100 * i + 100 - 16, 150, 1));
            walls.add(new Wall(100 * i + 100 - 16, 450, 1));
            walls.add(new Wall(100 * i + 100 + 16, 150, 1));
            walls.add(new Wall(100 * i + 100 + 16, 450, 1));
            if (i == 0 || i == 4 || i == 8) {
                walls.add(new Wall(100 * i + 100, 300 - 16, 1));
                walls.add(new Wall(100 * i + 100, 300 + 16, 1));
            }
            if (i == 1 || i == 3 || i == 5 || i == 7) {
                walls.add(new Wall(100 * i + 100, 300 - 80, 1));
                walls.add(new Wall(100 * i + 100, 300 - 16, 1));
                walls.add(new Wall(100 * i + 100, 300, 1));
                walls.add(new Wall(100 * i + 100, 300 + 16, 1));
                walls.add(new Wall(100 * i + 100, 300 + 80, 1));
            }
            //可跨越墙
            walls.add(new Wall(100 * i + 100, 150 + 16, 2));
            walls.add(new Wall(100 * i + 100, 450 - 16, 2));
            if (i == 1 || i == 3 || i == 5 || i == 7) {
                walls.add(new Wall(100 * i + 100, 300 - 64, 2));
                walls.add(new Wall(100 * i + 100, 300 - 48, 2));
                walls.add(new Wall(100 * i + 100, 300 - 32, 2));
                walls.add(new Wall(100 * i + 100, 300 + 32, 2));
                walls.add(new Wall(100 * i + 100, 300 + 48, 2));
                walls.add(new Wall(100 * i + 100, 300 + 64, 2));
            }
        }
        gameUnitList.addAll(walls);
    }

    public void createKeyListener() {
        setOnKeyPressed(e -> {//按下
            KeyCode kc = e.getCode();
            switch (kc) {
                case W:
                    heroA.setNeedUp(true);//↑
                    break;
                case UP:
                    heroB.setNeedUp(true);//↑
                    break;
                case S:
                    heroA.setNeedDown(true);//↓
                    break;
                case DOWN:
                    heroB.setNeedDown(true);//↓
                    break;
                case A:
                    heroA.setNeedLeft(true);//←
                    break;
                case LEFT:
                    heroB.setNeedLeft(true);//←
                    break;
                case D:
                    heroA.setNeedRight(true);//→
                    break;
                case RIGHT:
                    heroB.setNeedRight(true);//→
                    break;
                case J:
                    heroA.setNeedShot(true);//biu~
                    break;
                case NUMPAD1:
                    heroB.setNeedShot(true);//biu~
                    break;
                default:
                    return;
            }
        });
        setOnKeyReleased(e -> {//释放
            KeyCode kc = e.getCode();
            switch (kc) {
                case W:
                    heroA.setNeedUp(false);//↑
                    break;
                case UP:
                    heroB.setNeedUp(false);//↑
                    break;
                case S:
                    heroA.setNeedDown(false);//↓
                    break;
                case DOWN:
                    heroB.setNeedDown(false);//↓
                    break;
                case A:
                    heroA.setNeedLeft(false);//←
                    break;
                case LEFT:
                    heroB.setNeedLeft(false);//←
                    break;
                case D:
                    heroA.setNeedRight(false);//→
                    break;
                case RIGHT:
                    heroB.setNeedRight(false);//→
                    break;
                case J:
                    heroA.setNeedShot(false);//biu~
                    break;
                case NUMPAD1:
                    heroB.setNeedShot(false);//biu~
                    break;
                default:
                    return;
            }
        });
    }

    @Override
    public void run() {
        while (needRun) {
            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            Platform.runLater(() -> {
                //先更新单元
                updateUnits();
                //再更新列表
                updateUnitList();
                //再绘制
                redrawUnits(this);
            });
        }
    }

    public void addUnitLater(GameUnit unit) {
        unitListAddT.add(unit);
    }

    public void delUnitLater(GameUnit unit) {
        unitListDelT.add(unit);
    }

    public void setNeedRun(boolean needRun) {
        this.needRun = needRun;
    }


    private void updateUnits() {//更新单元
        for (GameUnit gameUnit : gameUnitList) {
            gameUnit.update();
        }
    }

    private void updateUnitList() {//更新单元列表
        if (!unitListAddT.isEmpty()) {//有单元要添加
            for (GameUnit u : unitListAddT) {
                gameUnitList.add(u);
                System.out.println("游戏中添加了一个单元，当前单元数量：" + gameUnitList.size());
            }
            unitListAddT.clear();
        }
        if (!unitListDelT.isEmpty()) {//有单元要删除
            for (GameUnit u : unitListDelT) {
                gameUnitList.remove(u);
                System.out.println("游戏中删除了一个单元，当前单元数量：" + gameUnitList.size());
            }
            unitListDelT.clear();
        }
    }

    private void redrawUnits(Pane parent) {//重绘单元列表
        //清空面板
        getChildren().clear();
        for (GameUnit gameUnit : gameUnitList) {
            gameUnit.redraw(parent);
        }
    }
}