// Represents the human player, tracks name and win count
public class Player {
    private String name;
    private int wins;

    public Player(String name) {
        this.name = name;
        this.wins = 0;
    }

    public String getName() { return name; }
    public int getWins() { return wins; }
    public void addWin() { wins++; }
}