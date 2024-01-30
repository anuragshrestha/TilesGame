package tilesGame;


import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.input.MouseEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/**
 * This class contains all the tiles with three different
 * size of rectangles with unique colors on it. Each rectangle
 * boxes are stored in Stack pane and a HashMap is used to
 * keep track of the list of colors each stack pane has.
 */

public class BoardGrid extends Parent {

    private static final int TILE_SIZE = 60;
    private static final int GRID_SIZE = 6;

    GridPane gridPane;
    private List<Color> colorList;
    HashMap<StackPane, List<Color>> tileMap = new HashMap<>();
    StackPane selectedTile = null;
    ScoreBoard scoreBoard = new ScoreBoard();

    /**
     * It first calls the tile method that initializes
     * the gridPane with different size of tiles and add
     * color to the tiles. Then it creates a BorderPane
     * to add the gridPane at the center,which holds all
     * the tiles. Then adds the current and longest streaks
     * text at the bottom.
     */
    public BoardGrid(){
        tile();

        BorderPane borderPane = new BorderPane();
        borderPane.setCenter(gridPane);
        borderPane.setBottom(scoreBoard.getVbox());
       this.getChildren().add(borderPane);
    }

    /**
     * This method adds all the color to coloList and then shuffles
     * the list randomly. It has a total of six unique colors
     */
    private void initializeColor(){
        Color[] colors = new Color[]{Color.RED, Color.PURPLE, Color.GREEN,
                Color.YELLOW, Color.BLACK,Color.AQUAMARINE};


        int numberOfRectanglesPerTile = 3;
        int totalNumberOfRectangles = GRID_SIZE * GRID_SIZE *
                numberOfRectanglesPerTile;

        colorList = new ArrayList<>();
        while (colorList.size() < totalNumberOfRectangles) {
            Collections.addAll(colorList, colors);
        }

        Collections.shuffle(colorList);
    }

    /**
     * This method creates a gridPane and sets the vertical and
     * horizontal gap along with padding. Then it creates a StackPane.
     * It starts removing the color from the end of colorList and
     * checks if the same color is being shared among all the three
     * boxes of the same tile. If the same color is share then it adds
     * the color again to the list and shuffle it and assigns it again.
     * After that it creates three rectangle boxes of different size and
     * assign them the color such that no same color is being shared among
     * the same tiles box. At the end all those rectangle boxes are added
     * to the stack pane and stack pane is added to the grid pane in each
     * specific index. When a tile is being clicked then it calls the
     * handleTileClick method.
     */

    private void tile(){

        gridPane = new GridPane();
        initializeColor();

        // Giving vertical and Horizontal gap between the tiles
        gridPane.setHgap(15);
        gridPane.setVgap(15);

        // Giving padding in all the side of the GridPane
        gridPane.setPadding(new Insets(8));

        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {

                StackPane stackPane = new StackPane();
                List<Color> colors = new ArrayList<>();

                Color outerColor = colorList.remove(colorList.size() - 1);
                Color middleColor = colorList.remove(colorList.size() - 1);
                Color innerColor = colorList.remove(colorList.size() - 1);

                // Making sure that no two rectangles shares the same color.
                while (middleColor.equals(outerColor)) {
                    colorList.add(middleColor);
                    Collections.shuffle(colorList);
                    middleColor = colorList.remove(colorList.size() - 1);
                }

                while (innerColor.equals(middleColor) || innerColor.equals(outerColor)) {
                    colorList.add(innerColor);
                    Collections.shuffle(colorList);
                    innerColor = colorList.remove(colorList.size() - 1);
                }

                colors.add(outerColor);
                colors.add(middleColor);
                colors.add(innerColor);
                tileMap.put(stackPane, colors);

                Rectangle outerBox = new Rectangle( TILE_SIZE ,  TILE_SIZE, outerColor);
                Rectangle middleBox = new Rectangle((double) TILE_SIZE / 1.2,
                        (double) TILE_SIZE / 1.5, middleColor);
                Rectangle innerBox = new Rectangle((double) TILE_SIZE / 2,
                        (double) TILE_SIZE / 2, innerColor);


                stackPane.getChildren().addAll(outerBox,middleBox,innerBox);
                stackPane.addEventHandler(MouseEvent.MOUSE_CLICKED, e ->
                        handleTileClick(stackPane));
                gridPane.add(stackPane, i, j);

            }
        }
    }


    /**
     * This method is called when the user clicks a tile. It
     * first checks if the selected tile is null, which means
     * no tile is being selected till now. If it is null then
     * it assigns the value of clicked tile to the selected tile
     * and gives it a blue border to represent that it is the
     * currently selected tile. If a second tile is clicked, and
     * it is not equal to the first selected tile then it removes
     * the blue border of the first tile and gives the same blue
     * border to the new tile. It then calls the removeCommonColors
     * method that return true if the second selected tile was
     * empty after removing the common colors. if it was empty
     * then the user needs to select choosing the tiles again from
     * start, else, the second selected tiles will become the
     * new current tile.
     * @param clickedTile the tile which was clicked or pressed
     */

    private void handleTileClick(StackPane clickedTile) {


        if (selectedTile == null) {
            selectedTile = clickedTile;
            selectedTile.setStyle("-fx-border-color: blue; -fx-border-width: 4;");
        }
        else if (selectedTile != clickedTile) {

            selectedTile.setStyle(" ");
            clickedTile.setStyle("-fx-border-color: blue; -fx-border-width: 4;");
            boolean isEmpty = removeCommonColors(selectedTile, clickedTile);

            if (isEmpty) {

                selectedTile = null;
                clickedTile.setStyle(" ");
            }
            else {
                selectedTile = clickedTile;
            }
        }

       //checks if the game is over. if it is then exit the Game by
        // showing an Alert Box.
        if(gameOver()){
            exitGame();
        }
    }


    /**
     * This method takes two stackPane as a parameter, where the firstTile
     * is the tile selected at first, and secondTile is the tile selected at
     * second. It stores all the colors of the selected tiles in a List of
     * colors and then remove the shared or common colors between two tiles.
     * It then updates the streaks if common colors where removed or reset
     * the streaks. And at last, it updates the GUI display by calling the
     * updateDisplay method from the DisplayTile class.
     * @param firstTile the tile clicked at first or previous second tile
     * @param secondTile the tile clicked at second
     * @return true if the second selected tile becomes empty else false
     */
     private  boolean removeCommonColors(StackPane firstTile, StackPane secondTile){

       List<Color> colors1 = tileMap.get(firstTile);
       List<Color> colors2 = tileMap.get(secondTile);

       List<Color> commonColors = colors1.stream().filter(colors2:: contains).toList();

       colors1.removeAll(commonColors);
       colors2.removeAll(commonColors);

         boolean hasCommonColors = !commonColors.isEmpty();
         if (hasCommonColors) {
             scoreBoard.updateStreak(true);
         } else {
             scoreBoard.updateStreak(false);
         }

       //Call the displayTile class to update the GUI Display
        DisplayTile.updateDisplay(firstTile,colors1,TILE_SIZE);
        DisplayTile.updateDisplay(secondTile,colors2,TILE_SIZE);


          return colors2.isEmpty();
     }


    /**
     * This method return true when the game is over.
     * @return true if game is over, else false.
     */
      private  boolean gameOver(){

         for(List<Color> colors: tileMap.values()){
              if(!colors.isEmpty()){
                  return false;
              }
          }
           return true;
      }

    /**
     * This method is called when the game is over.
     * And when the user clicks ok then the game
     * will exit.
     */

    private void exitGame(){

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Game Over!");
        alert.setHeaderText("Congratulations, you won!");
        alert.setContentText("Click Ok to exit the Game");
        alert.showAndWait().ifPresent(response -> {
              Platform.exit();
        });
    }


    @Override
    public Node getStyleableNode() {
        return super.getStyleableNode();
    }

}
