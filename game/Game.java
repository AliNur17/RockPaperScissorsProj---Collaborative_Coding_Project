package game;
import player.*;

public class Game
{
    private int roundCounter;
    private final int totalNumberRounds;
    private int player1Wins, player2Wins, drawGames = 0;
    private boolean checkSamePlayerType;

    private String player1Type, player1Name;
    private String player2Type, player2Name;
    Player p1, p2;


    public Game()
    {
        //Default game initialization
        //20 rounds, played by a Human and a randomly-deciding Computer
        roundCounter = 1;
        totalNumberRounds = 20;

        player1Type = "Human";
        player1Name = "Human";
        
        player2Type = "RandomComputer";
        player2Name = "Computer";
    }

    private void nextRound()
    {
        roundCounter++;
    }

    private void updateScore(int winnerVal, String p1Name, String p2Name)
    {
        switch(winnerVal)
        {
            case(0):
                drawGames++;
                System.out.println("Draw!");
                break;
            case(1):
                player1Wins++;
                System.out.println(p1Name + " wins!");
                break;
            case(2):
                player2Wins++;
                System.out.println(p2Name+" wins!");
                break;
            default:
                break;
        }
    }

    private void showCurrentScore()
    {
        System.out.println("Score: " + p1.getName() + ":" + player1Wins + " " + p2.getName() + ":" + player2Wins + " Draws=" + drawGames + "\n");
    }

    private void checkHidden()
    {
        checkSamePlayerType = player1Type.equals(player2Type);
    }

    public void play()
    {
        p1 = PlayerFactory.createPlayer(player1Type, player1Name);
        p2 = PlayerFactory.createPlayer(player2Type, player2Name);

        System.out.println("\n");
        String player1Name = p1.getName();
        String player2Name = p2.getName();

        while (roundCounter <= totalNumberRounds)
        {
            System.out.print("Round " + roundCounter + " - Choose " + RuleEngine.availableChoices() + ": ");
            String p1Decision, p2Decision;
            if (checkSamePlayerType) //In case of two human or non-human players
            {
                p1.updateHidden(checkSamePlayerType);
                p1Decision = p1.makeMove();
                p2Decision = p2.makeMove();
                //To differentiate the two players without repeating a descriptor like "Human" or "You" as an example
                player1Name = "Player 1";
                player2Name = "Player 2";
                System.out.print(player1Name + " chose: " + p1Decision + ". " + player2Name + " chose: " + p2Decision);
            }
            else //Different player types
            {
                p1Decision = p1.makeMove();
                p2Decision = p2.makeMove();
            }
            int winner = RuleEngine.getVictor(p1Decision, p2Decision);
            updateScore(winner, player1Name, player2Name);
            showCurrentScore();
            nextRound();
        }
        System.out.println("\nGame Over. The final scores are as follows...");
        showCurrentScore();
    }
}
