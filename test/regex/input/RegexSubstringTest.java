
package regex.input;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class RegexSubstringTest {
    
    public RegexSubstring regex;
    
    @Before
    public void beforeTest() {
        regex = new RegexSubstring("abc");
    }
    
    @Test
    public void getNextCharTest1() {
        assertEquals('a', regex.getNextChar());
    }
    
    @Test
    public void getNextCharTest2() {
        regex.getNextChar();
        assertEquals('b', regex.getNextChar());
    }
    
    @Test
    public void getNextCharTest3() {
        regex.getNextChar();
        regex.getNextChar();
        assertEquals('c', regex.getNextChar());
    }
    
    @Test
    public void peekNextCharTest() {
        assertEquals('a', regex.peekNextChar());
    }
    
    @Test
    public void hasNextCharTest1() {
        assertTrue(regex.hasNextChar());
    }
    
    @Test
    public void hasNextCharTest2() {
        regex.getNextChar();
        regex.getNextChar();
        regex.getNextChar();
        assertFalse(regex.hasNextChar());
    }

    @Test
    public void getExpression1() {
        assertEquals('a', new RegexSubstring("(abc)").getExpression().getNextChar());
    }
    
    @Test
    public void getExpression2() {
        assertEquals('(', new RegexSubstring("((abc))").getExpression().getNextChar());
    }
    
    @Test
    public void getExpression3() {
        RegexSubstring s = new RegexSubstring("((a()b()c))").getExpression().getExpression();
        s.getNextChar();
        s.getNextChar();
        s.getNextChar();
        assertEquals('b', s.getNextChar());
    }
    
    @Test(expected = IllegalStateException.class)
    public void getExpressionException1() {
        new RegexSubstring(")").getExpression();
    }
    
    @Test(expected = IllegalStateException.class)
    public void getExpressionException2() {
        new RegexSubstring("(").getExpression();
    }
    
    @Test(expected = IllegalStateException.class)
    public void getExpressionException3() {
        new RegexSubstring("((abc)").getExpression();
    }
    
    @Test(expected = IllegalStateException.class)
    public void getNextCharThrowsExceptionTest() {
        regex.getNextChar();
        regex.getNextChar();
        regex.getNextChar();
        regex.getNextChar();
    }
    
    @Test(expected = IllegalStateException.class)
    public void peekNextCharThrowsExceptionTest() {
        regex.getNextChar();
        regex.getNextChar();
        regex.getNextChar();
        regex.peekNextChar();
    }
}
