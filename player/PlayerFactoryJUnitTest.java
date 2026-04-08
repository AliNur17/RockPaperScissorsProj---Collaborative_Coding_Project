package player;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PlayerFactoryJUnitTest {

    @Test
    void createsHumanPlayer() {
        Player p = PlayerFactory.createPlayer("Human", "Alice");
        assertNotNull(p);
        assertEquals("Alice", p.getName());
        assertTrue(p.isHumanControlled());
    }

    @Test
    void createsRandomComputerPlayer() {
        Player p = PlayerFactory.createPlayer("RandomComputer", "Bot");
        assertNotNull(p);
        assertEquals("Bot", p.getName());
        assertFalse(p.isHumanControlled());
    }

    @Test
    void createsMachineLearningPlayer() {
        Player p = PlayerFactory.createPlayer("MachineLearningComputer", "ML Bot");
        assertNotNull(p);
        assertEquals("ML Bot", p.getName());
        assertFalse(p.isHumanControlled());
    }

    @Test
    void typeIsCaseInsensitive() {
        Player p = PlayerFactory.createPlayer("human", "Alice");
        assertNotNull(p);
        assertTrue(p.isHumanControlled());
    }

    @Test
    void unknownTypeThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            PlayerFactory.createPlayer("UnknownType", "Nobody");
        });
    }
}
