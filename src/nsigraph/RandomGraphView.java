/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nsigraph;

import java.awt.Point;

/**
 *
 * @author Lucas
 */
public class RandomGraphView implements ViewGenerator{

    @Override
    public GraphView generate(Graph g, int screenW, int screenH) {
        //Get a view
        GraphView gv = new GraphView();
        
        //For each vertex we will add it to the circle
        for(GVertex v : g.getVertices()){
            //Calc the center
            int x = (int)(Math.random()*screenW);
            int y = (int)(Math.random()*screenH);
            
            //Gen the point
            Point center = new Point(x,y);
            
            //Generate the VV
            GVertexView vv = new GVertexView(center, v);
            
            //Add the vv to the graphview
            gv.getVertices().add(vv);
        }
        
        //Add all edges
        for(GEdge e : g.getEdges()){
            //Get source view
            GVertexView sv = gv.getViewByID(e.getOrigin().getID());
            
            //Get target view
            GVertexView tv = gv.getViewByID(e.getTarget().getID());
            
            //Create the EdgeView
            GEdgeView ev = new GEdgeView(e,sv,tv);
            
            //Add to the graphview
            gv.getEdges().add(ev);
        }
        
        return gv;
    }
    
}
