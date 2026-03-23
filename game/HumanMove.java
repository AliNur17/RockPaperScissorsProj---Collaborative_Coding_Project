package game;

import java.util.Scanner;

// Handles human input only.
public class HumanMove implements Move {

    private static final Scanner INPUT = new Scanner(System.in);

    @Override
    public String chooseMove(boolean hiddenMode) {
        String raw = INPUT.next();
        String decision = parseDecision(raw);

        if (decision == null) {
            GameDisplay.showInvalidChoicePrompt();
            return chooseMove(hiddenMode);
        }

        if (hiddenMode) {
            GameDisplay.clearScreenForHiddenInput();
        } else {
            GameDisplay.showHumanChoice(decision);
        }

        return decision;
    }

    @Override
    public boolean isHumanControlled() {
        return true;
    }

    private String parseDecision(String raw) {
        try {
            int numericChoice = Integer.parseInt(raw);
            String decision = RuleEngine.selection(numericChoice);
            return normalizeDecision(decision);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private String normalizeDecision(String decision) {
        if (decision == null) {
            return null;
        }

        for (String move : RuleEngine.getMoves()) {
            if (move.equalsIgnoreCase(decision)) {
                return move;
            }
        }
        return null;
    }

    @Override
    public void recordMoves(String moveOne, String moveTwo) {
        
    }
}
