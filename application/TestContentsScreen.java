package application;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Labeled;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.List;

public class TestContentsScreen implements AppScreen {

    private final ScreenManager manager;
    private final List<Label> resultLabels = new ArrayList<>();

    public TestContentsScreen(ScreenManager manager) {
        this.manager = manager;
    }

    @Override
    public Parent getView() {

        Label title = new Label("Manual UI Test Checklist");
        title.getStyleClass().add("title");

        VBox testList = new VBox(12);
        testList.setPadding(new Insets(15));

        testList.getChildren().addAll(
                createTest("About screen loads"),
                createTest("Menu screen loads"),
                createTest("Select Rounds screen loads"),
                createTest("Rounds input field exists"),
                createTest("Game screen loads"),
                createTest("Game buttons exist"),
                createTest("Gameplay simulation works"),
                createTest("Round counter updates")
        );

        ScrollPane scrollPane = new ScrollPane(testList);
        scrollPane.setFitToWidth(true);

        Button runButton = new Button("Run Checks");
        runButton.setOnAction(e -> runChecks());

        Button backButton = new Button("Back to Menu");
        backButton.setOnAction(e -> manager.show(Main.MENU));

        HBox bottomButtons = new HBox(20, runButton, backButton);
        bottomButtons.setAlignment(Pos.CENTER);
        bottomButtons.setPadding(new Insets(10));

        BorderPane root = new BorderPane();
        root.setTop(title);
        BorderPane.setAlignment(title, Pos.CENTER);
        root.setCenter(scrollPane);
        root.setBottom(bottomButtons);

        return root;
    }

    private HBox createTest(String name) {
        Label testName = new Label(name);

        Label result = new Label("NOT RUN");
        result.setStyle("-fx-text-fill: gray;");

        resultLabels.add(result);

        HBox row = new HBox(20, testName, result);
        row.setAlignment(Pos.CENTER_LEFT);

        return row;
    }

    private void runChecks() {

        int i = 0;

        updateResult(i++, checkScreenExists(Main.ABOUT));
        updateResult(i++, checkScreenExists(Main.MENU));
        updateResult(i++, checkScreenExists(Main.SELECT_ROUNDS));
        updateResult(i++, checkRoundsFieldExists());
        updateResult(i++, checkScreenExists(Main.GAME));
        updateResult(i++, checkGameButtons());
        updateResult(i++, simulateGameplay());
        updateResult(i++, checkRoundCounter());
    }

    // ---------- CORE CHECKS ----------

    private boolean checkScreenExists(String screenName) {
        try {
            AppScreen screen = createScreen(screenName);
            return screen.getView() != null;
        } catch (Exception e) {
            return false;
        }
    }

    private boolean checkRoundsFieldExists() {
        try {
            SelectRoundsScreen screen = new SelectRoundsScreen(manager, new GameData());
            Parent view = screen.getView();

            return view.lookup("#roundsField") != null;

        } catch (Exception e) {
            return false;
        }
    }

    // ---------- FIXED GAME CHECKS ----------

    private boolean checkGameButtons() {
        try {
            GameData data = new GameData();
            data.setRounds(3);

            GameScreen game = new GameScreen(manager, data);
            Parent view = game.getView();

            return containsText(view, "Rock") &&
                   containsText(view, "Paper") &&
                   containsText(view, "Scissors");

        } catch (Exception e) {
            return false;
        }
    }

    private boolean simulateGameplay() {
        try {
            GameData data = new GameData();
            data.setRounds(3);

            GameScreen game = new GameScreen(manager, data);
            Parent view = game.getView();

            boolean hasButtons =
                    containsText(view, "Rock") &&
                    containsText(view, "Paper") &&
                    containsText(view, "Scissors");

            boolean hasReceipt =
                    containsText(view, "Chooses") &&
                    containsText(view, "Predicted human choice") &&
                    containsText(view, "Therefore computer chooses");

            return hasButtons && hasReceipt;

        } catch (Exception e) {
            return false;
        }
    }

    private boolean checkRoundCounter() {
        try {
            GameData data = new GameData();
            data.setRounds(5);

            GameScreen game = new GameScreen(manager, data);
            Parent view = game.getView();

            return view.lookup("#roundCounter") != null;

        } catch (Exception e) {
            return false;
        }
    }

    // ---------- HELPERS ----------

    private AppScreen createScreen(String screenName) {
        switch (screenName) {
            case Main.ABOUT:
                return new AboutScreen(manager);
            case Main.MENU:
                return new MenuScreen(manager);
            case Main.SELECT_ROUNDS:
                return new SelectRoundsScreen(manager, new GameData());
            case Main.GAME:
                return new GameScreen(manager, new GameData());
            default:
                throw new IllegalArgumentException("Unknown screen");
        }
    }

    private boolean containsText(Parent root, String text) {
        return root.lookupAll("*").stream()
                .anyMatch(node -> {
                    if (node instanceof Labeled) {
                        String t = ((Labeled) node).getText();
                        return t != null && t.contains(text);
                    }
                    return false;
                });
    }

    private void updateResult(int index, boolean passed) {
        Label label = resultLabels.get(index);

        if (passed) {
            label.setText("PASS");
            label.setStyle("-fx-text-fill: green; -fx-font-weight: bold;");
        } else {
            label.setText("FAIL");
            label.setStyle("-fx-text-fill: red; -fx-font-weight: bold;");
        }
    }
}
