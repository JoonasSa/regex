
package regex.nfa;

import regex.util.StateType;

public class NFAState {
    public StateType type;
    public NFAState arrowA = null;
    public NFAState arrowB = null;
    public char symbol;
    
    public NFAState(StateType t, char s) {
        this.type = t;
        this.symbol = s;
    }
    
    public NFAState(char s) {
        this.type = StateType.NORMAL;
        this.symbol = s;
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
    
    /**
     * @return Deep copy of the NFAState
     */
    public NFAState getCopy() {
        NFAState s = new NFAState(this.type, this.symbol);
        s.setNext(this.arrowA);
        s.setNext(this.arrowB);
        return s;
    }
    
    /**
     * @return Deep copy of the latest added NFAState
     */
    public NFAState getLatestArrow() {
        if (this.arrowB != null) {
            return this.arrowB;
        } else if (this.arrowA != null) {
            return this.arrowA;
        }
        return null;
    }
    
    public void removeLatestArrow() {
        if (this.arrowB != null) {
            this.arrowB = null;
        } else if (this.arrowA != null) {
            this.arrowA = null;
        }
    }
    
    public String dumpInfo() {
        return "Type: " + type + ", symbol: " + symbol;
    }

    /**
     * @return information about this state and the states it has connections to
     */
    @Override
    public String toString() {
        String info = "Type: " + type + ", symbol: " + symbol;
        if (arrowA != null)
            info += " | A: " + arrowA.dumpInfo();
        if (arrowB != null)
            info += " | B: " + arrowB.dumpInfo();
        return info;
    }
    
}
