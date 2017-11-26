
package regex.input;

import org.junit.Test;
import static org.junit.Assert.*;

public class RegexStringPreprocessorTest {
 
    @Test
    public void simpleRegexIsNotChangedTest() {
        String regex = RegexStringPreprocessor.parseInput("abc");
        assertEquals("abc", regex);
    }
    
    @Test
    public void wildCardCharactersAreChangedTest() {
        String regex = RegexStringPreprocessor.parseInput(".a.");
        assertEquals(new String(new char[]{0,'a',0}), regex);
    }
}
