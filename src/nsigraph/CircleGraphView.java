package nsigraph;

import java.awt.Point;

/**
 * Uma visualização em circulo de elementos do grafo.
 * @author Lucas
 */
public class CircleGraphView implements ViewGenerator{

    @Override
    public GraphView generate(Graph g, int screenW, int screenH) {
        //Get a view
        GraphView gv = new GraphView();
        
        //First we calculate the circle parts
        float slice = 360f/(1f*g.getVertices().size());
        
        //Get a theta
        float theta = 0f;
        
        //Radius
        float r = g.getVertices().size()*10;
        r = Math.min(r, screenW*0.25f);
        r = Math.max(r, 50);
        
        //For each vertex we will add it to the circle
        for(GVertex v : g.getVertices()){
            //Calc the center
            int x = (int)((screenW/2) + Math.sin(Math.toRadians(theta))*r);
            int y = (int)((screenH/2) + Math.cos(Math.toRadians(theta))*r);
            
            //Gen the point
            Point center = new Point(x,y);
            
            //Generate the VV
            GVertexView vv = new GVertexView(center, v);
            
            //Add the vv to the graphview
            gv.getVertices().add(vv);
            
            //Move the angle
            theta += slice;
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
