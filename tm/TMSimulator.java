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
        System.out.println("Running simulation...");
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
                        String transition = data;

                        // Add transition to the appropriate "from" state
                        if (inputCounter < tm.getAlphabetSize() - 1) {
                            tm.getState(String.valueOf(stateIndex)).addTransition(transition);
                            inputCounter++;
                        } else {
                            tm.getState(String.valueOf(stateIndex)).addTransition(transition);
                            inputCounter = 0;
                            stateIndex++;
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