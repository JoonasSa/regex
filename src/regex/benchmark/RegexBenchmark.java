package regex.benchmark;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.regex.Matcher;
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
     * @return was viable benchmark type
     */
    public static boolean getBenchmark(char type, long times, String regex, String input) {
        switch (type) {
            case 'a':
                benchmarkWholeProgram(regex, input, times);
                System.out.println("----------------------------------------------------------------------------------");
                benchmarkPreprocessing(regex, times);
                System.out.println("----------------------------------------------------------------------------------");
                benchmarkConstructing(regex, times);
                System.out.println("----------------------------------------------------------------------------------");
                benchmarkMatching(regex, input, times);
                return true;
            case 'w':
                benchmarkWholeProgram(regex, input, times);
                return true;
            case 'p':
                benchmarkPreprocessing(regex, times);
                return true;
            case 'c':
                benchmarkConstructing(regex, times);
                return true;
            case 'm':
                benchmarkMatching(regex, input, times);
                return true;
            case 'r':
                regexVersusJava(regex, input, times);
                return true;
            case 'f':
                benchmarkFileRegex(regex, input);
                return true;
            case 'v':
                fileRegexVersusJava(regex, input);
                return true;
            default:
                System.out.println("Invalid benchmark type. Valid types are:\n"
                        + "    a: whole process with all prints\n"
                        + "    w: whole process\n"
                        + "    p: regex preprocessing\n"
                        + "    c: nfa construction\n"
                        + "    m: input string matching\n"
                        + "    f: find instances of regex in input file"
                        + "    r: comparisons against Java Patter.match()");
                return false;
        }
    }

    private static void benchmarkWholeProgram(String regex, String input, long n) {
        for (int i = 0; i < n; i++) {
            String parsed = RegexStringPreprocessor.parseInput(regex);
            NFAState start = new NFAConstructor().constructNFA(parsed);
            new NFAMatcher(start).match(input);
        }
    }

    private static void benchmarkPreprocessing(String regex, long n) {
        for (int i = 0; i < n; i++) {
            RegexStringPreprocessor.parseInput(regex);
        }
    }

    private static void benchmarkConstructing(String regex, long n) {
        regex = RegexStringPreprocessor.parseInput(regex);
        for (int i = 0; i < n; i++) {
            new NFAConstructor().constructNFA(regex);
        }
    }

    private static void benchmarkMatching(String regex, String input, long n) {
        String parsed = RegexStringPreprocessor.parseInput(regex);
        NFAState start = new NFAConstructor().constructNFA(parsed);
        for (int i = 0; i < n; i++) {
            new NFAMatcher(start).match(input);
        }
    }

    private static void benchmarkJavaRegex(String regex, String input, long n) {
        for (int i = 0; i < n; i++) {
            Pattern.matches(regex, input);
        }
    }

    private static void benchmarkFileRegex(String regex, String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line = br.readLine();

            String parsed = RegexStringPreprocessor.parseInput(".*" + regex + ".*"); //not anchored
            NFAState start = new NFAConstructor().constructNFA(parsed);
            NFAMatcher matcher = new NFAMatcher(start);

            int wordCount = 0;
            while (line != null) {
                for (String word : line.split(" ")) {
                    if (matcher.match(word)) {
                        wordCount++;
                    }
                }
                line = br.readLine();
            }

            System.out.println(wordCount + " words matching regex found.");
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private static void benchmarkJavaFileRegex(String regex, String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line = br.readLine();

            Pattern pattern = Pattern.compile(regex);
            Matcher matcher;

            int wordCount = 0;
            while (line != null) {
                for (String word : line.split(" ")) {
                    matcher = pattern.matcher(word);
                    if (matcher.find()) {
                        wordCount++;
                    }
                }
                line = br.readLine();
            }

            System.out.println(wordCount + " words matching regex found.");
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private static void regexVersusJava(String regex, String input, long times) {
        System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<MY REGEX<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
        long timeStart = System.currentTimeMillis();
        benchmarkWholeProgram(regex, input, times);
        long timeEnd = System.currentTimeMillis();
        System.out.println("1 run of the program with parameters: [ " + regex + ", " + input + " ]\n"
                + "Total time: " + (timeEnd - timeStart) + "ms.");
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>JAVA REGEX>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        timeStart = System.currentTimeMillis();
        benchmarkJavaRegex(regex, input, times);
        timeEnd = System.currentTimeMillis();
        System.out.println("1 run of the program with parameters: [ " + regex + ", " + input + " ]\n"
                + "Total time: " + (timeEnd - timeStart) + "ms.");
        System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<\n");
    }

    private static void fileRegexVersusJava(String regex, String input) {
        System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<MY REGEX<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
        long timeStart = System.currentTimeMillis();
        benchmarkFileRegex(regex, input);
        long timeEnd = System.currentTimeMillis();
        System.out.println("1 run of the program with parameters: [ " + regex + ", " + input + " ]\n"
                + "Total time: " + (timeEnd - timeStart) + "ms.");
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>JAVA REGEX>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        timeStart = System.currentTimeMillis();
        benchmarkJavaFileRegex(regex, input);
        timeEnd = System.currentTimeMillis();
        System.out.println("1 run of the program with parameters: [ " + regex + ", " + input + " ]\n"
                + "Total time: " + (timeEnd - timeStart) + "ms.");
        System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<\n");
    }
}
