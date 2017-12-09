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
        String regex = "ab+";
        String input = "abbbb";
        //boolean result = new RegexBenchmark().getBenchmark('a', 10, regex, input);
        //System.out.println("\nThe input string match was: " + result);
        
        //preprocess regex string
        regex = RegexStringPreprocessor.parseInput(regex);
        System.out.println("regex: " + regex);
        //consruct nfa from processed regex string
        NFAState start = new NFAConstructor().constructNFA(regex); //abcde*|c
        //match input string on the nfa created from regex string
        boolean result = new NFAMatcher(start).match(input);
        System.out.println("The input string match was: " + result);
        
    }

    /*TODO LIST:
    0. kleene star bugit ja plus
    2. preprosessointi (lisä syntaksia, kuten [0-9], [a-zA-Z], \*)
    2.1 muuta regex symbolit vastaamaan lukuja => nyt kaikki \char voidaan esiprosessoida => NFAkonstruktorin ei tarvitse välittää \ merkistä
    3. komentoriviltä ajettava ohjelma
    4. testimittaukset ja niiden esittäminen (pandas?)
    x. paremmat kommentit nfa konstruktoriin + siistiminen
     */
}
