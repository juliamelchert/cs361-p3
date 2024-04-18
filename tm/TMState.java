package tm;

import java.util.*;

/**
 * Represents a state within a TM.
 * @author Axel Murillo
 * @author Julia Melchert
 */
public class TMState {
    
    public String label;
    public Map<Character, Transition> transitions;

    /**
     * Constructor for an TMState object
     * @param label label for state
    */
    public TMState(String label) {
        this.label = label;
        this.transitions = new HashMap<>();
    }

    /**
     * Returns the label of a state
    */
    public String getLabel() {
        return this.label;
    }

    /**
     * Adds a transition to the transitions map for this state
     * @param transition transition from file in the format "next_state, write_symbol, move"
    */
    public void addTransition(Character readChar, Character writeChar, Character move, String nextStateLabel) {
        transitions.put(readChar, new Transition(writeChar, move, nextStateLabel));
    }

    /**
     * Gets transition of given character
     * @param readChar given character 
     * @return transition of character
     */
    public Transition getTransition(Character readChar) {
        return transitions.get(readChar);
    }
    
}

class Transition {
    public char writeChar;
    public char move;
    public String nextStateLabel;

    public Transition(char writeChar, char move, String nextStateLabel) {
        this.writeChar = writeChar;
        this.move = move;
        this.nextStateLabel = nextStateLabel;
    }
}
