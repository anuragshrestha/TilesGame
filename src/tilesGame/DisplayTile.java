package tilesGame;


import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import java.util.List;

/**
 * This class updates the GUI Display each time when we remove
 * the common colors shared between two tiles.
 */
public class DisplayTile {


    /**
     * This method is called at the boardGrid class, removeCommonColors method,
     * to update the GUI Display everytime, a color is being removed.
     * It takes the clicked tile, list of colors the tile had and the
     * grid size as a parameter. At first, it removes all the color from the
     * selected tile and then adds the remaining colors to the tiles again.
     * @param tile the clicked tile
     * @param colors the list of colors, the tile has
     * @param tileSize the size of the tile
     */
    public static void updateDisplay(StackPane tile, List<Color> colors, double tileSize){


        tile.getChildren().clear();

        double[] sizes = new double[]{tileSize, tileSize/ 1.2, tileSize / 2};

        for (int i = 0; i < colors.size(); i++) {
            Color color = colors.get(i);
            Rectangle rectangle = new Rectangle(sizes[i], sizes[i], color);
            tile.getChildren().add(rectangle);
        }

    }
}
