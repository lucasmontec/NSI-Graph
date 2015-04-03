/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nsigraph;

import java.util.Comparator;

/**
 *
 * @author Lucas
 */
public class GEdgeViewComparator implements Comparator<GEdgeView>{

    @Override
    public int compare(GEdgeView o1, GEdgeView o2) {
        //Both must cost smt
        if(o1.getEdge().hasCost() && o2.getEdge().hasCost()){
            return (int)Math.signum(o1.getEdge().getCost()-o2.getEdge().getCost());
        }
        
        //equal
        return 0;
    }
    
}
