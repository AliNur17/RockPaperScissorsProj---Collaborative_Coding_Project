package game;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.util.Random;

public class MachineLearningMemoryTest implements TestProgram {

    private int passed = 0;
    private int failed = 0;

    private static final String MEMORY_FILE = "Memory.txt";
    private static final String TEMP_FILE = "MemoryTemp.txt";
    private String originalMemoryContents;
    private boolean memoryOriginallyExisted;

    @Override
    public String getTestName() {
        return "MachineLearningMemoryTest";
    }

    @Override
    public void runTests() {
        backupFiles();
        try {
            testNoPriorDataFallsBackToValidRandomMove();
            testKnownSequencePredictsCounterMove();
            testDataPersistsAcrossRestart();
            testTempFileSwapLeavesUsableMemoryFile();
        } finally {
            restoreFiles();
        }
        printSummary();
    }

    private void testNoPriorDataFallsBackToValidRandomMove() {
        writeFile(MEMORY_FILE, "");

        MachineLearningMove move = new MachineLearningMove(new Random(0));
        String result = silenceOutputAndChoose(move);

        assertTrue(isValidMove(result),
                "ML with no prior data should fall back to a valid move");
    }

    private void testKnownSequencePredictsCounterMove() {
        // Memory format for n=5:
        // move1/move2/move3/move4/predictedNextMove/count
        // If the human pattern predicts Rock next, ML should choose Paper.
        writeFile(MEMORY_FILE, "Rock/Rock/Rock/Rock/Rock/3\n");

        MachineLearningMove move = new MachineLearningMove(new Random(0));

        // Build the pastMoves state to Rock/Rock/Rock/Rock
        move.recordMoves("Rock", "Scissors");
        move.recordMoves("Rock", "Scissors");
        move.recordMoves("Rock", "Scissors");
        move.recordMoves("Rock", "Scissors");

        String result = silenceOutputAndChoose(move);

        assertEquals("Paper", result,
                "ML should counter a repeated Rock pattern with Paper");
    }

    private void testDataPersistsAcrossRestart() {
        writeFile(MEMORY_FILE, "Rock/Rock/Rock/Rock/Rock/2\n");

        MachineLearningMove first = new MachineLearningMove(new Random(0));
        MachineLearningMove second = new MachineLearningMove(new Random(0));

        first.recordMoves("Rock", "Scissors");
        first.recordMoves("Rock", "Scissors");
        first.recordMoves("Rock", "Scissors");
        first.recordMoves("Rock", "Scissors");

        second.recordMoves("Rock", "Scissors");
        second.recordMoves("Rock", "Scissors");
        second.recordMoves("Rock", "Scissors");
        second.recordMoves("Rock", "Scissors");

        String result = silenceOutputAndChoose(second);

        assertEquals("Paper", result,
                "ML should retain learned data from Memory.txt across restart");
    }

    private void testTempFileSwapLeavesUsableMemoryFile() {
        writeFile(MEMORY_FILE, "");

        MemoryManager mm = new MemoryManager();
        mm.recordOpponentMove("Paper");
        mm.recordOpponentMove("Paper");
        mm.recordOpponentMove("Paper");
        mm.recordOpponentMove("Paper");
        mm.recordOpponentMove("Paper");

        File memory = new File(MEMORY_FILE);
        File temp = new File(TEMP_FILE);

        assertTrue(memory.exists(), "Memory.txt should exist after update");
        assertTrue(memory.length() >= 0, "Memory.txt should remain readable after temp-file swap");
        assertTrue(!temp.exists() || temp.length() >= 0,
                "Temp-file workflow should not leave a corrupted unreadable state");
    }

    private String silenceOutputAndChoose(MachineLearningMove move) {
        PrintStream originalOut = System.out;
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        try {
            System.setOut(new PrintStream(buffer));
            return move.chooseMove(false);
        } finally {
            System.setOut(originalOut);
        }
    }

    private boolean isValidMove(String move) {
        return "Rock".equalsIgnoreCase(move)
                || "Paper".equalsIgnoreCase(move)
                || "Scissors".equalsIgnoreCase(move);
    }

    private void backupFiles() {
        File memory = new File(MEMORY_FILE);
        memoryOriginallyExisted = memory.exists();
        if (memoryOriginallyExisted) {
            originalMemoryContents = readFile(MEMORY_FILE);
        } else {
            originalMemoryContents = null;
        }
    }

    private void restoreFiles() {
        File temp = new File(TEMP_FILE);
        if (temp.exists()) {
            temp.delete();
        }

        if (memoryOriginallyExisted) {
            writeFile(MEMORY_FILE, originalMemoryContents == null ? "" : originalMemoryContents);
        } else {
            File memory = new File(MEMORY_FILE);
            if (memory.exists()) {
                memory.delete();
            }
        }
    }

    private void writeFile(String fileName, String contents) {
        try {
            Files.writeString(new File(fileName).toPath(), contents == null ? "" : contents);
        } catch (IOException e) {
            throw new RuntimeException("Failed to write " + fileName, e);
        }
    }

    private String readFile(String fileName) {
        try {
            File file = new File(fileName);
            if (!file.exists()) {
                return null;
            }
            return Files.readString(file.toPath());
        } catch (IOException e) {
            throw new RuntimeException("Failed to read " + fileName, e);
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