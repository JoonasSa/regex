
package regex;

import regex.nfa.*;
import regex.input.RegexStringPreprocessor;

public class Regex {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        String input = "abc";
        String regex = RegexStringPreprocessor.parseInput(input);
        NFAState start = new NFAConstructor().constructNFA(regex);
        boolean result = new NFAMatcher(start).match(input);
        System.out.println("The input string match was: " + result);
    }
    
}
