
package regex.util;

public class CharacterClassifier {

    /**
     * @param c input character
     * @return c is regex symbol * or + or |
     */
    public static boolean isRegexSymbol(char c) {
        return c == '*' || c == '+' || c == '|';
    }
    
    /**
     * @param c input character
     * @return c is digit
     */
    public static boolean isClassD(char c) {
        return Character.isDigit(c);
    }
    
    /**
     * @param c input character
     * @return c is alphabet
     */
    public static boolean isClassA(char c) {
        return Character.isAlphabetic(c);
    }
    
    /**
     * @param c input character
     * @return c is lowercase letter
     */
    public static boolean isClassL(char c) {
        return Character.isLowerCase(c);
    }
    
    /**
     * @param c input character
     * @return c is uppercase letter
     */
    public static boolean isClassU(char c) {
        return Character.isUpperCase(c);
    }
    
    /**
     * @param c input character
     * @return c is digit or alphabet or _
     */
    public static boolean isClassW(char c) {
        return Character.isAlphabetic(c) || Character.isDigit(c) || c == '_';
    }
}
