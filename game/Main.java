package game;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter 1 to play the game.");
        System.out.println("Enter 2 to run all tests.");
        System.out.print("Choice: ");

        String choice = scanner.nextLine().trim();

        if (choice.equals("2")) {
            runAllTests();
        } else {
            runGame(args);
        }
    }

    private static void runGame(String[] args) {
        GameConfig config = new GameConfig();
        config.parse(args);
        Game game = new Game(config);
        game.play();
    }

    private static void runAllTests() {
        TestProgram[] tests = {
                // Add tests as necessary, or remove if not needed
                /*
                new RandomMoveTest(),
                new PlayerFactoryTest(),
                new PlayerBehaviorTest()
                 */
                new RuleEngineTest(),
                new GameConfigFlagTest(),
                new MachineLearningMemoryTest(),
                new RandomMoveDistributionTest()
        };

        System.out.println("\nRunning all tests...\n");

        for (TestProgram test : tests) {
            System.out.println("Starting " + test.getTestName());
            test.runTests();
            System.out.println();
        }
    }
}
