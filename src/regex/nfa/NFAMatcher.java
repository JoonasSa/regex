package regex.nfa;

import regex.datastructure.Queue;
import regex.util.CharacterClassifier;
import regex.util.StateType;

public class NFAMatcher {
    
    private Queue queue;
    private NFAState start;
    
    public NFAMatcher(NFAState start) {
        this.start = start;
    }
    
    /**
     * @param input string
     * @return is the input string a match with the regex
     */
    public boolean match(String input) {
        this.queue = new Queue(100);
        recursiveEpsilonTransition(start);
        for (int i = 0; i < input.length(); i++) {
            queue.enqueue(new NFAState(StateType.QUEUE_END, 'ε'));
            while (true) {
                NFAState current = queue.dequeue();
                if (current.type == StateType.QUEUE_END) {
                    break;
                }
                handleNFAState(current, input.charAt(i));
            }
        }
        while (!queue.isEmpty()) {
            NFAState s = queue.dequeue();
            if (s.type == StateType.END) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * @param current nfa state
     * @param c current character in input string
     */
    private void handleNFAState(NFAState current, char c) {
        if (transitionSymbol(current.arrowA, c)) {
            NFAState next = current.arrowA;
            queue.enqueue(next);
            recursiveEpsilonTransition(next);
        } 
        if (transitionSymbol(current.arrowB, c)) {
            NFAState next = current.arrowB;
            queue.enqueue(next);
            recursiveEpsilonTransition(next);
        }
    }
    
    /**
     * @param arrow points to connected state
     * @param c current character in input string
     * @return is valid transition
     */
    private boolean transitionSymbol(NFAState arrow, char c) {
        if (arrow == null) {
            return false;
        }
        //transition symbol is of a special character class
        if (arrow.symbol > 0 && arrow.symbol < 6) {
            switch (arrow.symbol) {
                case 1:
                    return CharacterClassifier.isClassD(c); //digit
                case 2:
                    return CharacterClassifier.isClassA(c); //alphabet
                case 3:
                    return CharacterClassifier.isClassL(c); //lowercase letter
                case 4:
                    return CharacterClassifier.isClassU(c); //uppercase letter
                case 5:
                    return CharacterClassifier.isClassW(c); //digit or alphabet or _
            }
        }
        return arrow.symbol == c || arrow.symbol == 0; //0 is wildcard
    }
    
    /**
     * @param state to simulate epsilon transitions on
     */
    private void recursiveEpsilonTransition(NFAState state) {
        if (state == null) {
            return;
        }
        if (state.arrowA != null && state.arrowA.symbol == 'ε') {
            queue.enqueue(state.arrowA);
            recursiveEpsilonTransition(state.arrowA);
        }
        if (state.arrowB != null && state.arrowB.symbol == 'ε') {
            queue.enqueue(state.arrowB);
            recursiveEpsilonTransition(state.arrowB);
        }
    }

}
