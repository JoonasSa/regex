package main.java.regex.datastructure;

import main.java.regex.nfa.NFAState;

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
        if (isFull()) {
            increaseSize();
        }
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

    /**
     * @return is the queue full
     */
    private boolean isFull() {
        return (tail - 1 == head || (head == queue.length && tail == 0));
    }

    /**
     * replace current queue with bigger one with same contents
     */
    private void increaseSize() {
        NFAState[] biggerQueue = new NFAState[queue.length * 2];
        biggerQueue[0] = queue[tail];
        int index = 1;
        for (int i = tail + 1;;i++) {
            if (i == queue.length) {
                i = 0;
            }
            biggerQueue[index] = queue[i];
              index++;
            if (i == tail) {
                break;
            }
        }
        tail = 0;
        head = queue.length;
        this.queue = biggerQueue;
    }

    /**
     * @return queue head, tail, size
     */
    @Override
    public String toString() {
        return "head: " + head + ", tail: " + tail + ", size: " + queue.length;
    }
}
