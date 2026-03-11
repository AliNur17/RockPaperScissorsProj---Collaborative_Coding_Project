package game;

import java.util.Random;
import java.util.Scanner;

public class Move {

    static String[] moves = {"Rock", "Paper", "Scissors"};

    public static String makeHumanDecision(boolean checkHide)
    {
        Scanner input = new Scanner(System.in);

        int numSelect = input.nextInt();
        String decision = RuleEngine.selection(numSelect);

        boolean valid = false;

        for (String move : moves)
        {
            if (move.equalsIgnoreCase(decision))
            {
                decision = move; //capitalization
                valid = true;
                break;
            }
        }

        if (!valid)
        {
            System.out.print("Invalid choice. Choose from the following: 1, 2, or 3 >> ");
            return makeHumanDecision(checkHide);
        }

        if (checkHide) //When there are two human players, and we want to hide their choices from one another
        {
            for (int i = 0; i < 60; i++)
                System.out.println(); //Artificially "flush" the screen and hide the previous response
        }
        else
            System.out.print("You chose " + decision+". ");

        return decision;
    }
    public static String makeComputerDecision()
    {
        Random rand = new Random();

        String decision = moves[rand.nextInt(moves.length)];

        System.out.print("The computer chose " + decision + ". ");

        return decision;
    }
}