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
        return new String(removeNulls(index, parsed));
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
    
    private static char[] removeNulls(int index, char[] parsed) {
        char[] correctedForLength = new char[index];
        System.arraycopy(parsed, 0, correctedForLength, 0, index); //remove extra indexes
        return correctedForLength;
    }
    
    private static String convertPlusToStar(String regex) {
        String parsed = "";
        for (int i = regex.length() - 1; i > -1; i--) {
            if (regex.charAt(i) == '+') {
                parsed += '*';
                if (regex.charAt(i - 1) == ')') { //handle parentheses
                    String expression = "";
                    i--;
                    int rightParentheses = 1;
                    while (i > -1) {
                        char c = regex.charAt(--i);
                        if (c == '(') {
                            rightParentheses--;
                        } else if (c == ')') {
                            rightParentheses++;
                        }
                        if (rightParentheses < 0) {
                            throw new IllegalStateException("Invalid parentheses.");
                        }
                        if (rightParentheses == 0) {
                            break;
                        }
                        expression += c;
                    }
                    parsed += ")" + expression + "(" + expression;
                } else {
                    parsed += regex.charAt(i - 1);
                }
            } else {
                parsed += regex.charAt(i);
            }
        }
        String reversed = "";
        for (int i = parsed.length() - 1; i > -1; i--) {
            reversed += parsed.charAt(i);
        }
        return reversed;
    }
    
}
