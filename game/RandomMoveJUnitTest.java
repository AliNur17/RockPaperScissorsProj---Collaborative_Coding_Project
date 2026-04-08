package game;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class RandomMoveJUnitTest {

    @Test
    void moveIsNotNull() {
        RandomMove rm = new RandomMove();
        String move = rm.makeMove(false);
        assertNotNull(move);
    }

    @Test
    void moveIsValidChoice() {
        RandomMove rm = new RandomMove();
        Set<String> valid = new HashSet<>(Arrays.asList("Rock", "Paper", "Scissors"));
        String move = rm.makeMove(false);
        assertTrue(valid.contains(move));
    }

    @Test
    void isNotHumanControlled() {
        RandomMove rm = new RandomMove();
        assertFalse(rm.isHumanControlled());
    }

    @Test
    void multipleMovesAreAllValid() {
        RandomMove rm = new RandomMove();
        Set<String> valid = new HashSet<>(Arrays.asList("Rock", "Paper", "Scissors"));
        for (int i = 0; i < 20; i++) {
            assertTrue(valid.contains(rm.makeMove(false)));
        }
    }
}
