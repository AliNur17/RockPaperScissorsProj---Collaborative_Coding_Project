package game;

public class GameConfigFlagTest implements TestProgram {

    private int passed = 0;
    private int failed = 0;

    @Override
    public String getTestName() {
        return "GameConfigFlagTest";
    }

    @Override
    public void runTests() {
        testRandomFlagSelectsRandomComputer();
        testMachineLearningFlagSelectsMachineLearningComputer();
        testBothFlagsThrowException();
        testNoFlagsThrowException();
        printSummary();
    }

    private void testRandomFlagSelectsRandomComputer() {
        GameConfig config = new GameConfig();
        config.parse(new String[]{"-r"});

        assertEquals("RandomComputer", config.getCPUType(),
                "-r should select RandomComputer type");
        assertEquals("RandomComputer", config.getCPUName(),
                "-r should select RandomComputer name");
    }

    private void testMachineLearningFlagSelectsMachineLearningComputer() {
        GameConfig config = new GameConfig();
        config.parse(new String[]{"-m"});

        assertEquals("MachineLearningComputer", config.getCPUType(),
                "-m should select MachineLearningComputer type");
        assertEquals("MachineLearningComputer", config.getCPUName(),
                "-m should select MachineLearningComputer name");
    }

    private void testBothFlagsThrowException() {
        GameConfig config = new GameConfig();
        config.parse(new String[]{"-r", "-m"});

        try {
            config.getCPUType();
            failed++;
            System.out.println("FAIL: -r and -m together should throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            passed++;
            System.out.println("PASS: -r and -m together throw IllegalArgumentException");
        } catch (Exception e) {
            failed++;
            System.out.println("FAIL: Wrong exception type thrown for conflicting flags");
        }
    }

    private void testNoFlagsThrowException() {
        GameConfig config = new GameConfig();
        config.parse(new String[]{});

        try {
            config.getCPUType();
            failed++;
            System.out.println("FAIL: No flags should throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            passed++;
            System.out.println("PASS: No flags throw IllegalArgumentException");
        } catch (Exception e) {
            failed++;
            System.out.println("FAIL: Wrong exception type thrown for missing flags");
        }
    }

    private void assertEquals(Object expected, Object actual, String msg) {
        if ((expected == null && actual == null)
                || (expected != null && expected.equals(actual))) {
            passed++;
            System.out.println("PASS: " + msg);
        } else {
            failed++;
            System.out.println("FAIL: " + msg);
            System.out.println("  Expected: " + expected);
            System.out.println("  Actual:   " + actual);
        }
    }

    private void printSummary() {
        System.out.println("\n=== " + getTestName() + " Summary ===");
        System.out.println("Passed: " + passed);
        System.out.println("Failed: " + failed);
    }
}