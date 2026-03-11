package game;

import java.util.LinkedHashMap;
import java.util.Map;

public class RuleEngine
{
    private static final LinkedHashMap<Integer, String> possibleChoices = new LinkedHashMap<>();
    static String[][] rules = {
            {"rock", "scissors"}, //rock beats scissors
            {"scissors", "paper"}, //scissors beats paper
            {"paper", "rock"} //paper beats rock
    };

    static
    {
        possibleChoices.put(1, "Rock");
        possibleChoices.put(2, "Paper");
        possibleChoices.put(3, "Scissors");
        //Add choices as need be...
    }

    public static String availableChoices()
    {
        if (possibleChoices.isEmpty())
        {
            return "()";
        }
        String output = "(";
        for (Integer key : possibleChoices.keySet())
        {
            output += key + "=" + possibleChoices.get(key) + ", ";
        }
        output = output.substring(0, output.length() - 2); //Remove trailing comma
        output += ")";
        return output; //Currently returns >> (1=Rock, 2=Paper, 3=Scissors)
    }

    public static String selection(int choice)
    {
        return possibleChoices.get(choice); //If user inputs 1, returns Rock, and so on...
    }

    public static int getVictor(String p1Choice, String p2Choice) {
        if (p1Choice.equalsIgnoreCase(p2Choice)) {
            return 0; //draw
        }

        for (String[] rule : rules) {
            String winnerMove = rule[0]; //What beats the other choice (left column of rules array)
            String loserMove = rule[1]; //What gets beaten by the other choice (right column of rules array)

            if (p1Choice.equalsIgnoreCase(winnerMove) && p2Choice.equalsIgnoreCase(loserMove)) {
                return 1; //player 1 wins
            }
            if (p2Choice.equalsIgnoreCase(winnerMove) && p1Choice.equalsIgnoreCase(loserMove)) {
                return 2; //player 2 wins
            }
        }

        return -1; // invalid input
    }
}