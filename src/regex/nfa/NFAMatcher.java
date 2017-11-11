
package regex.nfa;

import java.util.LinkedList;
import java.util.Queue;

public class NFAMatcher {
    
    private Queue<NFAState> queue;
    
    public NFAMatcher(NFAState start) {
        this.queue = new LinkedList<>();
        queue.add(start);
    }
    
    public boolean match(String input) {
        /*
        1. To start, enqueue the first state to active states queue Q.
        2. Whenever the machine reads an input character c, dequeue all the states from Q one by one. 
        If a dequeued state X has an outgoing arrow(s) labeled c add the state(s) at the end of the arrow(s) to Q.
        If there is a blank arrow leading from state X to Y, then whenever X is enqueued to Q, also enqueue Y to Q.
        3. If there are still more characters in the input string return to 2 reading the next char c.
        4. If one of the dequeued states was the final state and the input string has been read then the input string is matched.
        Otherwise the string is not matched.
        */
        
        for (int i = 0; i < input.length(); i++) {
            
        }
        return false;
    }
}
