
package regex.datastructure;

import regex.nfa.NFAState;

public class Queue {

    private NFAState[] queue;
    private int head;
    private int tail;
    
    public Queue(int n) {
        queue = new NFAState[n];
        head = 0;
        tail = 0;
    }
    
    /**
     * @param s NFAState to enqueue to the queue
     */
    public void enqueue(NFAState s) {
        queue[head] = s;
        head++;
        if (head == queue.length) {
            head = 0;
        }
    }
    
    /**
     * @return the oldest NFAState in the queue
     */
    public NFAState dequeue() {
        NFAState s = queue[tail];
        tail++;
        if (tail == queue.length) {
            tail = 0;
        }
        return s;
    }
    
    /**
     * @return is the queue empty
     */
    public boolean isEmpty() {
        return head == tail;
    }
}
