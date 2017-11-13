
package regex.input;

import java.util.Stack;
import regex.util.CharacterClassifier;

public class InputString {
    private final String input;
    private int index;
    
    public InputString(String i) {
        this.input = i;
        this.index = 0;
    }
    
    public boolean hasNextChar() {
        return index < input.length();
    }
    
    public char getNextChar() {
        if (index == input.length())
            throw new IllegalStateException("Trying to get index out of bounds");
        return input.charAt(index++);
    }
    
    public InputString getExpression() {
        Stack<Character> stack = new Stack<>();
        String expression = "";
        this.index++; //move past first parentheses
        int leftParentheses = 1; //therefore increase leftParentheses by one
        while (this.hasNextChar()) {
            char c = this.getNextChar();
            if (c == '(') {
                leftParentheses++;
            } else if (c == ')') {
                leftParentheses--;
            }
            if (leftParentheses < 0) {
                throw new IllegalStateException("Invalid parentheses.");
            }
            if (leftParentheses == 0) {
                break;
            }
            expression += c;
        }
        if (leftParentheses != 0) {
            throw new IllegalStateException("Invalid parentheses.");
        }
        //parentheses might have a special symbol after them
        if (this.hasNextChar()) {
            if (CharacterClassifier.isRegexCharacter(this.peekNextChar())) {
                expression += this.getNextChar();
            }
        }
        return new InputString(expression);
    }
    
    public char peekNextChar() {
        if (index == input.length())
            throw new IllegalStateException("Trying to peek index out of bounds");
        return input.charAt(index);
    }
}
