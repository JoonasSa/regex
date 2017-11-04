
package regex.nfa;

import regex.util.StateType;

public class NFAState {
    public StateType type;
    public NFAState arrowA;
    public NFAState arrowB;
    public char symbol;
    
    public NFAState(StateType t, NFAState a, NFAState b, char s) {
        this.type = t;
        this.arrowA = a;
        this.arrowB = b;
        this.symbol = s;
    }
    
    public void setArrowA(NFAState a) {
        this.arrowA = a;
    }
    
    public void setArrowB(NFAState b) {
        this.arrowB = b;
    }
}
