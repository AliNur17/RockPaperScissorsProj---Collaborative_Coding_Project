package game;

import java.util.Random;

// Handles machine learning computer move selection only.

// CURRENTLY NOT YET UPDATED
public class MachineLearningMove implements Move {

    private final Random random;
    private final MemoryManager mm;

    public MachineLearningMove() {
        this(new Random());
    }

    public MachineLearningMove(Random random) {
        mm = new MemoryManager();
        this.random = random;
    }

    @Override
    public String chooseMove(boolean hiddenMode) {
        String[] moves = mm.chooseMove();
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

    @Override
    public void recordMoves(String moveOne, String moveTwo) {
        mm.recordOpponentMove(moveOne);
        mm.chosenMove(moveTwo);
    }
}
