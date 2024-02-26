package edu.gonzaga.Farkle;

/** Class to print dice with ASCII art. */
public class DicePrinter {
    
    // Method to print the die with ASCII art
    public static void printDie(int dieNumber, int value) {
        char dieLetter = (char) ('A' + (dieNumber - 1));
        System.out.println("Die " + dieLetter + ":");
        switch (value) {
            case 1:
                System.out.println("  -------");
                System.out.println(" |       |");
                System.out.println(" |   *   |");
                System.out.println(" |       |");
                System.out.println("  -------");
                break;
            case 2:
                System.out.println("  -------");
                System.out.println(" | *     |");
                System.out.println(" |       |");
                System.out.println(" |     * |");
                System.out.println("  -------");
                break;
            case 3:
                System.out.println("  -------");
                System.out.println(" | *     |");
                System.out.println(" |   *   |");
                System.out.println(" |     * |");
                System.out.println("  -------");
                break;
            case 4:
                System.out.println("  -------");
                System.out.println(" | *   * |");
                System.out.println(" |       |");
                System.out.println(" | *   * |");
                System.out.println("  -------");
                break;
            case 5:
                System.out.println("  -------");
                System.out.println(" | *   * |");
                System.out.println(" |   *   |");
                System.out.println(" | *   * |");
                System.out.println("  -------");
                break;
            case 6:
                System.out.println("  -------");
                System.out.println(" | *   * |");
                System.out.println(" | *   * |");
                System.out.println(" | *   * |");
                System.out.println("  -------");
                break;
            default: 
                System.out.println("Invalid die value.");
                break;
        }
        System.out.println(); // Add a newline after printing the die
    }
}
