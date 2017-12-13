package regex.benchmark;

import java.util.regex.Pattern;
import regex.input.RegexStringPreprocessor;
import regex.nfa.NFAConstructor;
import regex.nfa.NFAMatcher;
import regex.nfa.NFAState;

public class RegexBenchmark {
    
    public boolean getBenchmark(char type, long times, String regex, String input) {
        switch (type) {
            case 'a':
                boolean result = benchmarkWholeProgram(regex, input, times);
                System.out.println("----------------------------------------------------------------------------------");
                benchmarkPreprocessing(regex, times);
                System.out.println("----------------------------------------------------------------------------------");
                benchmarkConstructing(regex, times);
                System.out.println("----------------------------------------------------------------------------------");
                benchmarkMatching(regex, input, times);
                return result;
            case 'w':
                return benchmarkWholeProgram(regex, input, times);
            case 'p':
                return benchmarkPreprocessing(regex, times);
            case 'c':
                return benchmarkConstructing(regex, times);
            case 'm':
                return benchmarkMatching(regex, input, times);
            case 'r':
                result = benchmarkWholeProgram(regex, input, times);
                System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
                benchmarkJavaRegex(regex, input, times);
                System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<\n");
                return result;
            default:
                System.out.println("Invalid benchmark type. Valid types are:\n"
                    + "    a: whole process with all prints\n"
                    + "    w: whole process\n"
                    + "    p: regex preprocessing\n"
                    + "    c: nfa construction\n"
                    + "    m: input string matching\n"
                    + "    r: comparisons against Java Patter.match()");
        }
        return false;
    }

    private boolean benchmarkWholeProgram(String regex, String input, long n) {
        boolean result = false;
        long timeStart = System.currentTimeMillis();
        for (int i = 0; i < n; i++) {
            String parsed = RegexStringPreprocessor.parseInput(regex);
            NFAState start = new NFAConstructor().constructNFA(parsed);
            result = new NFAMatcher(start).match(input);
        }
        long timeEnd = System.currentTimeMillis();
        System.out.println("Integration benchmarks: " + n + " runs of the program with parameters: [ " + regex + ", " + input + " ]\n" +
                "Total time: " + (timeEnd - timeStart) + "ms. \nAverage time: " + ((double)(timeEnd - timeStart) / n) + "ms.");
        return result;
    }
    
    private boolean benchmarkPreprocessing(String regex, long n) {
        long timeStart = System.currentTimeMillis();
        for (int i = 0; i < n; i++) {
            RegexStringPreprocessor.parseInput(regex);
        }
        long timeEnd = System.currentTimeMillis();
        System.out.println("Preprocessing benchmarks: " + n + " runs of the program with parameters: [ " + regex + " ]\n" +
                "Total time: " + (timeEnd - timeStart) + "ms. \nAverage time: " + ((double)(timeEnd - timeStart) / n) + "ms.");
        return true;
    }
    
    private boolean benchmarkConstructing(String regex, long n) {
        regex = RegexStringPreprocessor.parseInput(regex);
        long timeStart = System.currentTimeMillis();
        for (int i = 0; i < n; i++) {
            new NFAConstructor().constructNFA(regex);
        }
        long timeEnd = System.currentTimeMillis();
        System.out.println("Constructing benchmarks: " + n + " runs of the program with parameters: [ " + regex + " ]\n" +
                "Total time: " + (timeEnd - timeStart) + "ms. \nAverage time: " + ((double)(timeEnd - timeStart) / n) + "ms.");
        return true;
    }
     
    private boolean benchmarkMatching(String regex, String input, long n) {
        boolean result = false;
        String parsed = RegexStringPreprocessor.parseInput(regex);
        NFAState start = new NFAConstructor().constructNFA(parsed);
        long timeStart = System.currentTimeMillis();
        for (int i = 0; i < n; i++) {
            result = new NFAMatcher(start).match(input);
        }
        long timeEnd = System.currentTimeMillis();
        System.out.println("Matching benchmarks: " + n + " runs of the program with parameters: [ " + regex + ", " + input + " ]\n" +
                "Total time: " + (timeEnd - timeStart) + "ms. \nAverage time: " + ((double)(timeEnd - timeStart) / n) + "ms.");
        return result;
    }
    
    private void benchmarkJavaRegex(String regex, String input, long n) {
        long timeStart = System.currentTimeMillis();
        for (int i = 0; i < n; i++) {
            Pattern.matches(regex, input);
        }
        long timeEnd = System.currentTimeMillis();
        System.out.println("Java regex benchmarks: " + n + " runs of the program with parameters: [ " + regex + ", " + input + " ]\n" +
                "Total time: " + (timeEnd - timeStart) + "ms. \nAverage time: " + ((double)(timeEnd - timeStart) / n) + "ms.");
    }
}
