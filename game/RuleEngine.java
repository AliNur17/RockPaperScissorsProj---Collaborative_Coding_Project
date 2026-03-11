package game;

import java.util.LinkedHashMap;

// Encapsulates the RULES of the game -- if rules change (e.g. Rock Paper Scissors Lizard Spock),
// only this class needs to change. No other class needs to know how winning is determined.
public class RuleEngine {

    // Maps numeric input to shape name -- add new shapes here as needed
    private static final LinkedHashMap<Integer, String> possibleChoices = new LinkedHashMap<>();

    // Defines which shape beats which -- extensible for future rule changes
    private static final String[][] rules = {
        {"Rock",     "Scissors"}, // Rock beats Scissors
        {"Scissors", "Paper"},    // Scissors beats Paper
        {"Paper",    "Rock"}      // Paper beats Rock
    };

    static {
        possibleChoices.put(1, "Rock");
        possibleChoices.put(2, "Paper");
        possibleChoices.put(3, "Scissors");
        // Add new choices here if rules expand (e.g. Lizard, Spock)
    }

    // Returns valid move names as an array -- used by Move class for validation
    public static String[] getMoves() {
        return possibleChoices.values().toArray(new String[0]);
    }

    // Builds the prompt string showing available choices e.g. (1=Rock, 2=Paper, 3=Scissors)
    public static String availableChoices() {
        if (possibleChoices.isEmpty()) return "()";
        String output = "(";
        for (Integer key : possibleChoices.keySet())
            output += key + "=" + possibleChoices.get(key) + ", ";
        output = output.substring(0, output.length() - 2); // remove trailing comma
        output += ")";
        return output;
    }

    // Maps numeric input to shape name e.g. 1 -> "Rock"
    public static String selection(int choice) {
        return possibleChoices.get(choice);
    }

    // Determines the winner: 0 = draw, 1 = p1 wins, 2 = p2 wins, -1 = invalid
    public static int getVictor(String p1Choice, String p2Choice) {
        if (p1Choice.equalsIgnoreCase(p2Choice)) return 0; // draw

        for (String[] rule : rules) {
            String winnerMove = rule[0]; // shape that wins
            String loserMove  = rule[1]; // shape that loses
            if (p1Choice.equalsIgnoreCase(winnerMove) && p2Choice.equalsIgnoreCase(loserMove)) return 1;
            if (p2Choice.equalsIgnoreCase(winnerMove) && p1Choice.equalsIgnoreCase(loserMove)) return 2;
        }

        return -1; // invalid input
    }
}