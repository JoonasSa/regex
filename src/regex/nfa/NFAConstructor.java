package regex.nfa;

import regex.input.RegexSubstring;
import regex.util.CharacterClassifier;
import regex.util.StateType;

public class NFAConstructor {

    /**
     * @param input regex
     * @return first state of the NFA
     */
    public NFAState constructNFA(String input) {
        //System.out.println("regex input: " + input);
        NFAState start = new NFAState(StateType.START, 'ε');
        NFAState epsilon = new NFAState(StateType.NORMAL, 'ε');
        //epsilon.name = "epsilon";
        start.setNext(epsilon);
        NFAState prev = recursiveBuild(epsilon, epsilon, new RegexSubstring(input))[1];
        prev.setNext(new NFAState(StateType.END, 'ε'));
        //System.out.println("epsilon: " + epsilon);
        //System.out.println("last: " + prev);
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
        boolean afterParentheses = false;
        NFAState current;
        while (regex.hasNextChar()) {
            char c = regex.peekNextChar();
            //System.out.println("character: " + c);
            if (CharacterClassifier.isRegexSymbol(c)) {
                prev = handleRegexSymbol(componentStart, prev, regex, regex.getNextChar(), afterParentheses);
                afterParentheses = false;
            } else {
                if (c == '(') {
                        NFAState substringStart = new NFAState('ε');
                        NFAState[] states = recursiveBuild(substringStart, substringStart, regex.getExpression());
                        prev.setNext(states[0]);
                        //System.out.println("recursive prev: " + prev);
                        prev = states[1];
                        //System.out.println("recursive current: " + states[1]);
                        afterParentheses = true;
                } else {
                        //System.out.println("default");
                        current = new NFAState(regex.getNextChar());
                        prev.setNext(current);
                        //System.out.println("prev: " + prev);
                        prev = current;
                        afterParentheses = false;
                }
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
        //System.out.println("kleenestart start: " + componentStart);
        NFAState starFirst = componentStart.getCopy();
        NFAState starLast = new NFAState('ε');
        componentStart.symbol = 'ε';
        componentStart.arrowA = null;
        componentStart.arrowB = null;
        componentStart.setNext(starFirst);
        componentStart.setNext(starLast);
        if (singleChar) { //not a single character kleenestar
            starFirst.setNext(starLast);
            starLast.setNext(starFirst);
        } else {
            prev.setNext(starFirst);
            prev.setNext(starLast);
        }
        /*
        prev.name = "kleeneStarPrev";
        componentStart.name = "componentStart";
        starFirst.name = "star first";
        starLast.name = "star last";
        System.out.println("componentStart: " + componentStart);
        System.out.println("prev: " + prev);
        System.out.println("starFirst: " + starFirst);
        System.out.println("starLast: " + starLast);
        */
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
        //System.out.println("union componentStart: " + componentStart);
        NFAState unionFirst = new NFAState('ε'); //Create new union start state
        NFAState unionA = componentStart.getLatestArrow();
        componentStart.removeLatestArrow();
        componentStart.setNext(unionFirst);
        unionFirst.setNext(unionA); //Union 1. split
        NFAState unionB = new NFAState('ε'); //get next char => problems with \char
        unionFirst.setNext(unionB); //Union 2. split
        NFAState unionBLast = recursiveBuild(unionB, unionB, regex)[1];
        NFAState unionLast = new NFAState('ε'); //Link both union sides to a union end state
        prev.setNext(unionLast);
        unionBLast.setNext(unionLast);
        /*
        unionA.name = "unionA";
        unionB.name = "unionB";
        unionBLast.name = "union B last";
        unionLast.name = "union last";
        System.out.println("componentStart: " + componentStart);
        System.out.println("unionA: " + unionA);
        System.out.println("unionB: " + unionB);
        System.out.println("unionBLast: " + unionBLast);
        System.out.println("unionLast: " + unionLast);
        System.out.println("union prev " + prev);
        */
        return unionLast;
    }
    
}
