package application;

import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class AboutScreen implements AppScreen {

    private final ScreenManager manager;

    public AboutScreen(ScreenManager manager) {
        this.manager = manager;
    }

    @Override
    public Parent getView() {
        Label title = new Label("About");
        title.getStyleClass().add("title");

        Label description = new Label(
            "Rock Paper Scissors (UI Edition)\n\n" +
            "Made possible by...\n" +
            "- Justin Lai\n" + 
            "- Tuan Nguyen\n" +
            "- Alisher Nurmatov\n" +
            "- Hruday Prathipati"
        );

        description.setWrapText(true);
        description.setMaxWidth(300);
        description.setAlignment(Pos.CENTER);

        Button backButton = new Button("Back to Menu");
        backButton.setOnAction(e -> manager.show(Main.MENU));

        VBox center = new VBox(25, description, backButton);
        center.setAlignment(Pos.CENTER);

        BorderPane root = new BorderPane();
        root.setTop(title);
        BorderPane.setAlignment(title, Pos.CENTER);
        root.setCenter(center);

        return root;
    }
}