
package regex.input;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class InputStringTest {
    
    public InputString input;
    
    @Before
    public void beforeTest() {
        input = new InputString("abc");
    }
    
    @Test
    public void getNextCharTest1() {
        assertEquals('a', input.getNextChar());
    }
    
    @Test
    public void getNextCharTest2() {
        input.getNextChar();
        assertEquals('b', input.getNextChar());
    }
    
    @Test
    public void getNextCharTest3() {
        input.getNextChar();
        input.getNextChar();
        assertEquals('c', input.getNextChar());
    }
    
    @Test
    public void peekNextCharTest() {
        assertEquals('a', input.peekNextChar());
    }
    
    @Test
    public void hasNextCharTest1() {
        assertTrue(input.hasNextChar());
    }
    
    @Test
    public void hasNextCharTest2() {
        input.getNextChar();
        input.getNextChar();
        input.getNextChar();
        assertFalse(input.hasNextChar());
    }
    
    @Test(expected = IllegalStateException.class)  
    public void getNextCharThrowsExceptionTest() {
        input.getNextChar();
        input.getNextChar();
        input.getNextChar();
        input.getNextChar();
    }
    
    @Test(expected = IllegalStateException.class)  
    public void peekNextCharThrowsExceptionTest() {
        input.getNextChar();
        input.getNextChar();
        input.getNextChar();
        input.peekNextChar();
    }
}
