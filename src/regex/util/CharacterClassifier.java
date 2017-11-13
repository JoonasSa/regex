
package regex.util;

public class CharacterClassifier {

    public static boolean isRegexCharacter(char c) {
        return (c == '*') || (c == '+') || (c == '|');
    }
}
