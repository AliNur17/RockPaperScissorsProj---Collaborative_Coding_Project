package player;

import game.Move;

// Represents a human player -- delegates move input to Move class
public class HumanPlayer extends Player {
    public HumanPlayer(String name) { super(name); }

    @Override
    public String makeMove() {
        return Move.makeHumanDecision(hiddenStatus);
    }
}