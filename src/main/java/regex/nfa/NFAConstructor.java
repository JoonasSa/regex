package main.java.regex.nfa;


import main.java.regex.input.RegexSubstring;
import main.java.regex.util.CharacterClassifier;
import main.java.regex.util.StateType;

public class NFAConstructor {

    /**
     * @param input regex
     * @return first state of the NFA
     */
    public NFAState constructNFA(String input) {
        NFAState start = new NFAState(StateType.START, 'ε');
        NFAState epsilon = new NFAState(StateType.NORMAL, 'ε');
        start.setNext(epsilon);
        NFAState prev = recursiveBuild(epsilon, epsilon, new RegexSubstring(input))[1];
        prev.setNext(new NFAState(StateType.END, 'ε'));
        return start;
    }

    /**
     * @param componentStart first state of the part currently under
     * construction
     * @param prev the previous NFAState
     * @param regex the input regex
     * @return NFAState array [first, last] states of the sub nfa
     */
    private NFAState[] recursiveBuild(NFAState componentStart, NFAState prev, RegexSubstring regex) {
        NFAState current;
        while (regex.hasNextChar()) {
            char c = regex.peekNextChar();
            if (CharacterClassifier.isRegexSymbol(c)) {
                prev = handleRegexSymbol(componentStart, prev, regex, regex.getNextChar(), false);
            } else if (c == '(') {
                NFAState substringStart = new NFAState('ε');
                NFAState[] states = recursiveBuild(substringStart, substringStart, regex.getExpression());
                prev.setNext(states[0]);
                prev = states[1];
                //check if there is a special character after the parentheses
                if (regex.hasNextChar()) {
                    if (CharacterClassifier.isRegexSymbol(regex.peekNextChar())) {
                        prev = handleRegexSymbol(substringStart, prev, regex, regex.getNextChar(), true);
                    }
                }
            } else {
                current = new NFAState(regex.getNextChar());
                prev.setNext(current);
                prev = current;
            }
        }
        return new NFAState[]{componentStart, prev};
    }

    /**
     * @param componentStart first state of the part currently under
     * construction
     * @param prev the previous NFAState
     * @param regex the input regex
     * @param c current character from the input string
     * @return last state of the constructed NFA part
     */
    private NFAState handleRegexSymbol(NFAState componentStart, NFAState prev, RegexSubstring regex, char c, boolean afterParentheses) {
        switch (c) {
            case '|':
                prev = union(componentStart, prev, regex);
                break;
            case '*':
                if (afterParentheses) {
                    prev = kleeneStar(componentStart, prev, false);
                } else {
                    prev = kleeneStar(prev, prev, true);
                }
                break;
        }
        return prev;
    }

    /**
     * @param componentStart first state of the part currently under
     * construction
     * @param prev the previous NFAState
     * @return last state of the constructed NFA kleenestar part
     */
    private NFAState kleeneStar(NFAState componentStart, NFAState prev, boolean singleChar) {
        NFAState starFirst = componentStart.getCopy();
        NFAState starLast = new NFAState('ε');
        componentStart.symbol = 'ε';
        componentStart.arrowA = null;
        componentStart.arrowB = null;
        componentStart.setNext(starFirst);
        componentStart.setNext(starLast);
        if (singleChar) {
            starFirst.setNext(starLast);
            starLast.setNext(starFirst);
        } else {
            NFAState extra = new NFAState('ε');
            extra.setNext(starFirst);
            extra.setNext(starLast);
            prev.setNext(extra);
        }
        return starLast;
    }

    /**
     * @param componentStart first state of the part currently under
     * construction
     * @param prev the previous NFAState
     * @param regex the input regex
     * @return last state of the constructed NFA union part
     */
    private NFAState union(NFAState componentStart, NFAState prev, RegexSubstring regex) {
        NFAState unionFirst = new NFAState('ε');
        NFAState unionA = componentStart.getLatestArrow();
        componentStart.removeLatestArrow();
        componentStart.setNext(unionFirst);
        //Union 1. split
        unionFirst.setNext(unionA);
        NFAState unionB = new NFAState('ε');
        //Union 2. split
        unionFirst.setNext(unionB);
        NFAState unionBLast = recursiveBuild(unionB, unionB, regex)[1];
        //Link both union sides to a union end state
        NFAState unionLast = new NFAState('ε');
        prev.setNext(unionLast);
        unionBLast.setNext(unionLast);
        return unionLast;
    }

}
