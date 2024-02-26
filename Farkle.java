package edu.gonzaga.Farkle;

import java.util.Arrays;
import java.util.Scanner;

/** Main program class for launching Farkle program. */
public class Farkle {
    // This main is where your Farkle game starts execution for general use.
    private String playerName;

    // Constructor
    public Farkle() {
        this.playerName = "Unknown Player"; // Default player name
    }

    // Method to set the player's name
    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }
    
    // Method to get the player's name
    public String getPlayerName() {
        return this.playerName;
    }
    public static void main(String[] args) {
        System.out.print("\033[H\033[2J");
        System.out.flush();

        Die[] dice = new Die[6]; // Array to store the dice objects
        for (int i = 0; i < dice.length; i++) {
            dice[i] = new Die(6); // Create a new die object with 6 sides
        }
        System.out.println(" _      _____ _     ____  ____  _      _____   _____  ____                        \n" + //
        "/ \\  /|/  __// \\   /   _\\/  _ \\/ \\__/|/  __/  /__ __\\/  _ \\                       \n" + //
        "| |  |||  \\  | |   |  /  | / \\|| |\\/|||  \\      / \\  | / \\|                       \n" + //
        "| |/\\|||  /_ | |_/\\|  \\_ | \\_/|| |  |||  /_     | |  | \\_/|                       \n" + //
        "\\_/  \\|\\____\\\\____/\\____/\\____/\\_/  \\|\\____\\    \\_/  \\____/                       \n" + //
        "                                                                                  \n" + //
        "                           ____  ____  _____   _____ ____  ____  _  __ _     _____\n" + //
        "                          /_   \\/  _ \\/  __/  /    //  _ \\/  __\\/ |/ // \\   /  __/\n" + //
        "                           /   /| / \\|| |  _  |  __\\| / \\||  \\/||   / | |   |  \\  \n" + //
        "                          /   /_| |-||| |_//  | |   | |-|||    /|   \\ | |_/\\|  /_ \n" + //
        "                          \\____/\\_/ \\|\\____\\  \\_/   \\_/ \\|\\_/\\_\\\\_|\\_\\\\____/\\____\\\n" + //
        "                                                                                  \n" + //
        
        "By Sean Nickerson \nCopyright: 2023 ");
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter your name: ");
        String playerName = scanner.nextLine();
        if (playerName.isEmpty()) {
            playerName = "Unknown Player"; // Default to "Unknown Player" if no name is provided

        }

        Scanner scn = new Scanner(System.in);
        System.out.print("Would you like to roll Y/N: " + playerName + "? ");
        String answer = scn.next();
        if (answer.equalsIgnoreCase("Y")) {
            System.out.print("\033[H\033[2J");
            System.out.flush();
            int[] diceValues = new int[dice.length];
            boolean[] inMeld = new boolean[dice.length];
            int score = 0; // Initialize the score

            // Roll the dice at the beginning and print their ASCII representation
            System.out.println("Dice Hand:");
            for (int i = 0; i < dice.length; i++) {
                dice[i].roll(); // Roll each die
                DicePrinter.printDie(i + 1, dice[i].getSideUp()); // Print the die with ASCII art
                diceValues[i] = dice[i].getSideUp(); // Store the rolled value
            }
            boolean hasHotHand = false;
            // Loop until the user decides to end the round or gets a Farkle
            while (true) {
                // Check if the current dice combination allows for any valid scoring
                // combinations
                if (!ScoringEngine.hasValidCombination(diceValues)) {
                    System.out.println("You farkled!"); // Print Farkle message
                    break; // Exit the loop
                }

                // Print the hand and meld with the current score
                printHandAndMeld(diceValues, inMeld, score, dice);

                if (hasHotHand) {
                    System.out.println("***** HOT HAND! ****");
                    System.out.print(
                            "Would you like to roll 6 new dice, or bank and end your turn? (Type 'R' to roll, 'B' to bank): ");
                    String hotHandDecision = scn.next();
                    if (hotHandDecision.equalsIgnoreCase("R")) {
                        // Roll 6 new dice
                        for (int i = 0; i < dice.length; i++) {
                            dice[i].roll();
                            diceValues[i] = dice[i].getSideUp();
                        }
                        // Reset meld flags
                        Arrays.fill(inMeld, false);
                        // Reset Hot Hand flag
                        hasHotHand = false;
                        // Continue the loop for the new round
                        continue;
                    } else if (hotHandDecision.equalsIgnoreCase("B")) {
                        // Bank and end the turn
                        break;
                    }
                }

                // Prompt user for action
                System.out.print("\n" + playerName + ", Enter dice letters to add to the meld, or 'K' to finish: ");
                String input = scn.next();
                if (input.equalsIgnoreCase("K")) {
                    break;
                } else if (input.equalsIgnoreCase("R")) {
                    // Reroll the dice that are not in the meld
                    for (int i = 0; i < dice.length; i++) {
                        if (!inMeld[i]) {
                            dice[i].roll(); // Reroll the die
                            diceValues[i] = dice[i].getSideUp(); // Update the value
                        }
                    }
                } else {
                    // Add or remove the dice from the meld
                    for (char letter : input.toUpperCase().toCharArray()) {
                        int diceIndex = letter - 'A';
                        if (diceIndex >= 0 && diceIndex < dice.length) {
                            // Toggle the state of the dice in the meld
                            inMeld[diceIndex] = !inMeld[diceIndex];
                        }
                    }
                }

                // Recalculate the score based on the dice in the meld
                score = calculateMeldScore(diceValues, inMeld);
            }

        } else if (answer.equalsIgnoreCase("N")) {
            System.out.print("\033[H\033[2J");
            System.out.flush();
            System.out.println("Good Bye");
            System.out.println();
        }

        scn.close(); // Close the scanner after use
        scanner.close();
    }

    // Method to print the hand and meld

    private static void printHandAndMeld(int[] diceValues, boolean[] inMeld, int score, Die[] dice) {
        System.out.println("Die   Hand |   Meld");
        System.out.println("------------+---------------");
        char letter = 'A';
        for (int i = 0; i < diceValues.length; i++) {
            String meldStatus = inMeld[i] ? "X" : ""; // Default meld status

            // Check if the die has been rerolled but not included in the meld
            if (dice[i].isRolled() && !inMeld[i]) {
                diceValues[i] = dice[i].getSideUp(); // Update the value of the die
                meldStatus = "N/A"; // Mark the die as rerolled
            }

            System.out.printf(" (%c)    %d   |   %s", letter++, diceValues[i], meldStatus);
            System.out.println();
        }
        System.out.println("------------+---------------");
        System.out.println("                Meld Score: " + score);
        System.out.println();
        System.out.println(" (K) BanK Meld & End Round");
        System.out.println(" (Q) Quit game");
    }

    // Method to calculate the score based only on the dice in the meld
    private static int calculateMeldScore(int[] diceValues, boolean[] inMeld) {
        int[] meldDiceValues = new int[6]; // Store the values of dice in the meld
        int count = 0; // Counter for the number of dice in the meld
        for (int i = 0; i < diceValues.length; i++) {
            if (inMeld[i]) {
                meldDiceValues[count++] = diceValues[i];
            }
        }
        // Trim the array to only include dice in the meld
        meldDiceValues = Arrays.copyOf(meldDiceValues, count);
        // Calculate the score based on the dice in the meld using the ScoringEngine
        return ScoringEngine.calculateScore(meldDiceValues);
    }

}
