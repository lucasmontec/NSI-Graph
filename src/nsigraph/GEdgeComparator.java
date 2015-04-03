/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nsigraph;

import java.util.Comparator;

/**
 * O comparador padrão de custo de arestas.
 * Ele compara e diz a aresta melhor como a de menor custo
 * usando a função {@link GEdge#getCost() getCost}
 * @author Lucas
 */
public class GEdgeComparator implements Comparator<GEdge>{

    @Override
    public int compare(GEdge o1, GEdge o2) {
        //Both must cost smt
        if(o1.hasCost() && o2.hasCost()){
            return (int)Math.signum(o1.getCost()-o2.getCost());
        }
        
        //equal
        return 0;
    }
    
}
