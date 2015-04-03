/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nsigraph;

import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;

/**
 *
 * @author Lucas
 */
public class OrganizerTool extends Tool {
    //TODO Arrange multiple vertices
    //Store the dragging vertice

    GVertexView dragging;
    ArrayList<GVertexView> near = null;
    ArrayList<GEdgeView> nearedges = null;

    public OrganizerTool() {
    }

    @Override
    public String getTitle() {
        return "Organizer";
    }

    @Override
    public void press(GraphControl g, Point pos, int button) {
        //If there is a vertex... select it and drag it arround
        dragging = g.getView().selectVertex(pos);
        if (dragging != null) {
            dragging.setCenter(pos);
        }
    }

    @Override
    public void release(GraphControl g, Point pos) {
        //If it is holding a vertex... drop it
        if (dragging != null) {
            dragging = null;
        }
        //Release the selection
        g.getView().unselectAll();
    }

    @Override
    public void draw(GraphControl graph, Graphics g) {
    }
    
    @Override
    public void mouseMoved(GraphControl graph, Point pos) {
        //Move the vertex arround
        if (dragging != null) {
            dragging.setCenter(pos);
        } else {
            //Show the vertex section only
            GVertexView v = graph.getView().selectVertex(pos);
            near = graph.getView().getVerticesConnectedTo(v); //Set the render guys
            near.add(v);
            for (GVertexView gv : graph.getView().getVertices()) {
                if (near.contains(gv)) {
                    gv.render = true;
                } else {
                    gv.render = false;
                }
            }
            nearedges = graph.getView().getEdgesConnectedTo(v);
            for (GEdgeView gv : graph.getView().getEdges()) {
                if (nearedges.contains(gv)) {
                    gv.render = true;
                } else {
                    gv.render = false;
                }
            }
        }
    }

    @Override
    public void unload(GraphControl gc) {
        gc.getView().resetRendering();
    }
}
