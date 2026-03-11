package game;

import java.util.Random;
import java.util.Scanner;

// Encapsulates HOW a move is made -- if input method changes (e.g. GUI, network),
// only this class needs to change
public class Move {

    // Single shared Scanner to avoid resource leak from creating new Scanner per call
    private static final Scanner input = new Scanner(System.in);

    // Encapsulates human input logic
    // reads input as String to handle all invalid inputs without crashing
    public static String makeHumanDecision(boolean checkHide) {
        String raw = input.next();
        String decision = null;

        // Try to parse as integer selection
        try {
            int numSelect = Integer.parseInt(raw);
            decision = RuleEngine.selection(numSelect);
        } catch (NumberFormatException e) {
            decision = null; // non-numeric input, caught as invalid below
        }

        // Validate decision against known moves
        boolean valid = false;
        if (decision != null) {
            for (String move : RuleEngine.getMoves()) {
                if (move.equalsIgnoreCase(decision)) {
                    decision = move; // normalize capitalization
                    valid = true;
                    break;
                }
            }
        }

        if (!valid) {
            System.out.print("Invalid choice. Choose from the following: 1, 2, or 3 >> ");
            return makeHumanDecision(checkHide); // retry on invalid input
        }

        if (checkHide) {
            // Flush screen so Player 2 cannot see Player 1's choice
            for (int i = 0; i < 60; i++)
                System.out.println();
        } else {
            System.out.print("You chose " + decision + ". ");
        }

        return decision;
    }

    // Encapsulates computer decision logic -- if computer strategy changes, only this method changes
    public static String makeComputerDecision() {
        Random rand = new Random();
        String[] moves = RuleEngine.getMoves();
        String decision = moves[rand.nextInt(moves.length)];
        System.out.print("The computer chose " + decision + ". ");
        return decision;
    }
}