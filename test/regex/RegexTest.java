
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
    
    //Kleene star tests
    @Test
    public void kleeneStarInputTrue1() {
        assertTrue(runWholeProgram("a*", "a"));
    }
    
    @Test
    public void kleeneStarInputTrue2() {
        assertTrue(runWholeProgram("a*", "aaa"));
    }
    
    @Test
    public void kleeneStarInputTrue3() {
        assertTrue(runWholeProgram("a*", ""));
    }

    @Test
    public void kleeneStarInputTrue4() {
        assertTrue(runWholeProgram("a*b*", "aabbb"));
    }
    
    @Test
    public void kleeneStarInputTrue5() {
        assertTrue(runWholeProgram("b*a*", "aa"));
    }
    
    @Test
    public void kleeneStarInputFalse1() {
        assertFalse(runWholeProgram("a*", "b"));
    }
    
    @Test
    public void kleeneStarInputFalse2() {
        assertFalse(runWholeProgram("a*b*", "ba"));
    }
    
    @Test
    public void kleeneStarInputFalse3() {
        assertFalse(runWholeProgram("a*b*", "aba"));
    }
    
    //Plus tests
    @Test
    public void plusInputTrue1() {
        assertTrue(runWholeProgram("a+", "a"));
    }
    
    @Test
    public void plusInputTrue2() {
        assertTrue(runWholeProgram("a+", "aaa"));
    }
    
    @Test
    public void plusInputFalse1() {
        assertFalse(runWholeProgram("a+", ""));
    }
    
    @Test
    public void plusInputFalse2() {
        assertFalse(runWholeProgram("a+", "b"));
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
    
    //Complicated inputs
    @Test
    public void complicatedInputTrue1() {
        assertTrue(runWholeProgram("a*|b*", "aa"));
    }
    
    @Test
    public void complicatedInputTrue2() {
        assertTrue(runWholeProgram("a*|b*", "bbbb"));
    }
    
    @Test
    public void complicatedInputTrue3() {
        assertTrue(runWholeProgram("a*|b*|c", "c"));
    }
    
    @Test
    public void complicatedInputTrue4() {
        assertTrue(runWholeProgram("ab*|c", "abb"));
    }
    
    @Test
    public void complicatedInputTrue5() {
        assertTrue(runWholeProgram("a*b*|c*b*", "cbb"));
    }
    
    @Test
    public void complicatedInputTrue6() {
        assertTrue(runWholeProgram("a*b*|c*b*", ""));
    }

    @Test
    public void complicatedInputFalse1() {
        assertFalse(runWholeProgram("a*|b*", "c"));
    }
    
    @Test
    public void complicatedInputFalse2() {
        assertFalse(runWholeProgram("a*|b*", "bba"));
    }
    
    @Test
    public void complicatedInputFalse3() {
        assertFalse(runWholeProgram("a*|b*|c", "cc"));
    }
    //plus tests
}
