package player;

import game.TestProgram;

public class PlayerFactoryTest implements TestProgram {

    private int passed = 0;
    private int failed = 0;

    @Override
    public String getTestName() {
        return "PlayerFactoryTest";
    }

    @Override
    public void runTests() {
        testCreateHumanPlayer();
        testCreateComputerPlayer();
        testUnknownTypeThrowsException();
        printSummary();
    }

    private void testCreateHumanPlayer() {
        Player p = PlayerFactory.createPlayer("Human", "Ryan");
        assertTrue(p instanceof HumanPlayer, "Factory should create HumanPlayer");
        assertEquals("Ryan", p.getName(), "HumanPlayer name should be correct");
    }

    private void testCreateComputerPlayer() {
        Player p = PlayerFactory.createPlayer("RandomComputer", "CPU");
        assertTrue(p instanceof ComputerPlayer, "Factory should create ComputerPlayer");
        assertEquals("CPU", p.getName(), "ComputerPlayer name should be correct");
    }

    private void testUnknownTypeThrowsException() {
        try {
            PlayerFactory.createPlayer("Alien", "X");
            failed++;
            System.out.println("FAIL: Unknown type should throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            passed++;
            System.out.println("PASS: Unknown type throws IllegalArgumentException");
        } catch (Exception e) {
            failed++;
            System.out.println("FAIL: Wrong exception type thrown");
        }
    }

    private void assertEquals(Object expected, Object actual, String msg) {
        if ((expected == null && actual == null) ||
                (expected != null && expected.equals(actual))) {
            passed++;
            System.out.println("PASS: " + msg);
        } else {
            failed++;
            System.out.println("FAIL: " + msg);
            System.out.println("  Expected: " + expected);
            System.out.println("  Actual:   " + actual);
        }
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