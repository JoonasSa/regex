
package regex.nfa;

import java.lang.reflect.Method;
import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import regex.util.StateType;

public class NFAMatcherTest {

    public NFAMatcher matcher;
    
    @Before
    public void beforeClass() {
        NFAState start = new NFAConstructor().constructNFA("abc");
        matcher = new NFAMatcher(start);
    }
    
    @Test
    public void matchesCorrectInput() {
        assertTrue(matcher.match("abc"));
    }
    
    @Test
    public void doesNotmatchIncorrectInput() {
        assertFalse(matcher.match("cba"));
    }
    
    //TODO Test private methods with reflection
    /*@Test
    public void transitionSymbolTest() {
        Method method = NFAMatcher.getDeclaredMethod(new Method(NFAMatcher.class.getMethod("transitionSymbol", new NFAState(StateType.NORMAL, 'a'))), NFAMatcher.class);
        method.setAccessible(true);
        assertEquals(method.invoke(targetObject, argObjects));
    }*/
}
