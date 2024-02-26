package edu.gonzaga.Farkle;

import java.util.Random;

/** Class to store the state of a single die. */
public class Die implements Comparable<Die> {
    private Integer sideUp; // Current die 'value' in range 1..numSides
    private Integer numSides; // Sides on the die (should be 1...INF integer)
    private static final Integer DEFAULT_NUM_SIDES = 6;
    private static final Integer DEFAULT_SIDE_UP = 1;
    private boolean rolled; // Flag to indicate if the die has been rolled

    public Die() {
        this.numSides = DEFAULT_NUM_SIDES;
        this.sideUp = DEFAULT_SIDE_UP;
        this.rolled = false; // Initialize rolled flag to false
    }

    public Die(Integer numSides) {
        this.numSides = numSides;
        this.sideUp = DEFAULT_SIDE_UP;
        this.rolled = false; // Initialize rolled flag to false
    }

    public Die(Integer numSides, Integer startingSide) {
        this.numSides = numSides;
        this.sideUp = startingSide;
        this.rolled = false; // Initialize rolled flag to false
    }

    /** Rolls the die once, getting new random value. */
    public void roll() {
        Random rand = new Random();
        this.sideUp = rand.nextInt(this.numSides) + 1;
        this.rolled = true; // Set rolled flag to true after rolling
    }

    /**
    * Returns current die value (the side that's up).
    *
    * @return Integer Current Die's Side Up
    */
    public Integer getSideUp() {
        return this.sideUp;
    }

    /**
    * Returns quantity of sides on the die.
    *
    * @return Integer number of sides on the die
    */
    public Integer getNumSides() {
        return this.numSides;
    }

    /**
    * Checks if the die has been rolled.
    *
    * @return boolean true if the die has been rolled, false otherwise
    */
    public boolean isRolled() {
        return this.rolled;
    }

    /**
    * Provides the ability to convert the Die object into a string representation, both with
    * .toString(), but also in System.out.println()
    *
    * @return String of whatever you want this die to say for itself
    */
    @Override
    public String toString() {
        return this.sideUp.toString();
    }

    /**
    * Makes two dice comparable using <, ==, >, etc. based on sideUp values.
    *
    * @param otherDie The die we're comparing to this one (two objects)
    * @return int -1, 0, 1 for less than, equal, greater than
    */
    @Override
    public int compareTo(Die otherDie) {
        return this.sideUp.compareTo(otherDie.sideUp);
    }
}
