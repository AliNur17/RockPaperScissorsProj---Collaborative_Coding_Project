package game;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class MemoryManagerJUnitTest {

    @Test
    void chooseMoveReturnsNonNullArray() {
        MemoryManager mm = new MemoryManager();
        String[] moves = mm.chooseMove();
        assertNotNull(moves);
    }

    @Test
    void chooseMoveReturnsValidMoves() {
        MemoryManager mm = new MemoryManager();
        Set<String> valid = new HashSet<>(Arrays.asList("Rock", "Paper", "Scissors"));
        String[] moves = mm.chooseMove();
        assertTrue(moves.length > 0);
        for (String move : moves) {
            assertTrue(valid.contains(move), "Move should be Rock, Paper, or Scissors");
        }
    }

    @Test
    void recordingMovesDoesNotThrow() {
        MemoryManager mm = new MemoryManager();
        assertDoesNotThrow(() -> {
            mm.recordOpponentMove("Rock");
            mm.chosenMove("Paper");
        });
    }

    @Test
    void predictionIsNullWithNoHistory() {
        MemoryManager mm = new MemoryManager();
        // Fresh manager with empty pastMoves has no prediction
        String prediction = mm.getPredictedHumanMove();
        assertNull(prediction);
    }
}
