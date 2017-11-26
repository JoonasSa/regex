package regex.nfa;

import regex.datastructure.Queue;
import regex.util.StateType;

public class NFAMatcher {

    /*
        1. To start, enqueue the first state to active states queue Q.
        2. Whenever the machine reads an input character c, dequeue all the states from Q one by one. 
        If a dequeued state X has an outgoing arrow(s) labeled c add the state(s) at the end of the arrow(s) to Q.
        If there is a blank arrow leading from state X to Y, then whenever X is enqueued to Q, also enqueue Y to Q.
        3. If there are still more characters in the input string return to 2 reading the next char c.
        4. If one of the dequeued states was the final state and the input string has been read then the input string is matched.
        Otherwise the string is not matched.
    */
    
    private Queue queue;
    
    public NFAMatcher(NFAState start) {
        this.queue = new Queue(100); //todo different sizes
        recursiveEpsilonTransition(start);
    }
    
    /**
     * @param input string
     * @return is the input string a match with the regex
     */
    public boolean match(String input) {
        System.out.println("input string: " + input);
        for (int i = 0; i < input.length(); i++) {
            queue.enqueue(new NFAState(StateType.QUEUE_END, 'a'));
            while (true) {
                NFAState current = queue.dequeue();
                if (current.type == StateType.QUEUE_END) {
                    break;
                }
                //System.out.println("current: " + current);
                handleNFAState(current, input.charAt(i));
            }
        }
        while (!queue.isEmpty()) {
            NFAState s = queue.dequeue();
            //System.out.println("dequeue: " + s);
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
        return arrow.symbol == c || arrow.symbol == 0; //0 is wild card
    }
    
    /**
     * @param state to simulate epsilon transitions on
     */
    private void recursiveEpsilonTransition(NFAState state) {
        if (state == null) {
            return;
        }
        //System.out.println("epsilon: " + state);
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
