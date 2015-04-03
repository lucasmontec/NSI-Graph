/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nsigraph;

import java.util.Comparator;

/**
 *Compare two vertixes by their degrees
 * @author Thiago
 */
public class GVertexComparator  implements Comparator<GVertex> {

    @Override
    public int compare(GVertex o1, GVertex o2) {
        return (int)Math.signum(o1.getDegree()-o2.getDegree());
    }
    
    
}
