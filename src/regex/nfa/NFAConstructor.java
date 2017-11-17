package regex.nfa;

import regex.input.RegexSubstring;
import regex.util.CharacterClassifier;
import regex.util.StateType;

public class NFAConstructor {

    private static RegexSubstring regex;

    /**
     * @param input regex
     * @return first state of the NFA
     */
    public NFAState constructNFA(String input) {
        System.out.println("input: " + input);
        NFAState start = new NFAState(StateType.START, 'ε');
        NFAState end = new NFAState(StateType.END, 'ε');
        NFAState prev = recursiveBuild(start, new RegexSubstring(input));
        prev.setNext(end);
        return start;
    }

    //should be able to link multiple states to end state
    /**
     * @param input regex
     * @return first state of the NFA
     */
    private NFAState recursiveBuild(NFAState prev, RegexSubstring regex) {
        NFAState current;
        while (regex.hasNextChar()) {
            if (regex.peekNextChar() == '(') {
                current = recursiveBuild(prev, regex.getExpression());
            } else {
                Character symbol = regex.getRegexSymbol();
                if (symbol != null) {
                    current = kleeneStar(prev, regex);
                } else {
                    //this is probaply not right => think about it
                    current = new NFAState(regex.getNextChar());
                    prev.setNext(current);
                }
            }
            prev = current;
        }
        return prev;
    }

    //doesn't work
    private NFAState kleeneStar(NFAState prev, RegexSubstring regex) {
        NFAState starFirst = new NFAState('ε');
        prev.setNext(starFirst);
        recursiveBuild(starFirst, regex);
        NFAState starLast = new NFAState('ε');
        prev.setNext(starLast);
        return starLast;
    }

}
