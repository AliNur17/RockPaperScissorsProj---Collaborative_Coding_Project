package game;

// Strategy interface for how a player chooses a move.
// Different move behaviors can be swapped without changing Player or Game.
public interface Move {
    String chooseMove(boolean hiddenMode);
    boolean isHumanControlled();
    void recordMoves(String moveOne, String moveTwo);
}
