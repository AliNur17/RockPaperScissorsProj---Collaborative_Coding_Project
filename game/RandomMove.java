package game;

import java.util.Random;

// Handles random computer move selection only.
public class RandomMove implements Move {

    private final Random random;

    public RandomMove() {
        this(new Random());
    }

    public RandomMove(Random random) {
        this.random = random;
    }

    @Override
    public String chooseMove(boolean hiddenMode) {
        String[] moves = RuleEngine.getMoves();
        String decision = moves[random.nextInt(moves.length)];
        GameDisplay.showComputerChoice(decision);
        return decision;
    }

    @Override
    public boolean isHumanControlled() {
        return false;
    }

    public String makeMove(boolean hiddenMode) {
        return chooseMove(hiddenMode);
    }
}
