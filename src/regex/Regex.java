package regex;

import regex.benchmark.RegexBenchmark;
import regex.nfa.*;
import regex.input.RegexStringPreprocessor;

public class Regex {

    /**
     * @author Joonas Sarapalo
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        String regex = "(abcdefghi|abcdefghij)";
        String input = "abcdefghij";

        //benchmarks
        runBenchmarkTests('w', 1000, regex, input);
        
        //program
        //runProgram(regex, input);
    }
    
    private static void runProgram(String regex, String input) {
        //preprocess regex string
        regex = RegexStringPreprocessor.parseInput(regex);
        //consruct nfa from processed regex string
        NFAState start = new NFAConstructor().constructNFA(regex); //abcde*|c
        //match input string on the nfa created from regex string
        boolean result = new NFAMatcher(start).match(input);
        System.out.println("The input string match was: " + result);
    }
    
    private static void runBenchmarkTests(char type, long times, String regex, String input) {
        int mul = 1;
        for (int i = 0; i < 5; i++) {
            boolean result = new RegexBenchmark().getBenchmark(type, times * mul, regex, input);
            System.out.println("\nThe input string match was: " + result);
            mul *= 10;
        }
    }

    /*TODO LIST:
    0. BUGIT: 
    * sulkeet aiheuttaa bugin -> input: "", regex: b+ => false, regex: (b)+ => true 
    * + ei prosessoida oikein jos niitä nestataan => rekursiivinen funktio tarpeen
    1. testimittaukset ja niiden esittäminen (pandas?)
    2. komentoriviltä ajettava ohjelma
    3. preprosessointi (lisä syntaksia, kuten [0-9], [a-zA-Z], \*)
    3.1 muuta regex symbolit vastaamaan lukuja => nyt kaikki \char voidaan esiprosessoida => NFAkonstruktorin ei tarvitse välittää \ merkistä
    x. paremmat kommentit nfa konstruktoriin + siistiminen
     */
}
