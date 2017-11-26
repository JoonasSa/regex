
package regex;

import regex.nfa.*;
import regex.input.RegexStringPreprocessor;

public class Regex {

    /**
     * @author Joonas Sarapalo
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        String regex = ".";
        String input = "a";
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
    0. kleene star (toimii nyt nii kuin kaikki ennen * olevat merkit olisi sulkeissa) ja plus
    => ongelma se ettei algo nyt osaa tulkita oikein missä on komponenttien alut millekin erikoismerkille => fix
    1. sulkeet
    2. preprosessointi (lisä syntaksia, kuten [0-9], [a-zA-Z], \*)
    3. paremmat kommentit nfa konstruktoriin
    x. NFA -> DFA
    */
}
