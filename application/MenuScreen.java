package application;

import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class MenuScreen implements AppScreen {

    private final ScreenManager manager;

    public MenuScreen(ScreenManager manager) {
        this.manager = manager;
    }

    @Override
    public Parent getView() {
        Label title = new Label("Menu");
        title.getStyleClass().add("title");

        Button roundsButton = new Button("Start Game");
        Button aboutButton = new Button("About");
        Button exitButton = new Button("Exit");

        roundsButton.setOnAction(e -> manager.show(Main.SELECT_ROUNDS));
        aboutButton.setOnAction(e -> manager.show(Main.ABOUT));
        exitButton.setOnAction(e -> Platform.exit());

        VBox center = new VBox(20, aboutButton, roundsButton, exitButton);
        center.setAlignment(Pos.CENTER);

        BorderPane root = new BorderPane();
        root.setTop(title);
        BorderPane.setAlignment(title, Pos.CENTER);
        root.setCenter(center);

        return root;
    }
}