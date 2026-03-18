package player;

import game.RandomMove;

// Represents a computer player with a swappable move strategy.
public class ComputerPlayer extends Player {
    public ComputerPlayer(String name) {
        super(name, new RandomMove());
    }
}
