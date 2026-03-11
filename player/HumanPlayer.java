package player;
import game.*;

public class HumanPlayer extends Player
{
    public HumanPlayer(String name)
    {
        super(name);
    }

    public String makeMove()
    {
        return Move.makeHumanDecision(hiddenStatus);
    }
}