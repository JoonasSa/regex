
import java.util.regex.Pattern;
import regex.benchmark.RegexBenchmark;
import regex.nfa.*;
import regex.input.RegexStringPreprocessor;

public class Main {

    /**
     * @author Joonas Sarapalo
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        if (args.length == 0) {
            warnAndExit("No arguments received!");
        }
        if (args[0].equals("--help")) {
            helpAndExit();
        }
        if (args.length < 2) {
            warnAndExit("Regex and input string required!");
        }

        String regex = args[0];
        String input = args[1];
        
        //no test flag set
        if (args.length == 2) { 
            runProgram(regex, input);
            return;
        } else if (args.length > 4) {
            warnAndExit("Too many arguments provided!");
        }
        
        char type = args[2].charAt(0);
        
        //Only run once
        if (type == 'v' || type == 'r' || type == 'l') {
            RegexBenchmark.getBenchmark(type, 1, regex, input);   
            return;
        }
        
        //Use Java regex just in case mine has bugs
        if (!Pattern.matches(regex, input)) {
            warnAndExit("Input string doesn't match the regex!");
        }

        if (args.length == 3) {
            int mul = 1;
            for (int i = 0; i < 3; i++) {
                long timeStart = System.currentTimeMillis();
                int n = 10 * mul;
                RegexBenchmark.getBenchmark(args[2].charAt(0), n, regex, input);
                mul *= 10;
                long timeEnd = System.currentTimeMillis();
                System.out.println(n + " runs of the program with parameters: [ " + regex + ", " + input + " ]\n"
                        + "Total time: " + (timeEnd - timeStart) + "ms. \nAverage time: " + ((double) (timeEnd - timeStart) / n) + "ms.");
            }
        } else {
            try {
                long timeStart = System.currentTimeMillis();
                RegexBenchmark.getBenchmark(args[2].charAt(0), Long.parseLong(args[3]), regex, input);
                long timeEnd = System.currentTimeMillis();
                System.out.println("1 run of the program with parameters: [ " + regex + ", " + input + " ]\n"
                        + "Total time: " + (timeEnd - timeStart) + "ms.");
            } catch (Exception e) {
                System.out.println("Fourth argument must be an integer!");
            }
        }
    }

    /**
     * @param regex
     * @param input
     */
    private static void runProgram(String regex, String input) {
        String preprocessed = RegexStringPreprocessor.parseInput(regex);
        NFAState start = new NFAConstructor().constructNFA(preprocessed);
        boolean result = new NFAMatcher(start).match(input);
        System.out.println("Matching result with\n"
                + "  regex: '" + regex + "'\n"
                + "  input: '" + input + "'\n"
                + "  result: " + result);
    }

    /**
     * @param message warning message
     */
    private static void warnAndExit(String message) {
        System.out.println(message + "\n"
                + "Give arguments in form: \" regex input \" or \" 'regex' 'input' \" without the \" characters.\n"
                + "Type --help to see info.");
        System.exit(0);
    }

    private static void helpAndExit() {
        System.out.println("Give arguments in form: \" regex input \" or \" 'regex' 'input' \" without the \" characters.\n"
                + "(Empty string can be input like this '')\n"
                + "\n"
                + "Regex argument supports:\n"
                + "  Symbols:\n"
                + "    parentheses: (), for example (ab)\n"
                + "    kleenestar: *, for example a*\n"
                + "    plus: +, for example a+\n"
                + "    union: |, for example (a|b) (IMPORTANT! Expects parentheses.)\n"
                + "  Character classes (match certain type of character):\n"
                + "    any character - .\n"
                + "    digit - \\\\d\n"
                + "    alphabet - \\\\a\n"
                + "    lowercase character - \\\\l\n"
                + "    uppercase character - \\\\u\n"
                + "    alphabet, digit and _ - \\\\w\n"
                + "\n"
                + "Optional 3rd argument: test type can be provided after regex and input arguments\n"
                + "Optional 4th argument: run times can be provided to run the tests a specific number of times\n"
                + "Without 4th parameter the default mode with multiple runs is done\n"
                + "  Benchmarks:\n"
                + "    a: benchmark whole process with all prints\n"
                + "    w: benchmark whole process\n"
                + "    p: benchmark regex preprocessing\n"
                + "    c: benchmark nfa construction\n"
                + "    m: benchmark input string matching\n"
                + "  Benchmarks vs Java (important to keep in mind that my regex and Java's regex are not 100% the same):\n"
                + "    f: benchmark matching words from a file (provide file location as the input string)\n"
                + "    v: benchmark comparisons against Java Matcher.find() from a file (provide file location as the input string)\n"
                + "    r: benchmark comparisons against Java Patter.match()\n"
                + "\n"
                + "Examples:"
                + "  \"java -jar regex.jar a+ aa\" - Normal run, regex a+ with input aa. \n"
                + "  \"java -jar regex.jar a* ''\" - Normal run, regex a* with input  .\n"
                + "  \"java -jar regex.jar (a|b)* aaaaabba w\" - Benchmark the whole program, regex (a|b)* with input aaaaabba.\n"
                + "  \"java -jar regex.jar \\\\a*. abcd_ m\" - Benchmark matching, regex \\\\a*. with input abcd_.\n");
        System.exit(0);
    }

    /*TODO LIST:
    0. BUGIT: 
    true: 'abab', 'abcd', 'cdabcdab'
    false: '', 'ab', 'cd'
    //String regex = "(ab|cd)+"; //b*a* toimii
    //String input = "cdabcdabcdab";
    * sulkeet aiheuttaa bugin -> input: "", regex: b+ => false, regex: (b)+ => true 
    1. file reading mittaukset (vs java regex) 
    2. testimittaukset ja niiden esittäminen (pandas?)
    3. dokumentaatio
    4. loppu hionta kaikkeen => katso se lista
    ----------------------------------------------
    x. preprosessointi (lisä syntaksia, kuten [0-9], [a-zA-Z], \*)
    x.1 muuta regex symbolit vastaamaan lukuja => nyt kaikki \char voidaan esiprosessoida => NFAkonstruktorin ei tarvitse välittää \ merkistä
    y. paremmat kommentit nfa konstruktoriin + siistiminen
     */
}
