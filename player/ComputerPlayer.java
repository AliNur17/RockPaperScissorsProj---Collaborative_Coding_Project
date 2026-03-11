package player;
import game.*;

public class ComputerPlayer extends Player
{
    public ComputerPlayer(String name)
    {
        super(name);
    }
    public String makeMove()
    {
        return Move.makeComputerDecision();
    }
}