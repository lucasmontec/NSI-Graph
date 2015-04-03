/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nsigraph;

import java.awt.Point;
import javax.swing.JOptionPane;

/**
 *
 * @author Lucas
 */
public class GridGraphView implements ViewGenerator {

    @Override
    public GraphView generate(Graph g, int screenW, int screenH) {

        //Get a view
        GraphView gv = new GraphView();

        float vertexWeight = g.getVertices().size()*0.35f;
        
        int startatx = (int) (screenW * 0.1f);
        int stopatx = (int) (screenW * 0.9f);
        int xstep = (int) ((screenW * 0.9) / vertexWeight);
        int startaty = (int) (screenH * 0.1f);
        int ystep = 80;
        try {
            ystep = Integer.parseInt(JOptionPane.showInputDialog(null,
                    "Select a value for the vertical step:",
                    "Vertical step", JOptionPane.INFORMATION_MESSAGE));
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Invalid entry!","Error",
                    JOptionPane.WARNING_MESSAGE);
            ystep = 80; //ensure shit
        }
        int currystep = startaty;
        int currxstep = startatx;

        //For each vertex we will add it to the circle
        for (GVertex v : g.getVertices()) {

            //Gen the point
            Point center = new Point(currxstep, currystep);

            //Generate the VV
            GVertexView vv = new GVertexView(center, v);

            //Add the vv to the graphview
            gv.getVertices().add(vv);

            //Reset step
            if (currxstep < stopatx) {
                currxstep += xstep;
            } else {
                currxstep = startatx;
                currystep += ystep;
            }

        }

        //Add all edges
        for (GEdge e : g.getEdges()) {
            //Get source view
            GVertexView sv = gv.getViewByID(e.getOrigin().getID());

            //Get target view
            GVertexView tv = gv.getViewByID(e.getTarget().getID());

            //Create the EdgeView
            GEdgeView ev = new GEdgeView(e, sv, tv);

            //Add to the graphview
            gv.getEdges().add(ev);
        }

        return gv;

    }
}
