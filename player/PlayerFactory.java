package player;

public class PlayerFactory{
    public static Player createPlayer(String type, String name) {
        if (type.equalsIgnoreCase("Human")) {
            return new HumanPlayer(name);
        }
        else if (type.equalsIgnoreCase("AI")) {
            return new ComputerPlayer(name);
        }
        //Add other player types as necessary...
        else {
            throw new IllegalArgumentException("Unknown player type: " + type);
        }
    }
}