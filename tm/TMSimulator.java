package tm;

import java.io.File;
import java.io.FileNotFoundException;

import java.util.*;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import tm.TMState;

/**
 * Represents a bi-infinite tape turing machine (TM), handling all transitions from a text file.
 * Manages the appropriate data for an NFA like the alphabet, states, start/final states, and transition table.
 * @author Axel Murillo
 * @author Julia Melchert
 */
public class TMSimulator {

    private String tape;
    private HashSet<TMState> allStates;
    private HashSet<String> alphabet;
    private TMState startState;
    private TMState finalState;
    private TMState currentState;
    // transitions are tracked in each individual TMState

    // Constructor
    public TMSimulator() {
        this.tape = new String();
        this.startState = new TMState("0");
        this.finalState = null;
        this.currentState = this.startState;
        this.allStates = new HashSet<TMState>();
        this.allStates.add(this.startState);
        this.alphabet = new HashSet<String>();
    }

    public TMState getState(String label) {
        // Iterate through all states and if the label matches, return the object
        for (TMState state : this.allStates) {
            if (state.getLabel().equals(label)) {
                return state;
            }
        }

        return null;
    }

    public HashSet<TMState> getAllStates() {
        return this.allStates;
    }

    public void addToStates(TMState state) {
        this.allStates.add(state);
    }

    public HashSet<String> getAlphabet() {
        return this.alphabet;
    }

    public Integer getAlphabetSize() {
        return this.alphabet.size();
    }

    public void addToAlphabet(String s) {
        this.alphabet.add(s);
    }

    public TMState getFinalState() {
        return this.finalState;
    }

    public void setFinalState(TMState state) {
        this.finalState = state;
    }

    public boolean isTapeEmpty() {
        return (this.tape.isEmpty()) ? true : false;
    }

    public String getTape() {
        return this.tape;
    } 

    public void setTape(String tape) {
        this.tape = tape;
    }

    // TODO: Write function that will run through the TMSimulator and change the tape accordingly
    public void runSimulation() {
        HashMap<Integer, Character> tape = new HashMap<>();
        int headPosition = 0;

        while (currentState != finalState) {
            char curr= tape.getOrDefault(headPosition, '0'); // Assuming '0' is the blank symbol
    
            Transition transition = currentState.getTransition(curr);
            if (transition == null) {
                break; // Halts if no transition is defined
            }
    
            // Write the character and move the head
            tape.put(headPosition, transition.writeChar);
            headPosition += (transition.move == 'R' ? 1 : -1);
    
            // Transition to the next state
            currentState = getState(transition.nextStateLabel);
        }
    
        // Output the content of the tape from the smallest to the largest index visited
        Integer minIndex = Collections.min(tape.keySet());
        Integer maxIndex = Collections.max(tape.keySet());
        StringBuilder output = new StringBuilder();
        for (int i = minIndex; i <= maxIndex; i++) {
            output.append(tape.getOrDefault(i, '0'));
        }

        System.out.println(output.toString());

    }

    /**
     * For use in TMSimulatorTest.java
     * @param lines
     */
    public void setupFromLines(List<String> lines) {
        Pattern transitionPattern = Pattern.compile("^([0-9]+),([0-9]),([0-9]),([LR]),([0-9]+)$");
        boolean isFirstLine = true, isSecondLine = false;
    
        int lineCounter = 0;
        for (String line : lines) {
            if (lineCounter == 0) {
                int numStates = Integer.parseInt(line);
                for (int i = 0; i <= numStates; i++) {
                    this.addToStates(new TMState(String.valueOf(i)));
                }
                isFirstLine = false;
                isSecondLine = true;
            } else if (isSecondLine) {
                String[] symbols = line.split("");
                for (String symbol : symbols) {
                    this.addToAlphabet(symbol);
                }
                isSecondLine = false;
            } else {
                Matcher matcher = transitionPattern.matcher(line);
                if (matcher.matches()) {
                    int fromIndex = Integer.parseInt(matcher.group(1));
                    char readChar = matcher.group(2).charAt(0);
                    char writeChar = matcher.group(3).charAt(0);
                    char move = matcher.group(4).charAt(0);
                    String toStateLabel = matcher.group(5);
    
                    TMState fromState = this.getState(String.valueOf(fromIndex));
                    if (fromState != null) {
                        fromState.addTransition(readChar, writeChar, move, toStateLabel);
                    }
                } else {
                    this.setTape(line);
                }
            }
            lineCounter++;
        }
    }
    
    

    public static void main(String[] args)
    {
        if (args.length > 0) {

            // Reads input file and initializes a TMSimulator with the given information
            try {
                TMSimulator tm = new TMSimulator();

                // Regex and indices for transitions
                Pattern transitionPattern = Pattern.compile("^[0-9]+,[0-9]+,[LR]$", Pattern.CASE_INSENSITIVE);
                Integer stateIndex = 0;
                Integer inputCounter = 0;

                // Read contents of file and create objects accordingly
                File inputFile = new File(args[0]);
                Scanner myReader = new Scanner(inputFile);

                Integer lineCounter = 0;
                while (myReader.hasNextLine()) {
                    String data = myReader.nextLine();
                    Matcher matcher = transitionPattern.matcher(data);
                  
                    // First line in file decides number of states
                    if (lineCounter == 0) {
                        for (int i = 1; i < Integer.parseInt(data) - 1; i++) {
                            tm.addToStates(new TMState(String.valueOf(i)));
                        }
                        tm.setFinalState(new TMState(String.valueOf(Integer.parseInt(data) - 1)));
                        tm.addToStates(tm.getFinalState());
                    }

                    // Second line in file decides alphabet
                    else if (lineCounter == 1) {
                        for (int i = 0; i < Integer.parseInt(data) + 1; i++) {
                            tm.addToAlphabet(new String(String.valueOf(i)));
                        }
                    }

                    // Check if the line is a transition with regex
                    else if (matcher.find()) {
                        int fromIndex = Integer.parseInt(matcher.group(1));
                        char readChar = matcher.group(2).charAt(0);
                        char writeChar = matcher.group(3).charAt(0);
                        char move = matcher.group(4).charAt(0);
                        String toStateString = matcher.group(5);
                        TMState fromState = tm.getState(String.valueOf(fromIndex));

                        if (fromState != null) {
                            fromState.addTransition(readChar, writeChar, move, toStateString);
                        }
                    }

                    // Otherwise, it must be the tape input
                    else {
                        tm.setTape(data);
                    }
                    lineCounter++;
                }
                myReader.close();

                // Initialize tape if it wasn't given in the file
                if (tm.isTapeEmpty()) {
                    tm.setTape("0");
                }

                tm.runSimulation();
                System.out.println(tm.getTape());

              } catch (FileNotFoundException e) {
                System.out.println("An error occurred.");
                e.printStackTrace();
              }

        } else {
            System.out.println("No command line arguments supplied.");
            System.exit(0);
        }
    }
}