
package regex.util;

import org.junit.Test;
import static org.junit.Assert.*;

public class CharacterClassifierTest {

    @Test
    public void isRegexSumbolTest1() {
        assertTrue(CharacterClassifier.isRegexCharacter('*'));
    }
    
    @Test
    public void isRegexSumbolTest2() {
        assertTrue(CharacterClassifier.isRegexCharacter('+'));
    }
    
    @Test
    public void isRegexSumbolTest3() {
        assertTrue(CharacterClassifier.isRegexCharacter('|'));
    }
    
    @Test
    public void isNotRegexSumbolTest1() {
        assertFalse(CharacterClassifier.isRegexCharacter('a'));
    }
    
}
