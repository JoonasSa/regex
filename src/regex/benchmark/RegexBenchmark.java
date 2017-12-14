package regex.benchmark;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.regex.Pattern;
import regex.input.RegexStringPreprocessor;
import regex.nfa.NFAConstructor;
import regex.nfa.NFAMatcher;
import regex.nfa.NFAState;

public class RegexBenchmark {

    /**
     * @param type test type
     * @param times how many times the test is run
     * @param regex
     * @param input
     */
    public static void getBenchmark(char type, long times, String regex, String input) {
        switch (type) {
            case 'a':
                benchmarkWholeProgram(regex, input, times);
                System.out.println("----------------------------------------------------------------------------------");
                benchmarkPreprocessing(regex, times);
                System.out.println("----------------------------------------------------------------------------------");
                benchmarkConstructing(regex, times);
                System.out.println("----------------------------------------------------------------------------------");
                benchmarkMatching(regex, input, times);
                return;
            case 'w':
                benchmarkWholeProgram(regex, input, times);
                return;
            case 'p':
                benchmarkPreprocessing(regex, times);
                return;
            case 'c':
                benchmarkConstructing(regex, times);
                return;
            case 'm':
                benchmarkMatching(regex, input, times);
                return;
            case 'r':
                benchmarkWholeProgram(regex, input, times);
                System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
                benchmarkJavaRegex(regex, input, times);
                System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<\n");
                return;
            default:
                System.out.println("Invalid benchmark type. Valid types are:\n"
                        + "    a: whole process with all prints\n"
                        + "    w: whole process\n"
                        + "    p: regex preprocessing\n"
                        + "    c: nfa construction\n"
                        + "    m: input string matching\n"
                        + "    r: comparisons against Java Patter.match()");
        }
    }

    private static void benchmarkWholeProgram(String regex, String input, long n) {
        long timeStart = System.currentTimeMillis();
        for (int i = 0; i < n; i++) {
            String parsed = RegexStringPreprocessor.parseInput(regex);
            NFAState start = new NFAConstructor().constructNFA(parsed);
            new NFAMatcher(start).match(input);
        }
        long timeEnd = System.currentTimeMillis();
        System.out.println("Integration benchmarks: " + n + " runs of the program with parameters: [ " + regex + ", " + input + " ]\n"
                + "Total time: " + (timeEnd - timeStart) + "ms. \nAverage time: " + ((double) (timeEnd - timeStart) / n) + "ms.");
    }

    private static void benchmarkPreprocessing(String regex, long n) {
        long timeStart = System.currentTimeMillis();
        for (int i = 0; i < n; i++) {
            RegexStringPreprocessor.parseInput(regex);
        }
        long timeEnd = System.currentTimeMillis();
        System.out.println("Preprocessing benchmarks: " + n + " runs of the program with parameters: [ " + regex + " ]\n"
                + "Total time: " + (timeEnd - timeStart) + "ms. \nAverage time: " + ((double) (timeEnd - timeStart) / n) + "ms.");
    }

    private static void benchmarkConstructing(String regex, long n) {
        regex = RegexStringPreprocessor.parseInput(regex);
        long timeStart = System.currentTimeMillis();
        for (int i = 0; i < n; i++) {
            new NFAConstructor().constructNFA(regex);
        }
        long timeEnd = System.currentTimeMillis();
        System.out.println("Constructing benchmarks: " + n + " runs of the program with parameters: [ " + regex + " ]\n"
                + "Total time: " + (timeEnd - timeStart) + "ms. \nAverage time: " + ((double) (timeEnd - timeStart) / n) + "ms.");
    }

    private static void benchmarkMatching(String regex, String input, long n) {
        String parsed = RegexStringPreprocessor.parseInput(regex);
        NFAState start = new NFAConstructor().constructNFA(parsed);
        long timeStart = System.currentTimeMillis();
        for (int i = 0; i < n; i++) {
            new NFAMatcher(start).match(input);
        }
        long timeEnd = System.currentTimeMillis();
        System.out.println("Matching benchmarks: " + n + " runs of the program with parameters: [ " + regex + ", " + input + " ]\n"
                + "Total time: " + (timeEnd - timeStart) + "ms. \nAverage time: " + ((double) (timeEnd - timeStart) / n) + "ms.");
    }

    private static void benchmarkJavaRegex(String regex, String input, long n) {
        long timeStart = System.currentTimeMillis();
        for (int i = 0; i < n; i++) {
            Pattern.matches(regex, input);
        }
        long timeEnd = System.currentTimeMillis();
        System.out.println("Java regex benchmarks: " + n + " runs of the program with parameters: [ " + regex + ", " + input + " ]\n"
                + "Total time: " + (timeEnd - timeStart) + "ms. \nAverage time: " + ((double) (timeEnd - timeStart) / n) + "ms.");
    }

    private static void benchmarkFileReading(String regex, String input, long n, String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();

            long timeStart = System.currentTimeMillis();
            String parsed = RegexStringPreprocessor.parseInput(".*" + regex + ".*"); //not an anchored regex
            NFAState start = new NFAConstructor().constructNFA(parsed);
            NFAMatcher matcher = new NFAMatcher(start);

            //TODO
            while (line != null) {
                sb.append(line);
                sb.append(System.lineSeparator());
                line = br.readLine();
            }

            long timeEnd = System.currentTimeMillis();
            System.out.println("Integration benchmarks: " + n + " runs of the program with parameters: [ " + regex + ", " + input + " ]\n"
                    + "Total time: " + (timeEnd - timeStart) + "ms. \nAverage time: " + ((double) (timeEnd - timeStart) / n) + "ms.");
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
