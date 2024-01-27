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

    public ScoreBoard() {

        // Setting the font for the text right here
        currentStreakText.setFont(Font.font("Arial", FontWeight.BOLD, 15));
        longestStreakText.setFont(Font.font("Arial", FontWeight.BOLD, 15));

        // Giving the text a blue color
        currentStreakText.setFill(Color.PURPLE);
        longestStreakText.setFill(Color.PURPLE);


        vbox = new VBox(10);
        vbox.setPadding(new Insets(10));
        vbox.getChildren().addAll(currentStreakText, longestStreakText);
    }

    public void updateStreak(boolean hasCommonColors) {
        if (hasCommonColors) {
            incrementStreak();
        } else {
            resetStreak();
        }
    }

    private void incrementStreak() {
        currentStreak++;
        if (currentStreak > longestStreak) {
            longestStreak = currentStreak;
        }
        updateText();
    }

    private void resetStreak() {
        currentStreak = 0;
        updateText();
    }

    private void updateText() {

        currentStreakText.setText("Current Streaks: " + currentStreak);
        longestStreakText.setText("Longest Streaks: " + longestStreak);
    }

    public VBox getVbox() {
        return vbox;
    }
}
