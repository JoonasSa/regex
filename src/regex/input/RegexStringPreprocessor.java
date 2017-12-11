package regex.input;

public class RegexStringPreprocessor {

    /**
     * @param regex
     * @return preprocessed regex
     */
    public static String parseInput(String regex) {
        regex = convertPlusToStar(regex);
        char[] parsed = new char[regex.length()];
        int index = 0;
        for (int i = 0; i < regex.length(); i++) {
            switch (regex.charAt(i)) {
                case '\\':
                    i = backslash(regex.charAt(i + 1), parsed, i, index);
                    break;
                case '.':
                    parsed[index] = 0; //metachar for any character
                    break;
                default:
                    parsed[index] = regex.charAt(i);
                    break;
            }
            index++;
        }
        char[] correctedForLength = new char[index];
        System.arraycopy(parsed, 0, correctedForLength, 0, index); //remove extra indexes
        return new String(correctedForLength);
    }

    private static int backslash(char c, char[] parsed, int i, int index) {
        switch (c) {
            case 'd':
                parsed[index] = 1; //metachar for any digit
                i++;
                break;
            case 'a':
                parsed[index] = 2; //metachar for any alphabet
                i++;
                break;
            case 'l':
                parsed[index] = 3; //metachar for any lowercase letter
                i++;
                break;
            case 'u':
                parsed[index] = 4; //metachar for any uppercase letter
                i++;
                break;
            case 'w':
                parsed[index] = 5; //metachar for any digit, letter or character _
                i++;
                break;
        }
        return i;
    }

    private static String convertPlusToStar(String raw) {
        String parsed = "";
        RegexSubstring regex = new RegexSubstring(reverseString(raw));
        while (regex.hasNextChar()) { //transform + to * one at a time
            char c = regex.getNextChar();
            if (c == '+') {
                parsed += '*';
                if (regex.peekNextChar() == '(') { //handle parentheses
                    String expression = regex.getExpression().toString();
                    System.out.println("expression: " + expression);
                    if (expression.length() == 1) { //no parentheses for single characters
                        parsed += expression + expression;
                    } else { //ab|cd(ab|cd)* => (ab|cd)(ab|cd)*
                        parsed += "(" + expression + ")" + expression;
                    }
                    while (regex.hasNextChar()) { //read rest of the string
                        parsed += regex.getNextChar();
                    }
                    regex = new RegexSubstring(parsed);
                    parsed = "";
                } else {
                    c = regex.getNextChar();
                    parsed += String.valueOf(c) + String.valueOf(c);
                }
            } else {
                parsed += c;
            }
        }
        //System.out.println("converted " + parsed);
        return reverseString(parsed);
    }
    
    private static String reverseString(String s) {
        String reversed = "";
        for (int i = s.length() - 1; i > -1; i--) {
            char c = s.charAt(i);
            switch (c) {
                case '(':
                    reversed += ')';
                    break;
                case ')':
                    reversed += '(';
                    break;
                default:
                    reversed += s.charAt(i);
                    break;
            }
        }
        return reversed;
    }

}
