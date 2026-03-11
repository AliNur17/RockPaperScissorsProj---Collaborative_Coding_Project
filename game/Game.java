package game;

import player.*;

// Manages game flow: round tracking, score keeping, and delegating moves to players.
// Separates game logic from player behavior and rule evaluation.
public class Game {

    private int roundCounter;
    private final int totalNumberRounds;
    private int player1Wins, player2Wins, drawGames;
    private final String player1Type, player1Name;
    private final String player2Type, player2Name;
    Player p1, p2;

    // Default game: Human vs RandomComputer, 20 rounds
    public Game() {
        roundCounter = 1;
        totalNumberRounds = 20;
        player1Type = "Human";
        player1Name = "Human";
        player2Type = "RandomComputer";
        player2Name = "Computer";
        player1Wins = 0;
        player2Wins = 0;
        drawGames = 0;
    }

    // Advances to the next round
    private void nextRound() {
        roundCounter++;
    }

    // Updates score and announces result -- delegates winner determination to RuleEngine
    private void updateScore(int winnerVal, String p1Name, String p2Name) {
        switch (winnerVal) {
            case 0:
                drawGames++;
                System.out.println("Draw!");
                break;
            case 1:
                player1Wins++;
                System.out.println(p1Name + " wins!");
                break;
            case 2:
                player2Wins++;
                System.out.println(p2Name + " wins!");
                break;
            default:
                System.out.println("Invalid result.");
                break;
        }
    }

    // Prints current score in the required format
    private void showCurrentScore() {
        System.out.println("Score: " + p1.getName() + ":" + player1Wins +
                           " " + p2.getName() + ":" + player2Wins +
                           " Draws=" + drawGames + "\n");
    }

    // Main game loop -- runs for totalNumberRounds rounds
    public void play() {
        // PlayerFactory creates the correct player type -- adding new player types only requires updating PlayerFactory
        p1 = PlayerFactory.createPlayer(player1Type, player1Name);
        p2 = PlayerFactory.createPlayer(player2Type, player2Name);

        System.out.println("Welcome to Rock-Paper-Scissors!\n");

        String displayName1 = p1.getName();
        String displayName2 = p2.getName();

        while (roundCounter <= totalNumberRounds) {
            System.out.print("Round " + roundCounter + " - Choose " + RuleEngine.availableChoices() + ": ");

            String p1Decision, p2Decision;

            if (player1Type.equals(player2Type)) {
                // Two players of same type (e.g. Human vs Human): hide choices from each other
                p1.updateHidden(true);
                p1Decision = p1.makeMove();
                System.out.print("Round " + roundCounter + " - Choose " + RuleEngine.availableChoices() + ": ");
                p2Decision = p2.makeMove();
                displayName1 = "Player 1";
                displayName2 = "Player 2";
                System.out.print(displayName1 + " chose: " + p1Decision + ". " +
                                 displayName2 + " chose: " + p2Decision + ". ");
            } else {
                // Different player types (e.g. Human vs Computer): no hiding needed
                p1Decision = p1.makeMove();
                p2Decision = p2.makeMove();
            }

            // Delegate winner calculation to RuleEngine -- Game doesn't need to know the rules
            int winner = RuleEngine.getVictor(p1Decision, p2Decision);
            updateScore(winner, displayName1, displayName2);
            showCurrentScore();
            nextRound();
        }

        System.out.println("Game Over. The final scores are as follows...");
        showCurrentScore();
    }
}