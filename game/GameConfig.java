package game;

public class GameConfig {
    boolean random;
    boolean machineLearning;

    public GameConfig() {
        random = false;
        machineLearning = false;
    }

    public void parse(String[] args) {
        for (String curr : args) {
            if (curr.equals("-r")) {
                random = true;
            }
            else if (curr.equals("-m")) {
                machineLearning = true;
            }
        }
    }

    // If both are flagged or neither are flagged, defaults to RandomComputer
    public String getCPUType() throws IllegalArgumentException {
        if (random && machineLearning) {
            throw new IllegalArgumentException("Both flags set. Only set one at a time");
        }
        else if (random) {
            return "RandomComputer";
        }
        else if (machineLearning) {
            return "MachineLearningComputer";
        }
        else {
            throw new IllegalArgumentException("No flags set. Input -r for random and -m for machine learning.");
        }
    }

    public String getCPUName() throws IllegalArgumentException {
        if (random && machineLearning) {
            throw new IllegalArgumentException("Both flags set. Only set one at a time");
        }
        else if (random) {
            return "RandomComputer";
        }
        else if (machineLearning) {
            return "MachineLearningComputer";
        }
        else {
            throw new IllegalArgumentException("No flags set. Input -r for random and -m for machine learning.");
        }
    }
}
