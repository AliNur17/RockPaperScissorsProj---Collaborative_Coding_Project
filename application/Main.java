package application;

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    public static final String MENU = "menu";
    public static final String ABOUT = "about";
    public static final String SELECT_ROUNDS = "selectRounds";
    public static final String GAME = "game";
    public static final String TEST_CONTENTS = "testContents";

    @Override
    public void start(Stage stage) {
        ScreenManager manager = new ScreenManager(stage);

        stage.setTitle("Rock Paper Scissors");
        manager.show(MENU);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}