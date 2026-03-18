package player;

// Factory class for creating Player objects.
public class PlayerFactory {
    public static Player createPlayer(String type, String name) {
        if (type.equalsIgnoreCase("Human")) {
            return new HumanPlayer(name);
        } else if (type.equalsIgnoreCase("RandomComputer")) {
            return new ComputerPlayer(name);
        } else {
            throw new IllegalArgumentException("Unknown player type: " + type);
        }
    }
}
