/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nsigraph;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Lucas
 */
public abstract class Tool implements ITool {
    //Create a proprieties map for each tool

    protected Map<String, Object> proprieties;
    
    public Tool() {
        proprieties = new HashMap<>();
    }

    /**
     * Set a propriety
     */
    public void setPropriety(String key, Object value) {
        if (proprieties != null) {
            //remove copy
            /*if (proprieties.containsKey(key)) {
                proprieties.remove(key);
            }*/

            //add new entry
            proprieties.put(key, value);
        }
    }

    /**
     * Get a propriety
     */
    public Object getPropriety(String key) {
        return proprieties.get(key);
    }
    
    /**
     * Get all proprieties
     */
    public Map getProprieties(){
        return proprieties;
    }
}
