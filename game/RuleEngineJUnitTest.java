package game;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class RuleEngineJUnitTest {

    @Test
    void rockBeatsScissors() {
        assertEquals(1, RuleEngine.getVictor("Rock", "Scissors"));
    }

    @Test
    void paperBeatsRock() {
        assertEquals(1, RuleEngine.getVictor("Paper", "Rock"));
    }

    @Test
    void scissorsBeatsPaper() {
        assertEquals(1, RuleEngine.getVictor("Scissors", "Paper"));
    }

    @Test
    void rockVsRockIsDraw() {
        assertEquals(0, RuleEngine.getVictor("Rock", "Rock"));
    }

    @Test
    void paperVsPaperIsDraw() {
        assertEquals(0, RuleEngine.getVictor("Paper", "Paper"));
    }

    @Test
    void scissorsVsScissorsIsDraw() {
        assertEquals(0, RuleEngine.getVictor("Scissors", "Scissors"));
    }

    @Test
    void computerWinsWhenScissorsBeatsPaper() {
        assertEquals(2, RuleEngine.getVictor("Paper", "Scissors"));
    }

    @Test
    void invalidMovereturnsNegativeOne() {
        assertEquals(-1, RuleEngine.getVictor("Lizard", "Rock"));
    }
}
