package main.java.regex.input;

public class RegexStringPreprocessor {

    /**
     * @param regex string
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
        //remove extra space from char[]
        char[] correctedForLength = new char[index];
        for (int i = 0; i < index; i++) {
            correctedForLength[i] = parsed[i];
        }
        return new String(correctedForLength);
    }

    /**
     * @param c character class type
     * @param parsed array of parsed characters
     * @param i current index of regex
     * @param index current index of parsed array
     * @return current index of regex
     */
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

    /**
     * @param raw unprocesssed input string
     * @return input string where + are transformed to *. For example a+ becomes aa*
     */
    private static String convertPlusToStar(String raw) {
        String parsed = "";
        RegexSubstring regex = new RegexSubstring(reverseString(raw));
        while (regex.hasNextChar()) {
            char c = regex.getNextChar();
            if (c == '+') {
                parsed += '*';
                if (regex.peekNextChar() == '(') {
                    regex = handleParentheses(regex, parsed);
                    parsed = "";
                } else {
                    c = regex.getNextChar();
                    parsed += String.valueOf(c) + String.valueOf(c);
                }
            } else {
                parsed += c;
            }
        }
        return reverseString(parsed);
    }
    
    /**
     * @param regex old RegexSubString
     * @param parsed currently parsed string
     * @return new RegexSubString containing the currently parsed string
     */
    private static RegexSubstring handleParentheses(RegexSubstring regex, String parsed) {
        String expression = regex.getExpression().toString();
        //no parentheses for single characters
        if (expression.length() == 1) {
            parsed += expression + expression;
        } else if (stringContainsChar(expression, '|')) {
            parsed += "(" + expression + ")(" + expression + ")";
        } else {
            parsed += "(" + expression + ")" + expression;
        }
        //read rest of the string
        while (regex.hasNextChar()) { 
            parsed += regex.getNextChar();
        }
        return new RegexSubstring(parsed);
    }
    
    /**
     * @param s string to reverse
     * @return s reversed
     */
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
    
    /**
     * @param s string to search for character
     * @param c searched character
     * @return string s contains character c
     */
    private static boolean stringContainsChar(String s, char c) {
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == c) {
                return true;
            }
        }
        return false;
    }

}
