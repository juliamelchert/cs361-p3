package tm;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;
import java.util.HashMap;

/**
 * The TMSimulator class serves as the main entry point for simulating a Turing Machine.
 * The class reads the configuration from the file, initializes the Turing Machine, and executes the simulation.
 */
public class TMSimulator {
    /**
     * The main method operates the execution of the program.
     *
     * @param args args[0] should be the file path
     */
    public static void main(String[] args) {
        //Validate that the input is correct
        if (args.length != 1) {
            System.out.println("Usage: java tm.TMSimulator <input_file>");
            return;
        }

        String inputFile = args[0]; //file path
        Map<Integer, TMState> states = new HashMap<>(); //map that stores states of TM
        String inputString = ""; //input string to be read from file

        //Reading the file to setup TM
        try (BufferedReader br = new BufferedReader(new FileReader(inputFile))) {
            int numStates = Integer.parseInt(br.readLine());
            int numSymbols = Integer.parseInt(br.readLine());

            for (int i = 0; i < numStates; i++) {
                states.put(i, new TMState(i));
            }

            for (int i = 0; i < numStates - 1; i++) {
                for (int j = 0; j <= numSymbols; j++) {
                    String[] transition = br.readLine().split(","); //split at the commas '(1,1,L)'
                    //Parse integers and characters then add to transition array
                    int nextState = Integer.parseInt(transition[0]); 
                    int symbol = Integer.parseInt(transition[1]);
                    char direction = transition[2].charAt(0);
                    //Add the transition after reading through line in file
                    states.get(i).addTransition(j, states.get(nextState), symbol, direction);
                }
            }
            
            //Read the input for the tape
            inputString = br.readLine();
            if (inputString == null) {
                inputString = "";
            } else {
                inputString = inputString.trim();
            }
        //Catch exceptions for invalid files
        } catch (IOException e) {
            System.out.println("Error reading the input file: " + e.getMessage());
            return;
        }

        //Run the simulation for TM
        TM tm = new TM(states, inputString);
        tm.simulate();

        //Print tape content for output
        tm.printTape();
    }
}