import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class GameLoop extends Application {


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {


        boardGrid board = new boardGrid();

        Scene scene = new Scene(board,380,440);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}