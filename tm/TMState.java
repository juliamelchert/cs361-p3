package tm;

import java.util.*;

/**
 * Represents a state within a TM.
 * @author Axel Murillo
 * @author Julia Melchert
 */
public class TMState {
    
    public String label;
    public Map<Character, TMState> transitions;

    /**
     * Constructor for an TMState object
     * @param label label for state
    */
    public TMState(String label) {
        this.label = label;
        this.transitions = new HashMap<Character, TMState>();
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
    public void addTransition(String transition) {
        // TODO: Implement this function so that the TMState's transitions map is actually updated
        System.out.println("Adding transition " + transition + " for State " + this.label);
    }

}
