package player;

import game.Move;

// Represents a computer player -- delegates move generation to Move class.
// If computer strategy changes (e.g. smarter AI), only Move.makeComputerDecision() needs to change.
public class ComputerPlayer extends Player {
    public ComputerPlayer(String name) { super(name); }

    @Override
    public String makeMove() {
        return Move.makeComputerDecision();
    }
}