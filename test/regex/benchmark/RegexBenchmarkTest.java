package regex.benchmark;

import org.junit.Test;
import static org.junit.Assert.*;
import regex.input.RegexStringPreprocessor;
import regex.nfa.NFAConstructor;
import regex.nfa.NFAMatcher;
import regex.nfa.NFAState;

public class RegexBenchmarkTest {

    private boolean benchmarkWholeProgram(String regex, String input, int n) {
        boolean result = false;
        long timeStart = System.currentTimeMillis();
        for (int i = 0; i < n; i++) {
            regex = RegexStringPreprocessor.parseInput(regex);
            NFAState start = new NFAConstructor().constructNFA(regex);
            result = new NFAMatcher(start).match(input);
        }
        long timeEnd = System.currentTimeMillis();
        System.out.println("Integration: " + n + " runs of the program with parameters: " + regex + ", " + input + " took: "
                + (double)(timeEnd - timeStart) / n + "ms.");
        return result;
    }
    
    private boolean benchmarkPreprocessing(String regex, int n) {
        long timeStart = System.currentTimeMillis();
        for (int i = 0; i < 10; i++) {
            RegexStringPreprocessor.parseInput(regex);
        }
        long timeEnd = System.currentTimeMillis();
        System.out.println("Preprocessing: " + n + " runs of the program with parameters: " + regex + " took: "
                + (double)(timeEnd - timeStart) / n + "ms.");
        return true;
    }
    
    private boolean benchmarkConstructing(String regex, int n) {
        regex = RegexStringPreprocessor.parseInput(regex);
        long timeStart = System.currentTimeMillis();
        for (int i = 0; i < n; i++) {
            new NFAConstructor().constructNFA(regex);
        }
        long timeEnd = System.currentTimeMillis();
        System.out.println("Constructing: " + n + " runs of the program with parameters: " + regex + " took: "
                + (double)(timeEnd - timeStart) / n + "ms.");
        return true;
    }
     
    private boolean benchmarkMatching(String regex, String input, int n) {
        boolean result = false;
        regex = RegexStringPreprocessor.parseInput(regex);
        NFAState start = new NFAConstructor().constructNFA(regex);
        long timeStart = System.currentTimeMillis();
        for (int i = 0; i < n; i++) {
            result = new NFAMatcher(start).match(input);
        }
        long timeEnd = System.currentTimeMillis();
        System.out.println("Matching: " + n + " runs of the program with parameters: " + regex + ", " + input + " took: "
                + (double)(timeEnd - timeStart) / n + "ms.");
        return result;
    }

    @Test
    public void benchmarkWholeProgramTest1() {
        assertTrue(benchmarkWholeProgram("abc", "abc", 10));
    }
    /*
    @Test
    public void benchmarkPreprocessingTest1() {
        assertTrue(benchmarkPreprocessing("abc", 100));
    }
    
    @Test
    public void benchmarkConstructingTest1() {
        assertTrue(benchmarkConstructing("abc", 100));
    }

    @Test
    public void benchmarkMatchingTest1() {
        assertTrue(benchmarkMatching("abc", "abc", 100));
    }
    */
    
}
