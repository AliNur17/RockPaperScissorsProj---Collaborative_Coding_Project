package player;

import game.Move;

// Abstract base class for all player types.
// Game interacts with players through this abstraction rather than concrete subclasses.
public abstract class Player {
    private final String name;
    private final Move moveBehavior;

    protected Player(String name, Move moveBehavior) {
        this.name = name;
        this.moveBehavior = moveBehavior;
    }

    public String getName() {
        return name;
    }

    public String makeMove(boolean hiddenMode) {
        return moveBehavior.chooseMove(hiddenMode);
    }

    public boolean isHumanControlled() {
        return moveBehavior.isHumanControlled();
    }

    public boolean shouldHideMoveAgainst(Player opponent) {
        return isHumanControlled() && opponent.isHumanControlled();
    }

    public void recordMoves(String moveOne, String moveTwo) {
        moveBehavior.recordMoves(moveOne, moveTwo);
    }
}
