package test.java.regex.nfa;

import main.java.regex.nfa.NFAConstructor;
import main.java.regex.nfa.NFAState;
import main.java.regex.util.StateType;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;


public class NFAConstructorTest {
    
    NFAConstructor constructor;
    
    @Before
    public void setUp() {
        constructor = new NFAConstructor();
    }

    @Test
    public void correctFirstStateType() {
        NFAState start = constructor.constructNFA("a");
        assertEquals(StateType.START, start.type);
    }
    
    @Test
    public void correctMidStateType() {
        NFAState start = constructor.constructNFA("a");
        assertEquals(StateType.NORMAL, start.arrowA.type);
    }
    
    @Test
    public void correctLastStateType() {
        NFAState start = constructor.constructNFA("a");
        assertEquals(StateType.END, start.arrowA.arrowA.arrowA.type);
    }
    
    @Test
    public void correctFirstStateSymbol() {
        NFAState start = constructor.constructNFA("a");
        assertEquals('ε', start.symbol);
    }
    
    @Test
    public void correctMidStateSymbol() {
        NFAState start = constructor.constructNFA("a");
        assertEquals('a', start.arrowA.arrowA.symbol);
    }
    
    @Test
    public void correctLastStateSymbol() {
        NFAState start = constructor.constructNFA("a");
        assertEquals('ε', start.arrowA.arrowA.arrowA.symbol);
    }

}
