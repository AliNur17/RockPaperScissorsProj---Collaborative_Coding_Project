package player;

import game.MachineLearningMove;

// Represents a computer player with a swappable move strategy.
public class MachineLearningComputerPlayer extends Player {
    public MachineLearningComputerPlayer(String name) {
        super(name, new MachineLearningMove());
    }
}
