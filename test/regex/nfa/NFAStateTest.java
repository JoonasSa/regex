
package regex.nfa;

import org.junit.Test;
import static org.junit.Assert.*;
import regex.util.StateType;

public class NFAStateTest {

    @Test
    public void constructorTest1() {
        NFAState s = new NFAState('a');
        assertEquals(s.type, StateType.NORMAL);
    }
    
    @Test
    public void constructorTest2() {
        NFAState s = new NFAState(StateType.START, 'a');
        assertEquals(s.type, StateType.START);
    }
    
    @Test
    public void getCopyTest1() {
        NFAState s = new NFAState('a');
        NFAState a = s.getCopy();
        assertNotEquals(s, a);
    }
    
    @Test
    public void getCopyTest2() {
        NFAState s = new NFAState('a');
        NFAState a = s.getCopy();
        assertEquals(s.arrowA, a.arrowA);
    }
    
    @Test
    public void getCopyTest3() {
        NFAState s = new NFAState('a');
        NFAState a = s.getCopy();
        assertEquals(s.arrowB, a.arrowB);
    }
    
    @Test
    public void getCopyTest4() {
        NFAState s = new NFAState('a');
        NFAState a = s.getCopy();
        assertEquals(s.type, a.type);
    }
    
    @Test
    public void getCopyTest5() {
        NFAState s = new NFAState('a');
        NFAState a = s.getCopy();
        assertEquals(s.symbol, a.symbol);
    }
    
    @Test
    public void toStringTest() {
        NFAState s = new NFAState(StateType.START, 'ε');
        s.setNext(new NFAState('a'));
        assertEquals("Type: START, symbol: ε | A: Type: NORMAL, symbol: a", s.toString());
    }
    
}
