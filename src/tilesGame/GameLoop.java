package tilesGame;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Name: Anurag Shrestha
 * CS 361L
 * This is the main class that controls the game by calling the
 * boardGrid class object. The scene has a width of 455 and
 * height of 520.
 */
public class GameLoop extends Application {


    //Launch the game when user starts it.
    public static void main(String[] args) {
        launch(args);
    }



    //Sets the scene and show the primary stage.
    @Override
    public void start(Stage primaryStage) throws Exception {


        BoardGrid board = new BoardGrid();

        Scene scene = new Scene(board,455,520);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}