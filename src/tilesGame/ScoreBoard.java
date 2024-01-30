package tilesGame;


import javafx.geometry.Insets;
import javafx.scene.layout.VBox;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import  javafx.scene.text.Font;
import javafx.scene.paint.Color;

public class ScoreBoard {

    private int currentStreak = 0;
    private int longestStreak = 0;

    private Text currentStreakText = new Text("Current Streaks: 0");
   private Text longestStreakText = new Text("Longest Streaks: 0");

   VBox vbox;


    /**
     * Sets the font size, color  of the current and longest streaks text.
     * Then creates a Vbox and adds the currentStreak and longestStreak text
     */
    public ScoreBoard() {


        currentStreakText.setFont(Font.font("Arial", FontWeight.BOLD, 15));
        longestStreakText.setFont(Font.font("Arial", FontWeight.BOLD, 15));

        // Giving the text a blue color
        currentStreakText.setFill(Color.PURPLE);
        longestStreakText.setFill(Color.PURPLE);


        vbox = new VBox(10);
        vbox.setPadding(new Insets(10));
        vbox.getChildren().addAll(currentStreakText, longestStreakText);
    }

    /**
     * This method takes a boolean as a parameter, and calls the
     * incrementStreak method if the streaks had common colors,
     * else calls the resetStreak method.
     * @param hasCommonColors shared colors between two selected tiles
     */
    public void updateStreak(boolean hasCommonColors) {
        if (hasCommonColors) {
            incrementStreak();
        } else {
            resetStreak();
        }
    }

    /**
     * Increments the streaks and updates the currentStreaks
     * if it is more than the longestStreak. And then updates
     * the score in the GUI display.
     */
    private void incrementStreak() {
        currentStreak++;
        if (currentStreak > longestStreak) {
            longestStreak = currentStreak;
        }
        updateText();
    }

    /**
     * Resets the current streaks to zero when
     * no common colors are found.
     */
    private void resetStreak() {
        currentStreak = 0;
        updateText();
    }


    /**
     * updates the GUI display when the streaks are changed.
     */
    private void updateText() {

        currentStreakText.setText("Current Streaks: " + currentStreak);
        longestStreakText.setText("Longest Streaks: " + longestStreak);
    }

    /**
     * Returns the Vbox where the contains the currentStreakText and
     * longestStreakText
     * @return Vbox
     */

    public VBox getVbox() {

        return vbox;

    }
}
