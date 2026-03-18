package game;

// Centralizes all console output for the game.
public final class GameDisplay {

    private GameDisplay() {
    }

    public static void showWelcome() {
        System.out.println("Welcome to Rock-Paper-Scissors!\n");
    }

    public static void showRoundPrompt(int roundCounter) {
        System.out.print("Round " + roundCounter + " - Choose " + RuleEngine.availableChoices() + ": ");
    }

    public static void showHumanChoice(String decision) {
        System.out.print("You chose " + decision + ". ");
    }

    public static void showComputerChoice(String decision) {
        System.out.print("The computer chose " + decision + ". ");
    }

    public static void showHiddenRoundChoices(String displayName1, String p1Decision,
                                              String displayName2, String p2Decision) {
        System.out.print(displayName1 + " chose: " + p1Decision + ". "
                + displayName2 + " chose: " + p2Decision + ". ");
    }

    public static void showDraw() {
        System.out.println("Draw!");
    }

    public static void showRoundWinner(String winnerName) {
        System.out.println(winnerName + " wins!");
    }

    public static void showInvalidResult() {
        System.out.println("Invalid result.");
    }

    public static void showScore(String player1Name, int player1Wins,
                                 String player2Name, int player2Wins,
                                 int drawGames) {
        System.out.println("Score: " + player1Name + ":" + player1Wins
                + " " + player2Name + ":" + player2Wins
                + " Draws=" + drawGames + "\n");
    }

    public static void showGameOver() {
        System.out.println("Game Over. The final scores are as follows...");
    }

    public static void showInvalidChoicePrompt() {
        System.out.print("Invalid choice. Choose from the following: 1, 2, or 3 >> ");
    }

    public static void clearScreenForHiddenInput() {
        for (int i = 0; i < 60; i++) {
            System.out.println();
        }
    }
}
