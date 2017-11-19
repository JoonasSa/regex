
package regex;

import org.junit.Test;
import static org.junit.Assert.*;
import regex.input.RegexStringPreprocessor;
import regex.nfa.NFAConstructor;
import regex.nfa.NFAMatcher;
import regex.nfa.NFAState;

public class RegexTest {
    
    private boolean runWholeProgram(String regex, String input) {
        regex = RegexStringPreprocessor.parseInput(regex);
        NFAState start = new NFAConstructor().constructNFA(regex);
        return new NFAMatcher(start).match(input);
    }
    
    //Simple inputs
    @Test
    public void simpleInputTrue1() {
        assertTrue(runWholeProgram("abcde", "abcde"));
    }
    
    @Test
    public void simpleInputTrue2() {
        assertTrue(runWholeProgram("lksdfljlsj", "lksdfljlsj"));
    }
    
    @Test
    public void simpleInputTrue3() {
        assertTrue(runWholeProgram("isfsafisaof", "isfsafisaof"));
    }
    
    @Test
    public void simpleInputTrue4() {
        assertTrue(runWholeProgram(" ", " "));
    }
    
    @Test
    public void simpleInputFalse1() {
        assertFalse(runWholeProgram("abcd", "abcde"));
    }
    
    @Test
    public void simpleInputFalse2() {
        assertFalse(runWholeProgram("lksdjlsj", "lksdfljlsj"));
    }
    
    @Test
    public void simpleInputFalse3() {
        assertFalse(runWholeProgram("isfsafiösaoh", "isfsafiösaof"));
    }
    
    @Test
    public void simpleInputFalse4() {
        assertFalse(runWholeProgram("a", ""));
    }
    
    //Union inputs
    @Test
    public void unionInputTrue1() {
        assertTrue(runWholeProgram("a|b", "a"));
    }
    
    @Test
    public void unionInputTrue2() {
        assertTrue(runWholeProgram("a|b", "b"));
    }
    
    @Test
    public void unionInputTrue3() {
        assertTrue(runWholeProgram("a|b|c", "c"));
    }
    
    @Test
    public void unionInputTrue4() {
        assertTrue(runWholeProgram("a|b|c|aa|bb|cc", "bb"));
    }
    
    @Test
    public void unionInputFalse1() {
        assertFalse(runWholeProgram("a|b", ""));
    }
    
    @Test
    public void unionInputFalse2() {
        assertFalse(runWholeProgram("a|b", "ab"));
    }
    
    @Test
    public void unionInputFalse3() {
        assertFalse(runWholeProgram("a|b|c", "d"));
    }
    
    @Test
    public void unionInputFalse4() {
        assertFalse(runWholeProgram("a|b|c|aa|bb|cc", "ab"));
    }
}
