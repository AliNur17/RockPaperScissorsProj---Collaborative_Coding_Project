package game;

import java.util.LinkedHashMap;
import java.util.Map;

// Encapsulates the rules of the game.
public class RuleEngine {

    private static final LinkedHashMap<Integer, String> possibleChoices = new LinkedHashMap<>();

    private static final String[][] rules = {
        {"Rock", "Scissors"},
        {"Scissors", "Paper"},
        {"Paper", "Rock"}
    };

    static {
        possibleChoices.put(1, "Rock");
        possibleChoices.put(2, "Paper");
        possibleChoices.put(3, "Scissors");
    }

    public static String[] getMoves() {
        return possibleChoices.values().toArray(String[]::new);
    }

    public static String availableChoices() {
        if (possibleChoices.isEmpty()) {
            return "()";
        }

        StringBuilder output = new StringBuilder("(");
        for (Map.Entry<Integer, String> entry : possibleChoices.entrySet()) {
            output.append(entry.getKey())
                  .append("=")
                  .append(entry.getValue())
                  .append(", ");
        }
        output.setLength(output.length() - 2);
        output.append(")");
        return output.toString();
    }

    public static String selection(int choice) {
        return possibleChoices.get(choice);
    }

    public static int getVictor(String p1Choice, String p2Choice) {
        if (p1Choice.equalsIgnoreCase(p2Choice)) {
            return 0;
        }

        for (String[] rule : rules) {
            String winnerMove = rule[0];
            String loserMove = rule[1];

            if (p1Choice.equalsIgnoreCase(winnerMove) && p2Choice.equalsIgnoreCase(loserMove)) {
                return 1;
            }
            if (p2Choice.equalsIgnoreCase(winnerMove) && p1Choice.equalsIgnoreCase(loserMove)) {
                return 2;
            }
        }

        return -1;
    }

    public static String getVictor(String choice) throws Exception {
        for (String[] currentRule : rules) {
            if (choice.equalsIgnoreCase(currentRule[1])) {
                return currentRule[0];
            }
        }
        throw new Exception("Choice made by ML doesnt match rules");
    }
}
