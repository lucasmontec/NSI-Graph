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
public class GraphView {

    private int edgeDist = 40;
    private ArrayList<GVertexView> vertices;
    private ArrayList<GVertexView> selectedVertices;
    private ArrayList<GEdgeView> edges;
    private ArrayList<GEdgeView> selectedEdges;

    /**
     * Create a visual representator to graph
     */
    public GraphView() {
        vertices = new ArrayList<>();
        selectedVertices = new ArrayList<>();

        edges = new ArrayList<>();
        selectedEdges = new ArrayList<>();
    }

    public void resetRendering() {
        for (GEdgeView e : edges) {
            e.render = true;
        }
        for (GVertexView e : vertices) {
            e.render = true;
        }
    }

    public ArrayList<GVertexView> getVerticesConnectedTo(GVertexView v) {
        ArrayList<GEdgeView> eges = getEdgesConnectedTo(v);
        ArrayList<GVertexView> vert = new ArrayList<>();
        if (eges.size() > 0) {
            for (GEdgeView e : edges) {
                if (e.getOrigin().equals(v)) {
                    vert.add(e.getTarget());
                }
                if (e.getTarget().equals(v)) {
                    vert.add(e.getOrigin());
                }
            }
        }
        return vert;
    }

    public ArrayList<GEdgeView> getEdgesConnectedTo(GVertexView v) {
        ArrayList<GEdgeView> eges = new ArrayList<>();
        for (GEdgeView e : edges) {
            if (e.getOrigin().equals(v) || e.getTarget().equals(v)) {
                eges.add(e);
            }
        }
        return eges;
    }

    /**
     * Select a vertex in a single click. N complexity
     *
     * @return If there was a vertice in the area that was clicked, select it
     */
    public GVertexView selectVertex(Point click) {
        //Go through everybody and find the guy at this click if this guy exits
        for (GVertexView vv : vertices) {
            if (vv.getCenter().distance(click) < GVertexView.size / 2) {
                vv.state = GVertexView.STATE.SELECTED;
                selectedVertices.add(vv);
                return vv;
            }
        }
        //Oh man, you clicked nothing!
        unselectAll();
        return null;
    }

    public GEdgeView selectEdge(Point click) {
        //Go through everybody and find the guy at this click if this guy exits
        for (GEdgeView ev : edges) {
            if (ev.getCenter().distance(click) < edgeDist) {
                ev.state = GEdgeView.STATE.SELECTED;
                selectedEdges.add(ev);
                return ev;
            }
        }
        //Oh man, you clicked nothing!
        unselectAll();
        return null;
    }

    /**
     * Unselect all vertices
     */
    public void unselectAll() {
        for (GVertexView v : selectedVertices) {
            v.state = GVertexView.STATE.IDLE;
        }
        for (GEdgeView e : selectedEdges) {
            e.state = GEdgeView.STATE.IDLE;
        }
        selectedEdges.clear();
        selectedVertices.clear();
    }

    /**
     * This will remove a edge using an edge view
     *
     * @param e
     */
    public void removeEdge(GEdgeView e) {
        if (edges.contains(e)) {
            edges.remove(e);
        }
    }

    /**
     * Send mouse moved events here!
     *
     * @param p
     */
    public void mouseMovedOver(Point p) {
        for (GVertexView vv : vertices) {
            if (vv.state != GVertexView.STATE.SELECTED) {
                if (vv.getCenter().distance(p) < GVertexView.size / 2) {
                    vv.state = GVertexView.STATE.MOUSEOVER;
                } else {
                    vv.state = GVertexView.STATE.IDLE;
                }
            }
        }

        for (GEdgeView ev : edges) {
            if (ev.state != GEdgeView.STATE.SELECTED) {
                if (ev.getCenter().distance(p) < edgeDist) {
                    ev.state = GEdgeView.STATE.MOUSEOVER;
                } else {
                    ev.state = GEdgeView.STATE.IDLE;
                }
            }
        }
    }

    public void paint(Graphics g) {
        //Draw all edges
        if (!edges.isEmpty()) {
            for (GEdgeView ev : edges) {
                ev.paint(g);
            }
        }

        //Draw all vertices
        if (!vertices.isEmpty()) {
            for (GVertexView vv : vertices) {
                vv.paint(g);
            }
        }
    }

    public GVertexView getViewByID(String ID) {
        for (GVertexView vv : vertices) {
            if (vv.getID().equals(ID) || vv.getVertex().getID().equals(ID)) {
                return vv;
            }
        }

        return null;
    }

    public int getVertexSize() {
        return GVertexView.size;
    }

    public void setVertexSize(int vertexSize) {
        GVertexView.size = vertexSize;
    }

    public ArrayList<GEdgeView> getEdges() {
        return edges;
    }

    public ArrayList<GVertexView> getVertices() {
        return vertices;
    }
}
