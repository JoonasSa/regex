
package regex.nfa;

import regex.util.StateType;

public class NFAState {
    public StateType type;
    public NFAState arrowA = null;
    public NFAState arrowB = null;
    public char symbol;
    public String name = null; //used for debugging, will be removed at some point
    
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
    
    public String dumpInfo() {
        String s = "";
        if (name != null) 
            s += ", name: " + name;
        return "Type: " + type + ", symbol: " + symbol + s;
    }

    @Override
    public String toString() {
        String info = "Type: " + type + ", symbol: " + symbol;
        if (name != null)
            info += ", name: " + name;
        if (arrowA != null)
            info += " | A: " + arrowA.dumpInfo();
        if (arrowB != null)
            info += " | B: " + arrowB.dumpInfo();
        return info;
    }
    /*
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj.getClass() != NFAState.class) {
            return false;
        }
        NFAState other = (NFAState) obj;
        System.out.println("equals: " + this + "  &  " + other);
        if (this.arrowA != null) {
            if (this.arrowB != null) {
                if (!this.arrowA.dumpInfo().equals(this.arrowB.dumpInfo()))
                    return false;
            } else {
                return false;
            } 
        } else {
            if (this.arrowB != null)
                return false;
        }
        return this.type == other.type && this.symbol == other.symbol;
    }
    */
}
