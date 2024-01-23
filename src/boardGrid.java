
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class boardGrid extends Parent {

    public static final int TILE_SIZE = 60;
    public static final int GRID_SIZE = 5;
    GridPane gridPane;
    List<Color> colorList;
    public boardGrid(){
        tile();
        this.getChildren().add(gridPane);
    }

    /**
     * This method adds all the color to coloList and then shuffles
     * the list randomly.
     */
    public void initializeColor(){
        Color[] colors = new Color[]{Color.RED, Color.BLUE, Color.GREEN,
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

                Rectangle outerBox = new Rectangle( TILE_SIZE ,  TILE_SIZE, outerColor);
                Rectangle middleBox = new Rectangle((double) TILE_SIZE / 1.2,
                        (double) TILE_SIZE / 1.5, middleColor);
                Rectangle innerBox = new Rectangle((double) TILE_SIZE / 2,
                        (double) TILE_SIZE / 2, innerColor);

                //Adding all the rectangles to the stackPane so that each inner
                // rectangles is at the center
                stackPane.getChildren().addAll(outerBox,middleBox,innerBox);
                gridPane.add(stackPane, i, j);
            }
        }
    }


    @Override
    public Node getStyleableNode() {
        return super.getStyleableNode();
    }

}
