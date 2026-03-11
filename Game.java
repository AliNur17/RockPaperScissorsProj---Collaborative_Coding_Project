import java.util.Scanner;
import java.util.Random;

// Manages all game logic including rounds, shape selection, and scoring
public class Game {
    public enum Shape {
        ROCK, PAPER, SCISSORS
    }

    private Player player;
    private Scanner scanner;
    private Random random;
    private int computerWins;
    private int ties;
    private static final int TOTAL_ROUNDS = 20;

    public Game(String playerName) {
        this.player = new Player(playerName);
        this.scanner = new Scanner(System.in);
        this.random = new Random();
        this.computerWins = 0;
        this.ties = 0;
    }

    public Shape playerChooseShape(int round) {
        System.out.println("Round " + round + " - Choose your shape: 1) Rock  2) Paper  3) Scissors");

        // reads input as a String instead of int to handle all possible invalid inputs without crashing
        String input = scanner.next();
        if (input.equals("2")) return Shape.PAPER;
        else if (input.equals("3")) return Shape.SCISSORS;
        else {
            if (!input.equals("1")) System.out.println("Invalid input, defaulting to Rock.");
            return Shape.ROCK;
        }
    }

    public Shape computerChooseShape() {
        // random.nextInt(3) gives 0-2, +1 shifts to 1-3 to match shape mapping
        int randomNum = random.nextInt(3) + 1;
        if (randomNum == 1) return Shape.ROCK;
        else if (randomNum == 2) return Shape.PAPER;
        else return Shape.SCISSORS;
    }

    public void determineWinner(Shape playerShape, Shape computerShape) {
        System.out.println("You chose: " + playerShape);
        System.out.println("Computer chose: " + computerShape);

        if (playerShape == computerShape) {
            System.out.println("It's a tie!");
            ties++;
        } else if (
            // all three winning combinations for the player
            (playerShape == Shape.ROCK && computerShape == Shape.SCISSORS) ||
            (playerShape == Shape.PAPER && computerShape == Shape.ROCK) ||
            (playerShape == Shape.SCISSORS && computerShape == Shape.PAPER)
        ) {
            System.out.println("You win!");
            player.addWin();
        } else {
            System.out.println("You lose!");
            computerWins++;
        }
    }

    public void printScore() {
        System.out.println(player.getName() + " Wins: " + player.getWins() + 
                           " | Computer Wins: " + computerWins + 
                           " | Ties: " + ties);
    }

    public void play() {
        System.out.println("Welcome to Rock Paper Scissors, " + player.getName() + "!");

        for(int round = 1; round <= TOTAL_ROUNDS; round++) {
            Shape playerShape = playerChooseShape(round);
            Shape computerShape = computerChooseShape();
            determineWinner(playerShape, computerShape);
            printScore();

            System.out.println();
        }

        System.out.println("Game over! Final Score: Human:" + player.getWins() + " Computer:" + computerWins + " Ties:" + ties);
        scanner.close();
    }
}