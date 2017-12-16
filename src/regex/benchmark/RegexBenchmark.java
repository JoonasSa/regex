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
                break;
            case 'w':
                benchmarkWholeProgram(regex, input, times);
                break;
            case 'p':
                benchmarkPreprocessing(regex, times);
                break;
            case 'c':
                benchmarkConstructing(regex, times);
                break;
            case 'm':
                benchmarkMatching(regex, input, times);
                break;
            case 'r':
                regexVersusJava(regex, input, times);
                break;
            case 'f':
                benchmarkFileRegex(regex, input, true);
                break;
            case 'v':
                fileRegexVersusJava(regex, input, true);
                break;
            case 'l':
                fileRegexVersusJava(regex, input, false);
                break;
            default:
                System.out.println("Invalid benchmark type. Valid types are:\n"
                        + "    a: whole process with all prints\n"
                        + "    w: whole process\n"
                        + "    p: regex preprocessing\n"
                        + "    c: nfa construction\n"
                        + "    m: input string matching\n"
                        + "    f: find instances of regex in input file"
                        + "    r: comparisons against Java Patter.match()");
                break;
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

    private static void benchmarkFileRegex(String regex, String filePath, boolean wordByWord) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line = br.readLine();

            if (wordByWord) {
                String parsed = RegexStringPreprocessor.parseInput(regex);
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
            } else {
                String parsed = RegexStringPreprocessor.parseInput(".*" + regex + ".*"); //non-anchored
                NFAState start = new NFAConstructor().constructNFA(parsed);
                NFAMatcher matcher = new NFAMatcher(start);
                int lineCount = 0;
                while (line != null) {
                    if (matcher.match(line)) {
                        lineCount++;
                    }
                    line = br.readLine();
                }
                System.out.println(lineCount + " lines containing a match for the regex.");
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private static void benchmarkJavaFileRegex(String regex, String filePath, boolean wordByWord) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line = br.readLine();
            Pattern pattern = Pattern.compile(regex);

            if (wordByWord) {
                int wordCount = 0;
                while (line != null) {
                    for (String word : line.split(" ")) {
                        if (pattern.matcher(word).matches()) {
                            wordCount++;
                        }
                    }
                    line = br.readLine();
                }
                System.out.println(wordCount + " words matching regex found.");
            } else {
                Matcher matcher;
                int lineCount = 0;
                while (line != null) {
                    matcher = pattern.matcher(line);
                    if (matcher.find()) {
                        lineCount++;
                    }
                    line = br.readLine();
                }
                System.out.println(lineCount + " lines containing a match for the regex.");
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private static void regexVersusJava(String regex, String input, long times) {
        System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<MY REGEX<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
        long timeStart = System.currentTimeMillis();
        benchmarkWholeProgram(regex, input, times);
        long timeEnd = System.currentTimeMillis();
        System.out.println(times + " runs of the program with parameters: [ " + regex + ", " + input + " ]\n"
                + "Total time: " + (timeEnd - timeStart) + "ms.");
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>JAVA REGEX>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        timeStart = System.currentTimeMillis();
        benchmarkJavaRegex(regex, input, times);
        timeEnd = System.currentTimeMillis();
        System.out.println(times + " runs of the program with parameters: [ " + regex + ", " + input + " ]\n"
                + "Total time: " + (timeEnd - timeStart) + "ms.");
        System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<\n");
    }

    private static void fileRegexVersusJava(String regex, String input, boolean wordByWord) {
        System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<MY REGEX<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
        long timeStart = System.currentTimeMillis();
        benchmarkFileRegex(regex, input, wordByWord);
        long timeEnd = System.currentTimeMillis();
        System.out.println("1 run of the program with parameters: [ " + regex + ", " + input + " ]\n"
                + "Total time: " + (timeEnd - timeStart) + "ms.");
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>JAVA REGEX>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        timeStart = System.currentTimeMillis();
        benchmarkJavaFileRegex(regex, input, wordByWord);
        timeEnd = System.currentTimeMillis();
        System.out.println("1 run of the program with parameters: [ " + regex + ", " + input + " ]\n"
                + "Total time: " + (timeEnd - timeStart) + "ms.");
        System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<\n");
    }
}
