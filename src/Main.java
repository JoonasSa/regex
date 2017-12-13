
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
            System.out.println("No arguments received.\nGive arguments in form: 'regex input' without the ''.\nType --help to see info.");
            System.exit(1);
        }
        if (args[0] == null) {
            System.out.println("Missing regex.");
            System.exit(1);
        } else if (args[0].equals("--help")) {
            System.out.println("Give arguments in form: 'regex input' without the ''\n"
                    + "Regex argument supports:\n"
                    + "  Symbols:\n"
                    + "    *,+,|\n"
                    + "  Character classes:\n"
                    + "    digit (\\d)\n"
                    + "    alphabet (\\a)\n"
                    + "    lowercase character (\\l)\n"
                    + "    uppercase character (\\u)\n"
                    + "    alphabet, digit and _ (\\w)\n\n"
                    + "Optional test flag can be given after regex and input arguments\n"
                    + "Fourth optional paramater can be provided to run the tests a specific number of times\n"
                    + "Without 4. parameter the default mode with different runs is made\n"
                    + "  Test flags:\n"
                    + "    a: whole process with all prints\n"
                    + "    w: whole process\n"
                    + "    p: regex preprocessing\n"
                    + "    c: nfa construction\n"
                    + "    m: input string matching\n"
                    + "    r: comparisons against Java Patter.match()");
            System.exit(1);
        }
        if (args[1] == null) {
            System.out.println("Missing input string.");
            System.exit(1);
        }
        String regex = args[0];
        String input = args[1];
        
        switch (args.length) {
            case 2: //no test flag set
                runProgram(regex, input);
                break;
            case 3: //no specific run times argument set
                int mul = 1;
                for (int i = 0; i < 5; i++) {
                    runBenchmarkTests(args[2].charAt(0), 100, regex, input, mul);
                    mul *= 10;
                }   break;
            default:
                try {
                    runBenchmarkTests(args[2].charAt(0), Long.parseLong(args[3]), regex, input, 1);
                } catch (Exception e) {
                    System.out.println("Fourth argument must be a integer");
                    System.exit(1);
                }   break;
        }
    }
    /*
    true: 'abab', 'abcd', 'cdabcdab'
    false: '', 'ab', 'cd'
    */
    //String regex = "(ab|cd)+"; //b*a* toimii
    //String input = "cdabcdabcdab";

    private static void runProgram(String regex, String input) {
        //preprocess regex string
        regex = RegexStringPreprocessor.parseInput(regex);
        System.out.println(regex);
        //consruct nfa from processed regex string
        NFAState start = new NFAConstructor().constructNFA(regex); //abcde*|c
        //match input string on the nfa created from regex string
        boolean result = new NFAMatcher(start).match(input);
        System.out.println("The input string match was: " + result);
    }

    private static void runBenchmarkTests(char type, long times, String regex, String input, int mul) {
        boolean result = new RegexBenchmark().getBenchmark(type, times * mul, regex, input);
        System.out.println("\nThe input string match was: " + result);
    }

    /*TODO LIST:
    0. BUGIT: 
    * sulkeet aiheuttaa bugin -> input: "", regex: b+ => false, regex: (b)+ => true 
    1. testimittaukset ja niiden esittäminen (pandas?)
    2. komentoriviltä ajettava ohjelma
    3. loppu hionta kaikkeen => katso se lista
    ----------------------------------------------
    x. preprosessointi (lisä syntaksia, kuten [0-9], [a-zA-Z], \*)
    x.1 muuta regex symbolit vastaamaan lukuja => nyt kaikki \char voidaan esiprosessoida => NFAkonstruktorin ei tarvitse välittää \ merkistä
    y. paremmat kommentit nfa konstruktoriin + siistiminen
     */
}
