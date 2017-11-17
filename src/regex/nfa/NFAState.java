
package regex.nfa;

import regex.util.StateType;

public class NFAState {
    public StateType type;
    public NFAState arrowA;
    public NFAState arrowB;
    public char symbol;
    
    public NFAState(StateType t, char s) {
        this.type = t;
        this.symbol = s;
        this.arrowA = null;
        this.arrowB = null;
    }
    
    public NFAState(char s) {
        this.type = StateType.NORMAL;
        this.symbol = s;
        this.arrowA = null;
        this.arrowB = null;
    }
    
    public NFAState(StateType t) {
        this.type = t;
        this.symbol = 'a';
        this.arrowA = null;
        this.arrowB = null;
    }
    
    /**
     * @param state to set as one of the one of the next nfa states of the current state
     */
    public void setNext(NFAState state) {
        if (this.arrowA == null) {
            this.arrowA = state;
        } else if (this.arrowB == null) {
            this.arrowB = state;
        } else {
            throw new IllegalStateException("Tried to add third connection to a NFA state!");
        }
    }
    
    public String dumpInfo() {
        return "Type: " + type + ", symbol: " + symbol;
    }

    //could use better logging for debugging
    @Override
    public String toString() {
        String info = "Type: " + type + ", symbol: " + symbol;
        if (arrowA != null)
            info += ", A: " + arrowA.dumpInfo();
        if (arrowB != null)
            info += ", B: " + arrowB.dumpInfo();
        return info;
    }
}
