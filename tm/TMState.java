package tm;

import java.util.Map;
import java.util.HashMap;

/**
 * Represents a state in a Turing Machine. Each state can have multiple transitions
 * based on different symbols read from the tape.
 * @author Axel Murillo
 * @author Julia Melchert
 */
public class TMState {
    private int stateNumber;
    private Map<Integer, Transition> transitions;

    /**
     * Constructs a new TMState 
     * @param stateNumber the identifier for this state
     */
    public TMState(int stateNumber) {
        this.stateNumber = stateNumber;
        this.transitions = new HashMap<>();
    }

    /**
     * Adds a transition from this state to another based on a read symbol.
     * @param symbol the symbol read from the tape 
     * @param nextState the state to transition to 
     * @param writeSymbol the symbol to write to the tape 
     * @param direction the direction to move the tape head ('L' for left, 'R' for right)
     */
    public void addTransition(int symbol, TMState nextState, int writeSymbol, char direction) {
        transitions.put(symbol, new Transition(nextState, writeSymbol, direction));
    }

    /**
     * Retrieves the transition corresponding to a given symbol read from the tape.
     * @param symbol the symbol read from the tape
     * @return the Transition associated with the read symbol/null for no symbol
     */
    public Transition getTransition(int symbol) {
        return transitions.get(symbol);
    }

    /**
     * Gets the state number of this TMState.
     * @return the state number
     */
    public int getStateNumber() {
        return stateNumber;
    }

    /**
     * Class that represents a transition in a Turing Machine
     */
    public static class Transition {
        private TMState nextState;
        private int writeSymbol;
        private char direction;

        /**
         * Constructor for Transition
         * @param nextState the state to which the machine transitions
         * @param writeSymbol the symbol that should be written to the tape
         * @param direction the direction the tape head should move L/R
         */
        public Transition(TMState nextState, int writeSymbol, char direction) {
            this.nextState = nextState;
            this.writeSymbol = writeSymbol;
            this.direction = direction;
        }

        /**
         * Returns the state to which this transition leads
         * @return the next state
         */
        public TMState getNextState() {
            return nextState;
        }

        /**
         * Returns the symbol that this transition writes to the tape
         * @return the symbol to write
         */
        public int getWriteSymbol() {
            return writeSymbol;
        }

        /**
         * Returns the direction the tape head moves during this transition
         * @return the direction L/R
         */
        public char getDirection() {
            return direction;
        }
    }
}
