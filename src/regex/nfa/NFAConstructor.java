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
        System.out.println("regex input: " + input);
        NFAState start = new NFAState(StateType.START, 'ε');
        NFAState epsilon = new NFAState(StateType.NORMAL, 'ε');
        //epsilon.name = "epsilon";
        start.setNext(epsilon);
        NFAState prev = recursiveBuild(epsilon, epsilon, new RegexSubstring(input));
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
     * @return last state of the constructed NFA part
     */
    private NFAState recursiveBuild(NFAState componentStart, NFAState prev, RegexSubstring regex) {
        while (regex.hasNextChar()) {
            char c = regex.getNextChar();
            if (CharacterClassifier.isRegexSymbol(c)) {
                prev = handleRegexSymbol(componentStart, prev, regex, c);
            } else {
                switch (c) {
                    /*
                    case '(':
                        current = recursiveBuild(new NFAState(regex.getNextChar()), prev, regex.getExpression());
                    case ')':
                        //if there is a * or + after this we need to call kleenestar with componentStart as prev
                        if (CharacterClassifier.isRegexCharacter(regex.peekNextChar())) {
                            return handleRegexSymbol(componentStart, prev, regex, regex.getNextChar());
                        }
                        return prev;
                     */
                    default:
                        NFAState current = new NFAState(c);
                        prev.setNext(current);
                        prev = current;
                        break;
                }
            }
        }
        return prev;
    }

    /**
     * @param componentStart first state of the part currently under
     * construction
     * @param prev the previous NFAState
     * @param regex the input regex
     * @param c current character from the input string
     * @return last state of the constructed NFA part
     */
    private NFAState handleRegexSymbol(NFAState componentStart, NFAState prev, RegexSubstring regex, char c) {
        switch (c) {
            case '|':
                prev = union(componentStart, prev, regex);
                break;
            case '*':
                prev = kleeneStar(componentStart, prev);
                break;
            case '+':
                prev = plus(componentStart, prev);
                break;
        }
        return prev;
    }

    //buggy doesn't work correctly
    /**
     * @param componentStart first state of the part currently under
     * construction
     * @param prev the previous NFAState
     * @return last state of the constructed NFA kleene start part
     */
    private NFAState kleeneStar(NFAState componentStart, NFAState prev) {
        NFAState starFirst = componentStart.getCopy();
        NFAState starLast = new NFAState('ε');
        componentStart.arrowA = null;
        componentStart.arrowB = null;
        componentStart.setNext(starFirst);
        componentStart.setNext(starLast);
        if (componentStart != prev) {
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

    //refactor into kleene star, doesn't work
    /**
     * @param componentStart first state of the part currently under
     * construction
     * @param prev the previous NFAState
     * @return last state of the constructed NFA kleene start part
     */
    private NFAState plus(NFAState componentStart, NFAState prev) {
        NFAState starLast = new NFAState('ε');
        prev.setNext(starLast);
        prev.setNext(componentStart);
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
        //Create new union start state
        NFAState unionA = componentStart.getCopy();
        componentStart.symbol = 'ε';
        componentStart.arrowA = null;
        componentStart.arrowB = null;
        componentStart.setNext(unionA); //Union 1. split
        NFAState unionB = new NFAState('ε');
        componentStart.setNext(unionB); //Union 2. split
        NFAState unionBLast = recursiveBuild(unionB, unionB, regex);
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
        */
        return unionLast;
    }
    
    /*
    //OLD
    //Create new union start state
        NFAState unionFirst = componentStart.getCopy();
        //Union 1. split
        NFAState startA = componentStart.arrowA; //arrowA component is already built
        componentStart.arrowA = null;
        NFAState unionA = new NFAState('ε');
        componentStart.setNext(unionA);
        unionA.setNext(startA);
        //Union 2. split
        NFAState unionB = new NFAState('ε');
        componentStart.setNext(unionB);
        NFAState unionBLast = recursiveBuild(unionB, unionB, regex);
        //Link both union sides to a union end state
        NFAState unionLast = new NFAState('ε');
        prev.setNext(unionLast); //link first union part to union end state
        unionBLast.setNext(unionLast); //link second union part to union end state
        return unionLast;
    */

}
