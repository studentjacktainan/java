import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 * @Date 2023/2/27 12:41
 * @Created by 邦邦拒绝魔抗
 * @Description TODO
 */
public class Demo extends Application {

    private BorderPane borderPane;
    private BBTankPane bbTankPane;

    public static void main(String[] args) {
        launch(args);//调用start
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        borderPane = new BorderPane();
        bbTankPane = BBTankPane.getInstance();
        borderPane.setCenter(bbTankPane);
        Scene scene = new Scene(borderPane, 1000, 600);

        primaryStage.setTitle("BBTank06");
        primaryStage.setResizable(false);
        primaryStage.setScene(scene);
        primaryStage.show();

        bbTankPane.requestFocus();//添加焦点
        bbTankPane.createKeyListener();//键盘监听
        new Thread(bbTankPane).start();//提供线程

        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                bbTankPane.setNeedRun(false);//关闭窗口后，各元素的更新重绘也终止
            }
        });
    }
}