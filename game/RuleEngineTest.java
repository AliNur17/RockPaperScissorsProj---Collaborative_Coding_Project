package game;

public class RuleEngineTest implements TestProgram {

    private int passed = 0;
    private int failed = 0;

    @Override
    public String getTestName() {
        return "RuleEngineTest";
    }

    @Override
    public void runTests() {
        testSelectionMapping();
        testAvailableChoices();
        testDrawCases();
        testWinningCases();
        testInvalidCases();
        printSummary();
    }

    private void testSelectionMapping() {
        assertEquals("Rock", RuleEngine.selection(1), "selection(1) should be Rock");
        assertEquals("Paper", RuleEngine.selection(2), "selection(2) should be Paper");
        assertEquals("Scissors", RuleEngine.selection(3), "selection(3) should be Scissors");
    }

    private void testAvailableChoices() {
        String choices = RuleEngine.availableChoices();
        assertTrue(choices.contains("1=Rock"), "availableChoices should contain Rock");
        assertTrue(choices.contains("2=Paper"), "availableChoices should contain Paper");
        assertTrue(choices.contains("3=Scissors"), "availableChoices should contain Scissors");
    }

    private void testDrawCases() {
        assertEquals(0, RuleEngine.getVictor("Rock", "Rock"), "Rock vs Rock should be draw");
        assertEquals(0, RuleEngine.getVictor("Paper", "Paper"), "Paper vs Paper should be draw");
        assertEquals(0, RuleEngine.getVictor("Scissors", "Scissors"), "Scissors vs Scissors should be draw");
    }

    private void testWinningCases() {
        assertEquals(1, RuleEngine.getVictor("Rock", "Scissors"), "Rock should beat Scissors");
        assertEquals(1, RuleEngine.getVictor("Paper", "Rock"), "Paper should beat Rock");
        assertEquals(1, RuleEngine.getVictor("Scissors", "Paper"), "Scissors should beat Paper");

        assertEquals(2, RuleEngine.getVictor("Scissors", "Rock"), "Rock should beat Scissors");
        assertEquals(2, RuleEngine.getVictor("Rock", "Paper"), "Paper should beat Rock");
        assertEquals(2, RuleEngine.getVictor("Paper", "Scissors"), "Scissors should beat Paper");
    }

    private void testInvalidCases() {
        assertEquals(-1, RuleEngine.getVictor("Lizard", "Rock"), "Invalid move should return -1");
        assertEquals(-1, RuleEngine.getVictor("Rock", "Spock"), "Invalid move should return -1");
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