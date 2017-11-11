
package regex.input;

public class InputString {
    private String input;
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
    
    public char peekNextChar() {
        if (index == input.length())
            throw new IllegalStateException("Trying to peek index out of bounds");
        return input.charAt(index);
    }
}
