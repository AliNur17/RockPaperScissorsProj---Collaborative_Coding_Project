package player;

// Factory class for creating Player objects.
// Loose Coupling: Game only calls PlayerFactory.createPlayer() and never directly
// instantiates HumanPlayer or ComputerPlayer -- adding new player types only requires
// updating this class, not Game.
public class PlayerFactory {
    public static Player createPlayer(String type, String name) {
        if (type.equalsIgnoreCase("Human")) {
            return new HumanPlayer(name);
        } else if (type.equalsIgnoreCase("RandomComputer")) {
            return new ComputerPlayer(name);
        }
        // Add new player types here as needed (e.g. SmartComputer, NetworkPlayer)
        else {
            throw new IllegalArgumentException("Unknown player type: " + type);
        }
    }
}