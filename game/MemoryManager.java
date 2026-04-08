package game;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Scanner;

// Manages memory for MachineLearningMove
public class MemoryManager {
    private File memory;
    private String pastMoves; // Formatted as move/move/move etc.
    private int n;
    private HashMap<String, MemoryChoice> map;
    // String represents n - 1 past choices
    // MemoryChoice represents the possible nth Choice

    public MemoryManager() {
        try {
            pastMoves = "";
            n = 5;
            memory = new File("Memory.txt");
            map = new HashMap<>();
            initializeMemory();
        } catch (FileNotFoundException ex) {
            System.getLogger(MemoryManager.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }   
    }

    private void initializeMemory() throws FileNotFoundException {
        Scanner sc = new Scanner(memory);
        while (sc.hasNextLine()) {
            String curr = sc.nextLine();
            if (curr.isEmpty()) {
                continue;
            }
            String[] currStringBroken = curr.split("/");
            String key = "";
            for (int i = 0; i < n - 1; i++) {
                key += currStringBroken[i] + "/";
            }
            key = key.substring(0, key.length() - 1);
            if (map.containsKey(key)) {
                MemoryChoice mc = map.get(key);
                mc.initializePair(currStringBroken[n - 1], currStringBroken[n]);
            }
            else {
                MemoryChoice mc = new MemoryChoice();
                mc.initializePair(currStringBroken[n - 1], currStringBroken[n]);
                map.put(key, mc);
            }
        }
    }

    public String[] chooseMove() {
        if (map.containsKey(pastMoves)) {
            MemoryChoice mc = map.get(pastMoves);
            return mc.choose();
        }
        else {
            return RuleEngine.getMoves();
        }
    }

    public String getPredictedHumanMove() {
        if (!map.containsKey(pastMoves)) {
            return null;
        }
        return map.get(pastMoves).getPrediction();
    }

    public void chosenMove(String curr) {
        updatePastMoves(curr);
    }

    public void recordOpponentMove(String curr) {
        if (pastMoves.split("/").length != n - 1) {
            updatePastMoves(curr);
            return;
        }
        if (map.containsKey(pastMoves)) {
            MemoryChoice mc = map.get(pastMoves);
            mc.increment(curr);
        }
        else {
            MemoryChoice mc = new MemoryChoice();
            mc.increment(curr);
            map.put(pastMoves, mc);
        }
        updateMemory();
        updatePastMoves(curr);
    }

    private void updatePastMoves(String curr) {
        if (pastMoves.split("/").length != n - 1) {
            if (pastMoves.equals("")) {
                pastMoves = pastMoves + curr;
            }
            else {
                pastMoves = pastMoves + "/" + curr;
            }
        }
        else {
            int firstBreak = pastMoves.indexOf("/");
            if (firstBreak != -1) {
                pastMoves = pastMoves.substring(firstBreak + 1);
            }
            pastMoves = pastMoves + "/" + curr;
        }
    }

    private void updateMemory() {
        File temp = new File("MemoryTemp.txt");
        PrintStream ps;
        try {
            ps = new PrintStream(temp);
            for (Entry<String, MemoryChoice> entry : map.entrySet()) {
                String key = entry.getKey();
                MemoryChoice mc = entry.getValue();
                String[] resultArray = mc.toStringArray();
                for (String curr : resultArray) {
                    ps.println(key + "/" + curr);
                }
            }
            ps.close();
            memory.delete();
            temp.renameTo(memory);
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private class MemoryChoice {

        private HashMap<String, Integer> innerMap;

        public MemoryChoice() {
            innerMap = new HashMap<>();
        }

        public String[] choose() {
            int highest = 0;
            ArrayList<String> list = new ArrayList<>();
            for (Entry<String, Integer> entry : innerMap.entrySet()) {
                String entryMove = entry.getKey();
                int entryValue = entry.getValue();
                if (entryValue > highest) {
                    list.clear();
                    try {
                        list.add(RuleEngine.getVictor(entryMove));
                    } catch (Exception e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    highest = entryValue;
                }
                else if (entryValue == highest) {
                    list.add(entryMove);
                }
            }
            return list.toArray(String[]::new);
        }

        public String getPrediction() {
            int highest = 0;
            String prediction = null;
            for (Entry<String, Integer> entry : innerMap.entrySet()) {
                if (entry.getValue() > highest) {
                    highest = entry.getValue();
                    prediction = entry.getKey();
                }
            }
            return prediction;
        }

        public void initializePair(String key, String numberAsString) {
            Integer number = Integer.valueOf(numberAsString);
            innerMap.put(key, number);
        }

        public void increment(String curr) {
            if (innerMap.containsKey(curr)) {
                Integer a = innerMap.get(curr);
                innerMap.put(curr, a + 1);
            }
            else {
                innerMap.put(curr, 1);
            }
        }

        public String[] toStringArray() {
            String[] s = new String[innerMap.size()];
            int i = 0;
            for (Entry<String, Integer> entry : innerMap.entrySet()) {
                String entryMove = entry.getKey();
                int entryValue = entry.getValue();
                s[i] = entryMove + "/" + entryValue;
                i++;
            }
            return s;
        }
    }
}
