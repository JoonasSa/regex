
package regex;

import regex.nfa.*;
import regex.input.RegexStringPreprocessor;

public class Regex {

    /**
     * @author Joonas Sarapalo
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        //preprocess regex string
        //String regex = RegexStringPreprocessor.parseInput(input);
        //consruct nfa from processed regex string
        NFAState start = new NFAConstructor().constructNFA("a*");
        //match input string on the nfa created from regex string
        boolean result = new NFAMatcher(start).match("aaa");
        System.out.println("The input string match was: " + result);
    }
    
    //TODO LIST: sulkeet, preprosessointi (lisä syntaksia, kuten [0-9], [a-zA-Z], \*), NFA -> DFA
    
}
