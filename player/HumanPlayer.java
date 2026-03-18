package player;

import game.HumanMove;

// Represents a human player with a dedicated human-input strategy.
public class HumanPlayer extends Player {
    public HumanPlayer(String name) {
        super(name, new HumanMove());
    }
}
