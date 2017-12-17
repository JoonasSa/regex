package test.java.regex.util;

import main.java.regex.util.CharacterClassifier;
import org.junit.Test;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;

public class CharacterClassifierTest {

    @Test
    public void isRegexSymbolTest1() {
        assertTrue(CharacterClassifier.isRegexSymbol('*'));
    }
    
    @Test
    public void isRegexSymbolTest2() {
        assertTrue(CharacterClassifier.isRegexSymbol('+'));
    }
    
    @Test
    public void isRegexSymbolTest3() {
        assertTrue(CharacterClassifier.isRegexSymbol('|'));
    }
    
    @Test
    public void isNotRegexSymbolTest1() {
        assertFalse(CharacterClassifier.isRegexSymbol('a'));
    }
    
    @Test
    public void isClassDTest() {
        assertTrue(CharacterClassifier.isClassD('1'));
    }
    
    @Test
    public void isNotClassDTest() {
        assertFalse(CharacterClassifier.isClassD('a'));
    }
    
    @Test
    public void isClassATest1() {
        assertTrue(CharacterClassifier.isClassA('a'));
    }
    
    @Test
    public void isClassATest2() {
        assertTrue(CharacterClassifier.isClassA('B'));
    }
    
    @Test
    public void isNotClassATest() {
        assertFalse(CharacterClassifier.isClassA('1'));
    }
    
    @Test
    public void isClassLTest() {
        assertTrue(CharacterClassifier.isClassL('a'));
    }
    
    @Test
    public void isNotClassLTest1() {
        assertFalse(CharacterClassifier.isClassL('A'));
    }
    
    @Test
    public void isNotClassLTest2() {
        assertFalse(CharacterClassifier.isClassL('1'));
    }
    
    @Test
    public void isClassUTest() {
        assertTrue(CharacterClassifier.isClassU('A'));
    }
    
    @Test
    public void isNotClassUTest1() {
        assertFalse(CharacterClassifier.isClassU('a'));
    }
    
    @Test
    public void isNotClassUTest2() {
        assertFalse(CharacterClassifier.isClassU('1'));
    }
    
    @Test
    public void isClassWTest1() {
        assertTrue(CharacterClassifier.isClassW('a'));
    }
    
    @Test
    public void isClassWTest2() {
        assertTrue(CharacterClassifier.isClassW('A'));
    }
    
    @Test
    public void isClassWTest3() {
        assertTrue(CharacterClassifier.isClassW('1'));
    }
    
    @Test
    public void isClassWTest() {
        assertTrue(CharacterClassifier.isClassW('_'));
    }
    
    @Test
    public void isNotClassWTest() {
        assertFalse(CharacterClassifier.isClassW('-'));
    }
    
}
