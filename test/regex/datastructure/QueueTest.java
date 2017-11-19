
package regex.datastructure;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import regex.nfa.NFAState;

public class QueueTest {
    
    public Queue queue;
    
    @Before
    public void beforeTest() {
        queue = new Queue(2);
    }
    
    @Test
    public void queueTest1() {
        NFAState s = new NFAState('a');
        queue.enqueue(s);
        assertEquals(s, queue.dequeue());
    }
    
    @Test
    public void queueTest2() {
        queue.enqueue(new NFAState('b'));
        NFAState s = new NFAState('a');
        queue.enqueue(s);
        assertNotEquals(s, queue.dequeue());
    }
    
    @Test
    public void queueTest3() {
        queue.enqueue(new NFAState('a'));
        queue.enqueue(new NFAState('b'));
        NFAState s = new NFAState('c');
        queue.enqueue(s);
        assertEquals(s, queue.dequeue());
    }
    
    @Test
    public void isEmptyTest1() {
        queue.enqueue(new NFAState('a'));
        assertFalse(queue.isEmpty());
    }
    
    @Test
    public void isEmptyTest2() {
        assertTrue(queue.isEmpty());
    }
}
