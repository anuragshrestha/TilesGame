import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.List;

/**
 * This class updated the GUI Display each time when we remove
 * the common colors shared between two tiles.
 */
public class DisplayTile {

    public static void updateDisplay(StackPane tile, List<Color> colors, double tileSize){

        // Clearing all the  existing rectangles
        tile.getChildren().clear();

        double[] sizes = new double[]{tileSize, tileSize/ 1.2, tileSize / 2};

        for (int i = 0; i < colors.size(); i++) {
            Color color = colors.get(i);
            Rectangle rectangle = new Rectangle(sizes[i], sizes[i], color);
            tile.getChildren().add(rectangle);
        }

    }
}
