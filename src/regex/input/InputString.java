
package regex.input;

public class InputString {
    private String input;
    private int index;
    
    public InputString(String i) {
        this.input = i;
        this.index = 0;
    }
    
    public char getNextChar() {
        return input.charAt(index++);
    }
}
