package edu.gonzaga.Farkle;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class ScoringEngine {
    private static final Map<String, Integer> SCORES_MAP = new HashMap<>();

    static {
        // Initialize the scores map
        SCORES_MAP.put("1", 100);   // Single 1s
        SCORES_MAP.put("11", 200);  // Two 1s
        SCORES_MAP.put("5", 50);    // Single 5s
        SCORES_MAP.put("15", 500);  // Three different pairs
        SCORES_MAP.put("111", 1000); // Triple 1s
        SCORES_MAP.put("222", 200); // Triple #s
        SCORES_MAP.put("333", 300); // Triple #s
        SCORES_MAP.put("444", 400); // Triple #s
        SCORES_MAP.put("555", 500); // Triple #s
        SCORES_MAP.put("666", 600); // Triple #s
    }

    // Method to calculate the score based on the dice values
    public static int calculateScore(int[] diceValues) {
        int score = 0;
        Arrays.sort(diceValues);

        // Check for FARKLE condition
        if (isFarkle(diceValues)) {
            return 0;
        }

        StringBuilder diceStrBuilder = new StringBuilder();
        for (int value : diceValues) {
            diceStrBuilder.append(value);
        }
        String diceStr = diceStrBuilder.toString();

        // Check for straight
        if (diceStr.equals("123456")) {
            score += 1000;
        }

        // Check for three pairs
        if (diceValues.length == 6) {
            int pairCount = 0;
            for (int i = 0; i < 5; i++) {
                if (diceValues[i] == diceValues[i + 1]) {
                    pairCount++;
                    i++; // Skip the next element
                }
            }
            if (pairCount == 3) {
                score += 750;
            }
        }

        // Check for triples
        Map<Integer, Integer> countMap = new HashMap<>();
        for (int value : diceValues) {
            countMap.put(value, countMap.getOrDefault(value, 0) + 1);
        }
        for (Map.Entry<Integer, Integer> entry : countMap.entrySet()) {
            int value = entry.getKey();
            int count = entry.getValue();
            if (count >= 3) {
                if (value == 1) {
                    score += 1000; // Triple 1s
                } else {
                    score += value * 100; // Triple #s
                }
                score += (count - 3) * 100 * value; // Extra dice face values
            }
        }

        // Check for single 1s and 5s
        for (int value : diceValues) {
            if (value == 1) {
                score += 100; // Single 1s
            } else if (value == 5) {
                score += 50; // Single 5s
            }
        }

        // Check for two 1s and two 5s
        int onesCount = countMap.getOrDefault(1, 0);
        int fivesCount = countMap.getOrDefault(5, 0);
        score += (onesCount / 3) * 1000; // Extra triple 1s
        score += (fivesCount / 3) * 500; // Extra triple 5s

        return score;
    }

    //  check if a FARKLE has occurred
    public static boolean isFarkle(int[] diceValues) {

        boolean hasOnes = false;
        boolean hasFives = false;
        for (int value : diceValues) {
            if (value == 1) {
                hasOnes = true;
            } else if (value == 5) {
                hasFives = true;
            }
        }
        return !hasOnes && !hasFives;
    }

    // check if there are valid scoring combinations based on the current dice values
    public static boolean hasValidCombination(int[] diceValues) {
        for (int i = 0; i < diceValues.length; i++) {
            for (int j = i + 1; j < diceValues.length; j++) {
                for (int k = j + 1; k < diceValues.length; k++) {
                    int[] subset = { diceValues[i], diceValues[j], diceValues[k] };
                    Arrays.sort(subset);
                    if (calculateScore(subset) > 0) {
                        return true; // At least one valid combination found
                    }
                }
            }
        }
        return false; 
    }
}
