
package regex.input;

import java.util.Arrays;

public class RegexStringPreprocessor {

    public static String parseInput(String regex) {
        char[] parsed = new char[regex.length()];
        for (int i = 0; i < regex.length(); i++) {
            switch (regex.charAt(i)) {
                case '(':
                    parsed[i] = '.';
                    break;
                case ')': //*, + etc
                    parsed[i] = regex.charAt(++i);
                    parsed[i] = '.';
                    break;
                default:
                    parsed[i] = regex.charAt(i);
                    break;
            }
        }
        return new String(parsed);
    }
}
