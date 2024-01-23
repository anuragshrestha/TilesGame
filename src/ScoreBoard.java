import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class ScoreBoard {

    public int currentStreak = 0;
    public int longestStreak = 0;

    public Text currentStreakText = new Text("Current Streaks: 0");
    public Text longestStreakText = new Text("Longest Streaks: 0");

   VBox vbox;

    public ScoreBoard() {
        vbox = new VBox(10);
        vbox.getChildren().addAll(currentStreakText, longestStreakText);
    }

    public void incrementStreak() {
        currentStreak++;
        if (currentStreak > longestStreak) {
            longestStreak = currentStreak;
        }
        updateText();
    }

    public void resetStreak() {
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
