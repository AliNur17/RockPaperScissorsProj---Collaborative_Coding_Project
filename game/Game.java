package game;

import player.*;

// Manages game flow: round tracking, score keeping, and delegating moves to players.
// Game stays decoupled from specific player implementations and move-generation details.
public class Game {

    private int roundCounter;
    private final int totalNumberRounds;
    private int player1Wins;
    private int player2Wins;
    private int drawGames;
    private final String player1Type;
    private final String player1Name;
    private final String player2Type;
    private final String player2Name;
    private Player p1;
    private Player p2;

    // Default game: Human vs RandomComputer, 20 rounds
    public Game() {
        roundCounter = 1;
        totalNumberRounds = 20;
        player1Type = "Human";
        player1Name = "Human";
        player2Type = "RandomComputer";
        player2Name = "RandomComputer";
        player1Wins = 0;
        player2Wins = 0;
        drawGames = 0;
    }

    public Game(GameConfig config) {
        roundCounter = 1;
        totalNumberRounds = 20;
        player1Type = "Human";
        player1Name = "Human";
        player2Type = config.getCPUType();
        player2Name = config.getCPUName();
        player1Wins = 0;
        player2Wins = 0;
        drawGames = 0;
    }

    private void nextRound() {
        roundCounter++;
    }

    private void updateScore(int winnerVal, String displayName1, String displayName2) {
        switch (winnerVal) {
            case 0:
                drawGames++;
                GameDisplay.showDraw();
                break;
            case 1:
                player1Wins++;
                GameDisplay.showRoundWinner(displayName1);
                break;
            case 2:
                player2Wins++;
                GameDisplay.showRoundWinner(displayName2);
                break;
            default:
                GameDisplay.showInvalidResult();
                break;
        }
    }

    private void showCurrentScore() {
        GameDisplay.showScore(p1.getName(), player1Wins, p2.getName(), player2Wins, drawGames);
    }

    public void play() {
        p1 = PlayerFactory.createPlayer(player1Type, player1Name);
        p2 = PlayerFactory.createPlayer(player2Type, player2Name);

        GameDisplay.showWelcome();

        while (roundCounter <= totalNumberRounds) {
            boolean hiddenRound = p1.shouldHideMoveAgainst(p2);
            String displayName1 = hiddenRound ? "Player 1" : p1.getName();
            String displayName2 = hiddenRound ? "Player 2" : p2.getName();

            GameDisplay.showRoundPrompt(roundCounter);
            String p1Decision = p1.makeMove(hiddenRound);

            if (hiddenRound) {
                GameDisplay.showRoundPrompt(roundCounter);
            }

            String p2Decision = p2.makeMove(hiddenRound);

            if (hiddenRound) {
                GameDisplay.showHiddenRoundChoices(displayName1, p1Decision, displayName2, p2Decision);
            }

            p1.recordMoves(p1Decision, p2Decision);
            p2.recordMoves(p1Decision, p2Decision);

            int winner = RuleEngine.getVictor(p1Decision, p2Decision);
            updateScore(winner, displayName1, displayName2);
            showCurrentScore();
            nextRound();
        }

        GameDisplay.showGameOver();
        showCurrentScore();
    }
}
