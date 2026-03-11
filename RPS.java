import java.util.Scanner;

// Entry point, collects player name and starts the game
public class RPS {
    public static void main(String[] args) {
        @SuppressWarnings("resource")
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter your name: ");
        String name = sc.nextLine();

        Game game = new Game(name);
        game.play();
    }
}