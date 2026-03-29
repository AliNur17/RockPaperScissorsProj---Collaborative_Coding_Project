package application;

import java.util.HashMap;
import java.util.Map;

import javafx.scene.Scene;
import javafx.stage.Stage;

public class ScreenManager {

    private final Stage stage;
    private final Map<String, AppScreen> screens = new HashMap<>();
    private final GameData gameData;

    public ScreenManager(Stage stage) {
        this.stage = stage;
        this.gameData = new GameData();

        registerScreens();
    }

    private void registerScreens() {
        screens.put(Main.MENU, new MenuScreen(this));
        screens.put(Main.ABOUT, new AboutScreen(this));
        screens.put(Main.SELECT_ROUNDS, new SelectRoundsScreen(this, gameData));
        screens.put(Main.GAME, new GameScreen(this, gameData));
    }

    public void show(String screenName) {
        AppScreen screen = screens.get(screenName);

        if (screen == null) {
            throw new IllegalArgumentException("No screen registered for: " + screenName);
        }

        Scene scene = new Scene(screen.getView(), 600, 400);
        scene.getStylesheets().add(
        	    ScreenManager.class.getResource("application.css").toExternalForm()
        	);

        stage.setScene(scene);
    }
}