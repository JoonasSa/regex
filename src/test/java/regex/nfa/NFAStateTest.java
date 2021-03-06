package test.java.regex.nfa;

import main.java.regex.nfa.NFAState;
import main.java.regex.util.StateType;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;


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
    public void getLatestArrow1() {
        NFAState s = new NFAState('a');
        NFAState a = s.getLatestArrow();
        assertEquals(null, a);
    }
    
    @Test
    public void getLatestArrow2() {
        NFAState s = new NFAState('a');
        NFAState b = new NFAState('b');
        s.setNext(b);
        NFAState a = s.getLatestArrow();
        assertEquals(a.symbol, b.symbol);
    }
    
    @Test
    public void getLatestArrow3() {
        NFAState s = new NFAState('a');
        s.setNext(new NFAState('c'));
        NFAState b = new NFAState('b');
        s.setNext(b);
        NFAState a = s.getLatestArrow();
        assertEquals(a.symbol, b.symbol);
    }
    
    @Test
    public void removeLatestArrow1() {
        NFAState s = new NFAState('a');
        s.setNext(new NFAState('b'));
        s.removeLatestArrow();
        assertEquals(null, s.arrowA);
    }
    
    @Test
    public void removeLatestArrow2() {
        NFAState s = new NFAState('a');
        NFAState b = new NFAState('b');
        s.setNext(b);
        s.setNext(new NFAState('c'));
        s.removeLatestArrow();
        assertEquals(b, s.arrowA);
    }
    
    @Test
    public void removeLatestArrow3() {
        NFAState s = new NFAState('a');
        NFAState b = new NFAState('b');
        s.setNext(b);
        s.setNext(new NFAState('c'));
        s.removeLatestArrow();
        s.removeLatestArrow();
        assertEquals(null, s.arrowA);
    }
    
    @Test
    public void toStringTest() {
        NFAState s = new NFAState(StateType.START, 'ε');
        s.setNext(new NFAState('a'));
        assertEquals("Type: START, symbol: ε | A: Type: NORMAL, symbol: a", s.toString());
    }
    
}
