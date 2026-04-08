package application;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class GameDataJUnitTest {

    @Test
    void defaultRoundsIsZero() {
        GameData gd = new GameData();
        assertEquals(0, gd.getRounds());
    }

    @Test
    void setAndGetRounds() {
        GameData gd = new GameData();
        gd.setRounds(10);
        assertEquals(10, gd.getRounds());
    }

    @Test
    void setRoundsToOne() {
        GameData gd = new GameData();
        gd.setRounds(1);
        assertEquals(1, gd.getRounds());
    }

    @Test
    void overwriteRounds() {
        GameData gd = new GameData();
        gd.setRounds(5);
        gd.setRounds(20);
        assertEquals(20, gd.getRounds());
    }
}
