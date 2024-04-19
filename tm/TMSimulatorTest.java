package tm;

import org.junit.Assert;
import org.junit.Test;
import java.io.*;
import java.util.Map;
import java.util.HashMap;

/**
 * Testing class for TM.java and TMSimulator.java
 * Tests file0.txt, file2.txt and file5.txt
 * @author Axel Murillo
 * @author Julia Melchert
 */
public class TMSimulatorTest {

    private TM initializeAndSimulate(String filePath) throws IOException {
        Map<Integer, TMState> states = new HashMap<>();
        String inputString = "";

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            int numStates = Integer.parseInt(br.readLine());
            int numSymbols = Integer.parseInt(br.readLine());

            for (int i = 0; i < numStates; i++) {
                states.put(i, new TMState(i));
            }

            for (int i = 0; i < numStates - 1; i++) {
                for (int j = 0; j <= numSymbols; j++) {
                    String[] transition = br.readLine().split(",");
                    int nextState = Integer.parseInt(transition[0]);
                    int symbol = Integer.parseInt(transition[1]);
                    char direction = transition[2].charAt(0);
                    states.get(i).addTransition(j, states.get(nextState), symbol, direction);
                }
            }

            inputString = br.readLine();
            if (inputString == null) {
                inputString = "";
            } else {
                inputString = inputString.trim();
            }
        } catch (IOException e) {
            System.out.println("Error reading the input file: " + e.getMessage());
            throw e; 
        }

        TM tm = new TM(states, inputString);
        tm.runSimulation(); 
        return tm;
    }

    @Test
    public void testFile2Simulation() throws IOException {
        TM tm = initializeAndSimulate("file2.txt");
        String result = tm.printableTape();
        Assert.assertEquals("03123333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333", result);
        Assert.assertEquals(140, result.length());
        Assert.assertEquals(414, sumOfSymbols(result));
    }

    @Test
    public void testFile0Simulation() throws IOException {
        TM tm = initializeAndSimulate("file0.txt");
        String result = tm.printableTape();
        Assert.assertEquals("111111", result);
        Assert.assertEquals(6, result.length());
        Assert.assertEquals(6, sumOfSymbols(result));
    }

    @Test
    public void testFile5Simulation() throws IOException {
        TM tm = initializeAndSimulate("file5.txt");
        String result = tm.printableTape();
        Assert.assertEquals(20430, result.length());
        Assert.assertEquals(47189, sumOfSymbols(result));
    }


    private int sumOfSymbols(String tape) {
        return tape.chars().map(c -> c - '0').sum();
    }
}
