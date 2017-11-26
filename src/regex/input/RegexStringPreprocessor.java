
package regex.input;

public class RegexStringPreprocessor {

    /**
     * @param regex
     * @return preprocessed regex
     */
    public static String parseInput(String regex) {
        char[] parsed = new char[regex.length()];
        for (int i = 0; i < regex.length(); i++) {
            switch (regex.charAt(i)) {
                case '.':
                    parsed[i] = 0; //metachar for wild card .
                    break;
                default:
                    parsed[i] = regex.charAt(i);
                    break;
            }
        }
        return new String(parsed);
    }
}
