
package regex.input;

public class RegexSubstring {
    private final String input;
    private int index;
    
    public RegexSubstring(String i) {
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
    
    public char peekNextChar() {
        if (index == input.length())
            throw new IllegalStateException("Trying to peek index out of bounds");
        return input.charAt(index);
    }
    
    /**
     * @return InputString that contains the next nested regex expression i.e. (a(b)c)* returns new InputString("a(b)c")
     */
    public RegexSubstring getExpression() {
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
        return new RegexSubstring(expression);
    }

}
