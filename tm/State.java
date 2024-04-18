package tm;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Class to represent the State object 
 * 
 */
public class State {
    
    protected boolean isStart;
    protected boolean isFinal;
    protected Map<Character, Set<State>> delta = new HashMap<>();
    
    /**
     * Creates an State object with name
     * 
     * @param name name of the State
     */
    public State(String name) {
        isFinal = false;
        isStart = false;
    }

    /**
     * Add transitions to State's delta
     * 
     * @param onSymb symbol to transition on
     * @param toState state to transition to
     */
    public void addTransition(char onSymb, State toState) {
        Set<State> set = new HashSet<>();
        delta.putIfAbsent(onSymb, set);
        delta.get(onSymb).add(toState);
    }


    /**
     * set start state of State
     */
    public void setStart() {
        isStart = true;
    }

    /**
     * set State to final/accepting state
     */
    public void setFinal() {
        isFinal = true;
    }
    
    /**
     * Return delta of the State
     * @param onSymb symbol of State
     * @return delta of given symbol
     */
    public Set<State> toStates(char onSymb) {
        return delta.get(onSymb);
    }
}
