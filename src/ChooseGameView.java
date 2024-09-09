import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class ChooseGameView {

    @FXML
    private Button singleGame;
    @FXML
    private Button createRoom;
    @FXML
    private Button joinRoom;

    private BorderPane borderPane;
    private BBTankPane bbTankPane;
    private Stage stage;

    // Inject the primary stage via a setter method or constructor

    @FXML
    public void initialize() {
        // Ensure buttons are connected to methods
        singleGame.setOnAction(this::singleGameClicked);
        createRoom.setOnAction(this::createRoomClicked);
        joinRoom.setOnAction(this::joinRoomClicked);
    }

    @FXML
    public void singleGameClicked(ActionEvent actionEvent) {
        stage=new Stage();
        borderPane = new BorderPane();
        bbTankPane = BBTankPane.getInstance(); // Ensure this returns a valid instance
        borderPane.setCenter(bbTankPane);
        Scene scene = new Scene(borderPane, 1000, 600);

        stage.setTitle("BBTank06");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();

        bbTankPane.requestFocus(); // Request focus for key inputs
        bbTankPane.createKeyListener(); // Setup key listener
        new Thread(bbTankPane).start(); // Start the game thread

        stage.setOnCloseRequest(event -> bbTankPane.setNeedRun(false)); // Stop game updates on window close
    }

    @FXML
    public void multipleGamesClicked(ActionEvent actionEvent) {
        // Handle the multiple games button click
        System.out.println("Multiple Games Clicked");
    }

    public void createRoomClicked(ActionEvent actionEvent) {
    }

    public void joinRoomClicked(ActionEvent actionEvent) {
    }
}
