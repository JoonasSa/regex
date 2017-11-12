
package regex.input;

import org.junit.Test;
import static org.junit.Assert.*;

public class RegexStringPreprocessorTest {
 
    @Test
    public void simpleRegexIsNotChanged() {
        String regex = RegexStringPreprocessor.parseInput("abc");
        assertEquals("abc", regex);
    }
}
