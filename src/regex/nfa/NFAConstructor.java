
package regex.nfa;

import regex.util.StateType;

public class NFAConstructor {
    
    public static NFAState constructNFA(String regex) {
        NFAState start = new NFAState(StateType.START, null, null, (char) 0);
        //construct NFA here
        NFAState end = new NFAState(StateType.FINAL, null, null, (char) 0);
        return start;
    }
}
