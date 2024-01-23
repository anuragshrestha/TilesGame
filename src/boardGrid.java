
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;


import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class boardGrid extends Parent {

    public static final int TILE_SIZE = 60;
    public static final int GRID_SIZE = 5;

    GridPane gridPane;
    List<Color> colorList;
    HashMap<StackPane, List<Color>> tileMap = new HashMap<>();
    StackPane selectedTile = null;
    ScoreBoard scoreBoard = new ScoreBoard();

    public boardGrid(){
        tile();
        VBox layout = new VBox(gridPane, scoreBoard.getVbox());
        this.getChildren().add(layout);
    }

    /**
     * This method adds all the color to coloList and then shuffles
     * the list randomly.
     */
    public void initializeColor(){
        Color[] colors = new Color[]{Color.RED, Color.PURPLE, Color.GREEN,
                Color.YELLOW, Color.BLACK};


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
     * horizontal gap along with padding. Then it creates a StackPane
     * It starts removing the color from the end of colorList and
     * checks if the same color is being shared among all the three
     * boxes of the same tile. If the same color is share then it adds
     * the color again to the list and shuffle it and assigns it again.
     * After that it creates three rectangle boxes of different size and
     * assign them the color such that no same color is being shared among
     * those boxes. At the end all those boxes are added to the stack pane
     * and stack pane is added to the grid pane in each specific index.
     */

    public  void tile(){

        gridPane = new GridPane();
        initializeColor();

        // Setting the vertical and Horizontal gap between the tiles
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

                // Ensure that no two rectangles have the same color
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

                // Store the colors for each tile
                colors.add(outerColor);
                colors.add(middleColor);
                colors.add(innerColor);
                tileMap.put(stackPane, colors);

                Rectangle outerBox = new Rectangle( TILE_SIZE ,  TILE_SIZE, outerColor);
                Rectangle middleBox = new Rectangle((double) TILE_SIZE / 1.2,
                        (double) TILE_SIZE / 1.5, middleColor);
                Rectangle innerBox = new Rectangle((double) TILE_SIZE / 2,
                        (double) TILE_SIZE / 2, innerColor);



                //Adding all the rectangles to the stackPane so that each inner
                // rectangles is at the center
                stackPane.getChildren().addAll(outerBox,middleBox,innerBox);
                stackPane.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> handleTileClick(stackPane));
                gridPane.add(stackPane, i, j);

            }
        }
    }


    public void handleTileClick(StackPane clickedTile) {

        if (selectedTile != null) {
            selectedTile.setStyle(" ");
            clickedTile.setStyle("-fx-border-color: blue; -fx-border-width: 4;");
            removeCommonColors(selectedTile, clickedTile);
        }
        selectedTile = clickedTile;
        selectedTile.setStyle("-fx-border-color: blue; -fx-border-width: 4;");

    }

     public  void removeCommonColors(StackPane firstTile, StackPane secondTile){

       List<Color> colors1 = tileMap.get(firstTile);
       List<Color> colors2 = tileMap.get(secondTile);

       List<Color> commonColors = colors1.stream().filter(colors2:: contains).toList();

       colors1.removeAll(commonColors);
       colors2.removeAll(commonColors);

        if(!commonColors.isEmpty()) {
          scoreBoard.incrementStreak();
        } else {
            scoreBoard.resetStreak();
        }

       //Call the displayTile class to update the GUI Display
        DisplayTile.updateDisplay(firstTile,colors1,TILE_SIZE);
        DisplayTile.updateDisplay(secondTile,colors2,TILE_SIZE);
     }






    @Override
    public Node getStyleableNode() {
        return super.getStyleableNode();
    }

}
