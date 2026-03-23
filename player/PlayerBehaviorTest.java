package player;

import game.TestProgram;

public class PlayerBehaviorTest implements TestProgram {

    private int passed = 0;
    private int failed = 0;

    @Override
    public String getTestName() {
        return "PlayerBehaviorTest";
    }

    @Override
    public void runTests() {
        testGetName();
        testHumanVsHumanHidesMoves();
        testHumanVsComputerDoesNotHideMoves();
        testComputerVsHumanDoesNotHideMoves();
        printSummary();
    }

    private void testGetName() {
        HumanPlayer p = new HumanPlayer("Ryan");
        assertEquals("Ryan", p.getName(), "getName should return the correct name");
    }

    private void testHumanVsHumanHidesMoves() {
        HumanPlayer p1 = new HumanPlayer("A");
        HumanPlayer p2 = new HumanPlayer("B");
        assertTrue(p1.shouldHideMoveAgainst(p2), "Human vs Human should hide moves");
    }

    private void testHumanVsComputerDoesNotHideMoves() {
        HumanPlayer p1 = new HumanPlayer("A");
        RandomComputerPlayer p2 = new RandomComputerPlayer("CPU");
        assertFalse(p1.shouldHideMoveAgainst(p2), "Human vs Computer should not hide moves");
    }

    private void testComputerVsHumanDoesNotHideMoves() {
        RandomComputerPlayer p1 = new RandomComputerPlayer("CPU");
        HumanPlayer p2 = new HumanPlayer("A");
        assertFalse(p1.shouldHideMoveAgainst(p2), "Computer vs Human should not hide moves");
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

    private void assertFalse(boolean condition, String msg) {
        if (!condition) {
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