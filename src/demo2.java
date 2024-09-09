import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
public class demo2 extends Application {

    public static void main(String[] args) {
        launch(args);//调用start
    }

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxmls/hello-view.fxml"));
        VBox root = fxmlLoader.load();

        Scene scene = new Scene(root);

        stage.setTitle("User Registration");
        stage.setScene(scene);

        // Maximize or set specific size
        stage.setWidth(800);
        stage.setHeight(600);

        stage.show();
    }
}