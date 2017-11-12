
package regex.nfa;

import regex.input.InputString;
import regex.util.StateType;

public class NFAConstructor {
    
    private static InputString regex;
    
    /**
     * @param input regex
     * @return first state of the NFA
     */
    public NFAState constructNFA(String input) {
        System.out.println("input: " + input);
        regex = new InputString(input);
        NFAState start = new NFAState(StateType.START, 'ε');
        NFAState end = new NFAState(StateType.END, 'ε');
        NFAState prev = recursiveBuild(start);
        prev.setNext(end);
        return start;
    }
    
    //make this recursive, should be able to link multiple states to end state
    private NFAState recursiveBuild(NFAState prev) {
        while (regex.hasNextChar()) {
            char currentChar = regex.getNextChar();
            //if (currentChar == '.') {
            //    handleStar(prev);
            //}
            NFAState current = new NFAState(currentChar);
            prev.setNext(current);
            prev = current;
        }
        return prev;
    }

    //this should be done recursively, this approach doesn't work
    private void handleStar(NFAState prev) {
        NFAState start = new NFAState(regex.getNextChar());
        prev.setNext(start);
        prev = start;
        while (regex.peekNextChar() != '*') {
            NFAState current = new NFAState(regex.getNextChar());
            prev.setNext(current);
            prev = current;
        }
        prev.setNext(start);
        System.out.println(prev);
        regex.getNextChar(); //move past *
        regex.getNextChar(); //move past .
    }

}
