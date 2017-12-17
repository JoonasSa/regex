package test.java.regex;

import main.java.regex.input.RegexStringPreprocessor;
import main.java.regex.nfa.NFAConstructor;
import main.java.regex.nfa.NFAMatcher;
import main.java.regex.nfa.NFAState;
import org.junit.Test;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;

public class RegexTest {

    private boolean runWholeProgram(String regex, String input) {
        regex = RegexStringPreprocessor.parseInput(regex);
        NFAState start = new NFAConstructor().constructNFA(regex);
        return new NFAMatcher(start).match(input);
    }

    //Simple inputs
    @Test
    public void simpleInputTrue() {
        assertTrue(runWholeProgram("abcdefghijklmnopqrstuwvxyz", "abcdefghijklmnopqrstuwvxyz"));
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

    //Special character classes
    @Test
    public void wildCardInputTrue1() {
        assertTrue(runWholeProgram(".", "a"));
    }

    @Test
    public void wildCardInputTrue2() {
        assertTrue(runWholeProgram(".", "b"));
    }

    @Test
    public void wildCardInputTrue3() {
        assertTrue(runWholeProgram("..", "ab"));
    }

    @Test
    public void wildCardInputTrue4() {
        assertTrue(runWholeProgram("a.a", "aba"));
    }

    @Test
    public void wildCardInputTrue5() {
        assertTrue(runWholeProgram(".*a.*", "a"));
    }

    @Test
    public void wildCardInputTrue6() {
        assertTrue(runWholeProgram(".*a.*", "zxcxzczczxccxac"));
    }

    @Test
    public void wildCardInputFalse1() {
        assertFalse(runWholeProgram(".", ""));
    }

    @Test
    public void wildCardInputFalse2() {
        assertFalse(runWholeProgram("..", "a"));
    }

    @Test
    public void wildCardInputFalse3() {
        assertFalse(runWholeProgram(".*a.*", "kxcjsdfjslf"));
    }

    @Test
    public void wildCardInputFalse4() {
        assertFalse(runWholeProgram(".*a.*", ""));
    }

    @Test
    public void classDInputTrue() {
        assertTrue(runWholeProgram("a\\da", "a1a"));
    }

    @Test
    public void classDInputFalse() {
        assertFalse(runWholeProgram("\\d", "a"));
    }

    @Test
    public void classAInputTrue() {
        assertTrue(runWholeProgram("a\\aa", "aba"));
    }

    @Test
    public void classAInputFalse() {
        assertFalse(runWholeProgram("\\a", "1"));
    }

    @Test
    public void classLInputTrue() {
        assertTrue(runWholeProgram("a\\la", "aba"));
    }

    @Test
    public void classLInputFalse1() {
        assertFalse(runWholeProgram("\\l", "A"));
    }

    @Test
    public void classLInputFalse2() {
        assertFalse(runWholeProgram("\\l", "1"));
    }

    @Test
    public void classUInputTrue() {
        assertTrue(runWholeProgram("a\\ua", "aBa"));
    }

    @Test
    public void classUInputFalse1() {
        assertFalse(runWholeProgram("\\u", "a"));
    }

    @Test
    public void classUInputFalse2() {
        assertFalse(runWholeProgram("\\u", "1"));
    }

    @Test
    public void classWInputTrue1() {
        assertTrue(runWholeProgram("a\\wa", "aba"));
    }

    @Test
    public void classWInputTrue2() {
        assertTrue(runWholeProgram("a\\wa", "a1a"));
    }

    @Test
    public void classWInputTrue3() {
        assertTrue(runWholeProgram("a\\wa", "a_a"));
    }

    @Test
    public void classWInputTrue4() {
        assertTrue(runWholeProgram("a\\wa", "aBa"));
    }

    @Test
    public void classWInputFalse() {
        assertFalse(runWholeProgram("\\w", "-"));
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
    public void plusInputTrue3() {
        assertTrue(runWholeProgram("a+b+", "aaab"));
    }

    @Test
    public void plusInputTrue4() {
        assertTrue(runWholeProgram("b+a+", "baaa"));
    }

    @Test
    public void plusInputFalse1() {
        assertFalse(runWholeProgram("a+", ""));
    }

    @Test
    public void plusInputFalse2() {
        assertFalse(runWholeProgram("a+", "b"));
    }

    @Test
    public void plusInputFalse3() {
        assertFalse(runWholeProgram("a+b+", "b"));
    }

    @Test
    public void plusInputFalse4() {
        assertFalse(runWholeProgram("(a)+", ""));
    }

    //Union inputs
    @Test
    public void unionInputTrue1() {
        assertTrue(runWholeProgram("(a|b)", "a"));
    }

    @Test
    public void unionInputTrue2() {
        assertTrue(runWholeProgram("(a|b)", "b"));
    }

    @Test
    public void unionInputTrue3() {
        assertTrue(runWholeProgram("(a|b|c)", "c"));
    }

    @Test
    public void unionInputTrue4() {
        assertTrue(runWholeProgram("(a|b|c|aa|bb|cc)", "bb"));
    }

    @Test
    public void unionInputFalse1() {
        assertFalse(runWholeProgram("(a|b)", ""));
    }

    @Test
    public void unionInputFalse2() {
        assertFalse(runWholeProgram("(a|b)", "ab"));
    }

    @Test
    public void unionInputFalse3() {
        assertFalse(runWholeProgram("(a|b|c)", "d"));
    }

    @Test
    public void unionInputFalse4() {
        assertFalse(runWholeProgram("(a|b|c|aa|bb|cc)", "ab"));
    }

    //Complicated inputs
    @Test
    public void complicatedInputTrue1() {
        assertTrue(runWholeProgram("(a*|b*)", "aa"));
    }

    @Test
    public void complicatedInputTrue2() {
        assertTrue(runWholeProgram("(a*|b*)", "bbbb"));
    }

    @Test
    public void complicatedInputTrue3() {
        assertTrue(runWholeProgram("(a*|b*|c)", "c"));
    }

    @Test
    public void complicatedInputTrue4() {
        assertTrue(runWholeProgram("a(b*|c)", "abb"));
    }

    //Stack Overflow
    /*@Test
    public void complicatedInputTrue5() {
        assertTrue(runWholeProgram("(a*b*|c*b*)*", "acc"));
    }*/

    @Test
    public void complicatedInputTrue6() {
        assertTrue(runWholeProgram("a*(b*|c*)d*", "acccd"));
    }

    @Test
    public void complicatedInputTrue7() {
        assertTrue(runWholeProgram("\\d\\a\\l\\u\\w", "1abC_"));
    }

    @Test
    public void complicatedInputTrue8() {
        assertTrue(runWholeProgram("(\\d|b)*", "1b2b3bbbbb44"));
    }

    @Test
    public void complicatedInputTrue9() {
        assertTrue(runWholeProgram("(\\d\\w*|\\a|\\l*)", "1"));
    }

    @Test
    public void complicatedInputTrue10() {
        assertTrue(runWholeProgram("(ab)+", "abab"));
    }

    @Test
    public void complicatedInputTrue11() {
        assertTrue(runWholeProgram("(ab|cd)+", "cd"));
    }

    @Test
    public void complicatedInputTrue12() {
        assertTrue(runWholeProgram("(abcd)*(ab)*", "abcdabcd"));
    }

    @Test
    public void complicatedInputFalse1() {
        assertFalse(runWholeProgram("(a*|b*)", "bba"));
    }

    @Test
    public void complicatedInputFalse2() {
        assertFalse(runWholeProgram("(a*|b*|c)", "cc"));
    }
    
    @Test
    public void complicatedInputFalse3() {
        assertFalse(runWholeProgram("(ab|cd)+", ""));
    }
}
