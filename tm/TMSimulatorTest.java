package tm;
import org.junit.Assert;
import org.junit.Test;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class TMSimulatorTest {

    private TMSimulator initializeSimulator(String filePath) throws IOException {
        TMSimulator tm = new TMSimulator();
        List<String> lines = Files.readAllLines(Paths.get(filePath));
        tm.setupFromLines(lines); 
        return tm;
    }

    private String simulateAndGetTape(String filename) throws IOException {
        TMSimulator tm = initializeSimulator(filename);
        tm.runSimulation();
        return tm.getTape();
    }

    @Test
    public void testFile2Simulation() throws IOException {
        String result = simulateAndGetTape("test/file2.txt");
        Assert.assertEquals("031233333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333", result);
        Assert.assertEquals(140, result.length());
        Assert.assertEquals(414, sumOfSymbols(result));
    }

    private int sumOfSymbols(String tape) {
        return tape.chars().map(c -> c - '0').sum();  
    }

}
