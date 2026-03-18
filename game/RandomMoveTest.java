package game;

public class RandomMoveTest implements TestProgram {

    private int passed = 0;
    private int failed = 0;

    @Override
    public String getTestName() {
        return "RandomMoveTest";
    }

    @Override
    public void runTests() {
        testRandomMoveIsValid();
        testRandomMoveRepeatedly();
        printSummary();
    }

    private void testRandomMoveIsValid() {
        RandomMove moveStrategy = new RandomMove();
        String move = moveStrategy.makeMove(false);
        assertTrue(isValidMove(move), "Single random move should be valid");
    }

    private void testRandomMoveRepeatedly() {
        RandomMove moveStrategy = new RandomMove();

        for (int i = 0; i < 100; i++) {
            String move = moveStrategy.makeMove(false);
            assertTrue(isValidMove(move), "Random move #" + (i + 1) + " should be valid");
        }
    }

    private boolean isValidMove(String move) {
        return move.equalsIgnoreCase("Rock")
                || move.equalsIgnoreCase("Paper")
                || move.equalsIgnoreCase("Scissors");
    }

    private void assertTrue(boolean condition, String msg) {
        if (condition) {
            passed++;
            System.out.println("PASS: " + msg);
        } else {
            failed++;
            System.out.println("FAIL: " + msg);
        }
    }

    private void printSummary() {
        System.out.println("\n=== " + getTestName() + " Summary ===");
        System.out.println("Passed: " + passed);
        System.out.println("Failed: " + failed);
    }
}