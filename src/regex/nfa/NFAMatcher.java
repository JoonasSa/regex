package regex.nfa;

import java.util.LinkedList;
import java.util.Queue;
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
    
    private Queue<NFAState> queue;
    
    public NFAMatcher(NFAState start) {
        this.queue = new LinkedList<>();
        this.queue.add(start);
    }
    
    public boolean match(String input) {
        for (int i = 0; i < input.length(); i++) {
            System.out.println("char: " + input.charAt(i));
            queue.add(new NFAState(StateType.QUEUE_END));
            while (true) {
                NFAState current = queue.poll();
                if (current.type == StateType.QUEUE_END) {
                    break;
                }
                handleNFAState(current, input.charAt(i));
            }
        }
        while (!queue.isEmpty()) {
            if (queue.poll().type == StateType.END) {
                return true;
            }
        }
        return false;
    }
    
    private void handleNFAState(NFAState current, char c) {
        System.out.println(current);
        if (transitionSymbol(current.arrowA, c)) {
            NFAState next = current.arrowA;
            queue.add(next);
            if (transitionEpsilon(next.arrowA)) {
                queue.add(next.arrowA);
            } else if (transitionEpsilon(next.arrowB)) {
                queue.add(next.arrowB);
            }
        } else if (transitionSymbol(current.arrowB, c)) {
            NFAState next = current.arrowB;
            queue.add(next);
            if (transitionEpsilon(next.arrowA)) {
                queue.add(next.arrowA);
            } else if (transitionEpsilon(next.arrowB)) {
                queue.add(next.arrowB);
            }
        }
    }
    
    private boolean transitionSymbol(NFAState arrow, char c) {
        if (arrow == null) {
            return false;
        }
        return arrow.symbol == c;
    }
    
    private boolean transitionEpsilon(NFAState arrow) {
        if (arrow == null) {
            return false;
        }
        return arrow.symbol == 'ε';
    }
}
