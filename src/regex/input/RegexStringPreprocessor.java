package regex.input;

public class RegexStringPreprocessor {

    /**
     * @param regex
     * @return preprocessed regex
     */
    public static String parseInput(String regex) {
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
        for (int i = 0; i < index; i++) {
            correctedForLength[i] = parsed[i];
        }
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
}
