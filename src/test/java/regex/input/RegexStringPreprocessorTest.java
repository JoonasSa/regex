
package regex.input;

import main.java.regex.input.RegexStringPreprocessor;
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
        assertEquals(new String(new char[]{0, 'a', 0}), regex);
    }

    @Test
    public void classDCharactersAreChangedTest() {
        String regex = RegexStringPreprocessor.parseInput("\\da\\d");
        assertEquals(new String(new char[]{1, 'a', 1}), regex);
    }
    
    @Test
    public void classACharactersAreChangedTest() {
        String regex = RegexStringPreprocessor.parseInput("\\aa\\a");
        assertEquals(new String(new char[]{2, 'a', 2}), regex);
    }
    
    @Test
    public void classLCharactersAreChangedTest() {
        String regex = RegexStringPreprocessor.parseInput("\\la\\l");
        assertEquals(new String(new char[]{3, 'a', 3}), regex);
    }
    
    @Test
    public void classUCharactersAreChangedTest() {
        String regex = RegexStringPreprocessor.parseInput("\\ua\\u");
        assertEquals(new String(new char[]{4, 'a', 4}), regex);
    }
    
    @Test
    public void classWCharactersAreChangedTest() {
        String regex = RegexStringPreprocessor.parseInput("\\wa\\w");
        assertEquals(new String(new char[]{5, 'a', 5}), regex);
    }
    
    @Test
    public void transformingPlusToStarTest1() {
        String regex = RegexStringPreprocessor.parseInput("a+");
        assertEquals("aa*", regex);
    }
    
    @Test
    public void transformingPlusToStarTest2() {
        String regex = RegexStringPreprocessor.parseInput("(ab)+");
        assertEquals("ab(ab)*", regex);
    }
    
    @Test
    public void transformingPlusToStarTest3() {
        String regex = RegexStringPreprocessor.parseInput("((a)+)+");
        assertEquals("aa*(aa*)*", regex);
    }
    
    @Test
    public void transformingPlusToStarTest4() {
        String regex = RegexStringPreprocessor.parseInput("(((a)+)+)+");
        assertEquals("aa*(aa*)*(aa*(aa*)*)*", regex);
    }
    
    @Test
    public void transformingPlusToStarTest5() {
        String regex = RegexStringPreprocessor.parseInput("(ab|cd)+");
        assertEquals("(ab|cd)(ab|cd)*", regex);
    }
    
}
