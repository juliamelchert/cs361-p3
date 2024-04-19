package tm;

import java.util.Map;
import java.util.ArrayList;
import java.util.List;
import java.util.Collections;

/**
 * 
 * @author Axel Murillo
 * @author Julia Melchert
 */
public class TM {
    private Map<Integer, TMState> states;
    private List<Integer> tape;
    private int currentState;
    private int headPosition;

    /**
     * Constructs a Turing Machine with specified states and initializes the tape based on input string.
     * @param states map of machine states
     * @param inputString string representing initial values on the tape
     */
    public TM(Map<Integer, TMState> states, String inputString) {
        this.tape = new ArrayList<>(Collections.nCopies(10000, 0)); 
        this.states = states;
        this.currentState = this.headPosition= 0;

        //Get initial tape values from input 
        for (int i = 0; i < inputString.length(); i++) {
            set(headPosition + i, inputString.charAt(i) - '0');
        }
    }

    /**
     * Simulates the operation of the Turing Machine
     */
    public void runSimulation() {
        while (currentState != states.size() - 1) { 
            int currentSymbol = tape.get(headPosition);
            TMState.Transition transition = states.get(currentState).getTransition(currentSymbol);

            if (transition == null) {
                break; //break if no transition
            }

            setTapeValue(headPosition, transition.getWriteSymbol());

            //Move tape head left or right depending on direction
            moveTapeHead(transition.getDirection());
            currentState = transition.getNextState().getStateNumber();
        }
    }

    /**
     * Moves the tape head based on the direction specified in the transition
     * @param direction The direction ('L' for left, 'R' for right) to move the head
     */
    private void moveTapeHead(char direction) {
        if (direction == 'L') {
            headPosition = (headPosition == 0) ? expandTapeAtStart() : headPosition - 1;
        } else {
            headPosition++;
            if (headPosition >= tape.size()) {
                tape.add(0);
            }
        }
    }

    /**
     * Expands the tape at the start and returns 0 for head position
     * @return new head position / 0
     */
    private int expandTapeAtStart() {
        tape.add(0, 0);
        return 0;
    }

    /**
     * Sets tape value so no OutOfBoundsException occurs
     * @param index tape index to set
     * @param value value to set at specified index
     */
    private void setTapeValue(int index, int value) {
        if (index >= tape.size()) {
            expandTape(index - tape.size() + 1);
        }
        tape.set(index, value);
    }

    /**
     * Expands the tape by a specified number of elements, saves space
     * @param numberOfElements number of elements to add
     */
    private void expandTape(int numberOfElements) {
        tape.addAll(Collections.nCopies(numberOfElements, 0));
    }

    /**
     * Prints the contents of the tape from the first to the last non-zero character
     */
    public void printTape() {
        int startIndex = findFirstSignificantCharacter();
        int endIndex = findLastSignificantCharacter();

        StringBuilder sb = buildTapeOutput(startIndex, endIndex);
        String tapeContent = sb.toString();

        System.out.println(tapeContent);
        System.out.println("Output length: " + tapeContent.length());
        System.out.println("Sum of symbols: " + calculateSymbolSum(sb));
    }

    public String stringOutput() {
        StringBuilder sb = new StringBuilder();
        for (Integer symbol : tape) {
            if (symbol != 0) {
                sb.append(symbol);
            }
        }
        return sb.toString(); // Return the tape content as a string
    }

    /**
     * Finds the index of the first significant character on the tape
     * LEADING ZEROS ARE COUNTED
     *
     * @return index of first significant character
     */
    private int findFirstSignificantCharacter() {
        int index = 0;
        while (index < tape.size() && tape.get(index) == 0 && index < tape.size() - 1) {
            index++;
        }
        return index > 0 ? index - 1 : index;
    }

    /**
     * Find the index of last significant character
     *
     * @return index of last non-zero character
     */
    private int findLastSignificantCharacter() {
        int index = tape.size() - 1;
        while (index >= 0 && tape.get(index) == 0) {
            index--;
        }
        return index;
    }
    
    /**
     * Build the string representation of the tape
     *
     * @param start starting index of the segment 
     * @param end ending index of the segment
     * @return StringBuilder of the tape
     */
    private StringBuilder buildTapeOutput(int start, int end) {
        StringBuilder sb = new StringBuilder();
        for (int i = start; i <= end; i++) {
            sb.append(tape.get(i));
        }
        return sb;
    }

    /**
     * Calculates the sum of the integers
     * 
     * @param tapeContent content of tape as StringBuilder object
     * @return sum of the integers
     */
    private int calculateSymbolSum(StringBuilder tapeContent) {
        return tapeContent.chars()
                          .map(c -> c - '0')
                          .sum();
    }
    
    /**
     * Sets the symbol at the specified index on the tape
     * The tape will automatically expand if it reaches capacity
     *
     * @param index The index at which to set the symbol.
     * @param value The symbol value to set at the specified index.
     */
    public void set(int index, int value) {
        
        if (index >= tape.size()) {
            int additionalElementsNeeded = index - tape.size() + 1;
            tape.addAll(Collections.nCopies(additionalElementsNeeded, 0));
        }
        
        tape.set(index, value);
    }

    /**
     * Expands the tape by adding 10000 so there is no out of bounds exception
     */
    public void expandTape() {
        tape.addAll(Collections.nCopies(10000, 0)); 
    }

    /**
     * Method to be used in TMSimulatorTest.java
     * @return toString of StringBuilder object
     */
    public String printableTape() {
        int startIndex = findFirstSignificantCharacter();
        int endIndex = findLastSignificantCharacter();
    
        if (startIndex > endIndex) { 
            return "";
        }
    
        StringBuilder sb = new StringBuilder();
        for (int i = startIndex; i <= endIndex; i++) {
            sb.append(tape.get(i));
        }
        return sb.toString();
    }
}