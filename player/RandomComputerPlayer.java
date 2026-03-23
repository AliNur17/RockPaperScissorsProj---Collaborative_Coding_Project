package player;

import game.RandomMove;

// Represents a computer player with a swappable move strategy.
public class RandomComputerPlayer extends Player {
    public RandomComputerPlayer(String name) {
        super(name, new RandomMove());
    }
}
