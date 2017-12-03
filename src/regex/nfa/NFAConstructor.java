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
        NFAState current;
        while (regex.hasNextChar()) {
            char c = regex.peekNextChar();
            //System.out.println("character: " + c);
            if (CharacterClassifier.isRegexSymbol(c)) {
                prev = handleRegexSymbol(componentStart, prev, regex, regex.getNextChar());
            } else {
                switch (c) {
                    case '(':
                        NFAState substringStart = new NFAState('ε');
                        NFAState[] states = recursiveBuild(substringStart, substringStart, regex.getExpression());
                        prev.setNext(states[0]);
                        //System.out.println("recursive prev: " + prev);
                        prev = states[1];
                        //System.out.println("recursive current: " + states[1]);
                        break;
                    default:
                        //System.out.println("default");
                        current = new NFAState(regex.getNextChar());
                        prev.setNext(current);
                        //System.out.println("prev: " + prev);
                        prev = current;
                        break;
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

    /**
     * @param componentStart first state of the part currently under
     * construction
     * @param prev the previous NFAState
     * @return last state of the constructed NFA kleene star part
     */
    private NFAState kleeneStar(NFAState componentStart, NFAState prev) {
        //System.out.println("componentStart before getCopy: " + componentStart); //is epsilon with no connections
        NFAState starFirst = componentStart.getCopy(); //is right?
        NFAState starLast = new NFAState('ε');
        //System.out.println("componentStart after getCopy: " + componentStart);
        componentStart.symbol = 'ε';
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

    /**
     * @param componentStart first state of the part currently under
     * construction
     * @param prev the previous NFAState
     * @return last state of the constructed NFA plus part
     */
    private NFAState plus(NFAState componentStart, NFAState prev) {
        NFAState starLast = new NFAState('ε');
        prev.setNext(starLast);
        prev.setNext(componentStart);
        return starLast;
    }
    /*
    //System.out.println("componentStart before getCopy: " + componentStart); //is epsilon with no connections
        NFAState plusFirst = componentStart.getCopy(); //is right?
        NFAState plusLast = new NFAState('ε');
        //System.out.println("componentStart after getCopy: " + componentStart);
        componentStart.symbol = 'ε';
        componentStart.arrowA = null;
        componentStart.arrowB = null;
        componentStart.setNext(plusFirst);
        componentStart.setNext(plusLast);
        if (componentStart != prev) {
            prev.setNext(plusFirst);
            prev.setNext(plusLast);
        }
        kleeneStar(plusFirst, plusLast);
        prev.name = "plusPrev";
        componentStart.name = "componentStart";
        plusFirst.name = "plus first";
        plusLast.name = "plus last";
        System.out.println("componentStart: " + componentStart);
        System.out.println("prev: " + prev);
        System.out.println("plusFirst: " + plusFirst);
        System.out.println("plusLast: " + plusLast);
        return plusLast;
     */

    /**
     * @param componentStart first state of the part currently under
     * construction
     * @param prev the previous NFAState
     * @param regex the input regex
     * @return last state of the constructed NFA union part
     */
    private NFAState union(NFAState componentStart, NFAState prev, RegexSubstring regex) {
        System.out.println("union componentStart: " + componentStart);
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
