package application;

import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class SelectRoundsScreen implements AppScreen {

    private final ScreenManager manager;
    private final GameData gameData;

    public SelectRoundsScreen(ScreenManager manager, GameData gameData) {
        this.manager = manager;
        this.gameData = gameData;
    }

    @Override
    public Parent getView() {
        Label title = new Label("Select Rounds");
        title.getStyleClass().add("title");

        Label instruction = new Label("Enter number of rounds (must be greater than 0):");
        TextField roundsField = new TextField();
        roundsField.setId("roundsField");

        Label errorLabel = new Label();
        errorLabel.setStyle("-fx-text-fill: red;");

        Button startButton = new Button("Play");
        Button backButton = new Button("Back to Menu");

        startButton.setOnAction(e -> {
            try {
                int rounds = Integer.parseInt(roundsField.getText().trim());

                if (rounds <= 0) {
                    errorLabel.setText("Please enter an integer greater than 0.");
                    return;
                }

                gameData.setRounds(rounds);
                errorLabel.setText("");
                manager.show(Main.GAME);

            } catch (NumberFormatException ex) {
                errorLabel.setText("Please enter a valid integer.");
            }
        });

        backButton.setOnAction(e -> manager.show(Main.MENU));

        VBox center = new VBox(15, instruction, roundsField, errorLabel, startButton, backButton);
        center.setAlignment(Pos.CENTER);

        BorderPane root = new BorderPane();
        root.setTop(title);
        BorderPane.setAlignment(title, Pos.CENTER);
        root.setCenter(center);

        return root;
    }
}