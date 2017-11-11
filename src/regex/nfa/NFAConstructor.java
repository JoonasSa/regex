
package regex.nfa;

import regex.input.InputString;
import regex.util.StateType;

public class NFAConstructor {
    
    //star = rekursio handleStar
    private static InputString regex;
    
    public NFAState constructNFA(String input) {
        System.out.println("input: " + input);
        regex = new InputString(input);
        NFAState start = new NFAState(StateType.START, (char) 0);
        NFAState end = new NFAState(StateType.END, (char) 0);
        NFAState prev = recursiveBuild(start);
        System.out.println(prev);
        prev.setNext(end);
        return start;
    }
    
    private NFAState recursiveBuild(NFAState prev) {
        while (regex.hasNextChar()) {
            char currentChar = regex.getNextChar();
            if (currentChar == '.') {
                handleStar(prev);
            }
            NFAState current = new NFAState(currentChar);
            prev.setNext(current);
            prev = current;
        }
        return prev;
    }

    //this should be done recursively, though it doesn't work atm
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
