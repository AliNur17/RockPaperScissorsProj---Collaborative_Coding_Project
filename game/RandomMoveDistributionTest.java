package game;

import java.util.Random;

public class RandomMoveDistributionTest implements TestProgram {

    private int passed = 0;
    private int failed = 0;

    @Override
    public String getTestName() {
        return "RandomMoveDistributionTest";
    }

    @Override
    public void runTests() {
        testAllThreeMovesAppearInLargeSample();
        printSummary();
    }

    private void testAllThreeMovesAppearInLargeSample() {
        RandomMove move = new RandomMove(new Random(0));

        int rock = 0;
        int paper = 0;
        int scissors = 0;
        int rounds = 300;

        for (int i = 0; i < rounds; i++) {
            String choice = move.makeMove(false);
            if ("Rock".equalsIgnoreCase(choice)) {
                rock++;
            } else if ("Paper".equalsIgnoreCase(choice)) {
                paper++;
            } else if ("Scissors".equalsIgnoreCase(choice)) {
                scissors++;
            }
        }

        boolean allPresent = rock > 0 && paper > 0 && scissors > 0;
        boolean roughlyBalanced = rock >= 60 && paper >= 60 && scissors >= 60;

        assertTrue(allPresent, "Random move sample should include Rock, Paper, and Scissors");
        assertTrue(roughlyBalanced,
                "Random move sample should be roughly balanced across 300 rounds"
                        + " [Rock=" + rock + ", Paper=" + paper + ", Scissors=" + scissors + "]");
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