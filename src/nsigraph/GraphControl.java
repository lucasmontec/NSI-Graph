/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nsigraph;

import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;

/**
 *
 * @author Lucas
 */
public class GraphControl {

    private Graph graph;
    private GraphView view;

    public GraphControl(Graph g, GraphView v) {
        graph = g;
        view = v;
    }

    public void clearScriptSelection() {
        clearViewScriptSelection(view);
    }

    public void removeEdge(Object id) {
        if (id instanceof GEdge) {
            removeEdge((GEdge) id, graph, view);
        }
        if (id instanceof GEdgeView) {
            removeEdge((GEdgeView) id, graph, view);
        }
    }

    public void removeVertex(Object idnt) {
        removeVertex(idnt, graph, view);
    }

    public void addVertex(Point p, String title) {
        addVertex(p, title, graph, view);
    }

    public void addVertex(Point p) {
        addVertex(p, graph, view);
    }

    public void addEdge(GVertexView o, GVertexView t, String cost) {
        addEdge(o, t, cost, graph, view);
    }

    public void addEdge(GVertexView o, GVertexView t) {
        addEdge(o, t, graph, view);
    }

    public Graph getGraph() {
        return graph;
    }

    public void setGraph(Graph graph) {
        this.graph = graph;
    }

    public GraphView getView() {
        return view;
    }

    public void setView(GraphView view) {
        this.view = view;
    }

    ////////////////////////////////////////////////////////////////////////////
    //************************************************************************//
    //****************************STATIC**PART********************************//
    //************************************************************************//
    ////////////////////////////////////////////////////////////////////////////
    /**
     * This method clears all script selected vertices.
     *
     * @param gv The view to clear
     */
    public static void clearViewScriptSelection(GraphView gv) {
        for (GEdgeView eg : gv.getEdges()) {
            if (eg.isScriptSelected()) {
                eg.script_select(false);
            }
        }

        for (GVertexView eg : gv.getVertices()) {
            if (eg.isScriptSelected()) {
                eg.scriptColor = Color.orange;
                eg.script_select(false);
            }
        }
    }

    /**
     * This method will generate a graph visualization from a graph object
     *
     * @param g The graph to analyze
     * @param panl The panel to place the view
     * @param gen The view generator
     * @return Will return the graph view
     */
    public static GraphView generateViewFromGraph(Graph g, GraphPanel panl,
            ViewGenerator gen) {
        return gen.generate(g, panl.getWidth(), panl.getHeight());
    }

    /**
     * Add a vertex to the model
     *
     * @param p The center of the vertex on the view
     * @param title A title to the vertex (This is not its ID)
     */
    public static void addVertex(Point p, String title, Graph g, GraphView gv) {
        //create the vertex
        GVertex v = new GVertex(title);
        //add to the model
        if (g != null) {
            g.addVertex(v);
        }
        //add a view to it
        if (gv != null) {
            gv.getVertices().add(new GVertexView(p, v));
        }
    }

    /**
     * Add a vertex to the model
     *
     * @param p The center of the vertex on the view
     */
    public static void addVertex(Point p, Graph g, GraphView gv) {
        //create the vertex
        GVertex v = new GVertex();
        //add to the model
        if (g != null) {
            g.addVertex(v);
        }
        //add a view to it
        if (gv != null) {
            gv.getVertices().add(new GVertexView(p, v));
        }
    }

    /**
     * This will remove all edges connected the a vertex.
     *
     * @param vv The vertex view to use to remove everything
     */
    public static void isolateVertex(GVertexView vv, GraphView gv, Graph g) {
        ArrayList<GEdgeView> removeGuys = new ArrayList<>();
        ArrayList<GEdge> removeGuys2 = new ArrayList<>();

        //Remove all edges that connect to this guy
        for (GEdgeView ev : gv.getEdges()) {
            if (ev.getOrigin().equals(vv) || ev.getTarget().equals(vv)) {
                removeGuys.add(ev);
            }
        }
        //Avoid concurrent modification
        if (!removeGuys.isEmpty()) {
            for (GEdgeView ev : removeGuys) {
                gv.removeEdge(ev);
            }
        }

        //Remove all edges that connect to this guy
        for (GEdge e : g.getEdges()) {
            if (e.getOrigin().equals(vv.getVertex()) || e.getTarget().equals(vv.getVertex())) {
                //Update the degs - doesnt matter which one because
                //the isolated will be set to 0
                e.getOrigin().decdeg();
                e.getTarget().decdeg();
                //Add to ping-pong list
                removeGuys2.add(e);
            }
        }
        //Avoid concurrent modification
        if (!removeGuys2.isEmpty()) {
            for (GEdge e : removeGuys2) {
                g.removeEdge(e);
            }
        }

        //update degree
        vv.getVertex().setDegree(0);
    }

    /**
     * Will try to remove the edge from the graph and from the view
     *
     * @param edge The edge to remove
     * @param g The graph to serach for it
     * @param gv The graph view to search for it
     */
    public static void removeEdge(GEdge edge, Graph g, GraphView gv) {
        //Vertices to update after deletion
        GVertex origin = edge.getOrigin();
        GVertex target = edge.getTarget();

        //Remove edge from graph
        if (g != null) {
            g.removeEdge(edge);
            //Update the degree
            origin.decdeg();
            target.decdeg();
        }

        //Remove from view
        if (gv != null) {
            //Get all edges
            ArrayList<GEdgeView> edges = gv.getEdges();
            ArrayList<GEdgeView> redges = new ArrayList<>();
            //Add to ping-pong remove list
            for (GEdgeView gev : edges) {
                if (gev.getEdge().equals(edge)) {
                    redges.add(gev);
                }
            }
            //Check if we need to remove
            if (redges.size() > 0) {
                for (GEdgeView re : redges) {
                    edges.remove(re);
                }
            }
            //Clear redges
            redges.clear();
        }
    }

    /**
     * Will try to remove the edge from the graph and from the view
     *
     * @param edge The edge to remove
     * @param g The graph to serach for it
     * @param gv The graph view to search for it
     */
    public static void removeEdge(GEdgeView edge, Graph g, GraphView gv) {
        //Vertices to update after deletion
        GVertex origin = edge.getEdge().getOrigin();
        GVertex target = edge.getEdge().getTarget();

        //Remove edge from graph
        if (g != null) {
            g.removeEdge(edge.getEdge());
            //Update the degree
            origin.decdeg();
            target.decdeg();
        }

        //Remove from view
        if (gv != null) {
            //try
            gv.removeEdge(edge);
        }
    }

    /**
     * This will add an edge using 2 vertex views
     *
     * @param o The origin vertex
     * @param t The target vertex
     */
    public static void addEdge(GVertexView o, GVertexView t, Graph g, GraphView gv) {
        GEdge e = new GEdge(o.getVertex(), t.getVertex());
        GEdgeView ev = null;
        if (o != null && t != null) {
            ev = new GEdgeView(e, o, t);

            if (g != null) {
                if (g.addEdge(e)) {
                    //Update degrees
                    e.getOrigin().incdeg();
                    e.getTarget().incdeg();
                }
            }
            if (gv != null) {
                if (gv.getEdges().contains(ev)) {
                    gv.getEdges().remove(ev);
                }

                gv.getEdges().add(ev);
            }
        }
    }

    /**
     * This will add an edge using 2 vertex views
     *
     * @param o The origin vertex
     * @param t The target vertex
     */
    public static void addEdge(GVertexView o, GVertexView t, String cost, Graph g, GraphView gv) {
        GEdge e = new GEdge(o.getVertex(), t.getVertex(), Float.parseFloat(cost));
        GEdgeView ev = null;
        if (o != null && t != null) {
            ev = new GEdgeView(e, o, t);

            if (g != null) {
                if (g.addEdge(e)) {
                    //Update degrees
                    e.getOrigin().incdeg();
                    e.getTarget().incdeg();
                }
            }
            if (gv != null) {
                if (gv.getEdges().contains(ev)) {
                    gv.getEdges().remove(ev);
                }

                gv.getEdges().add(ev);
            }
        }
    }

    /**
     * Get a view from object
     *
     * @param v The object to look
     * @param gv The view to search
     * @return The corresponding view if found
     */
    public static Object getView(Object v, GraphView gv) {
        if (gv == null) {
            return null;
        }

        if (v instanceof GVertex) {
            for (GVertexView vv : gv.getVertices()) {
                if (vv.getVertex().equals(v)) {
                    return vv;
                }
            }
        }

        if (v instanceof GEdge) {
            for (GEdgeView vv : gv.getEdges()) {
                if (vv.getEdge().equals(v)) {
                    return vv;
                }
            }
        }
        return null;
    }

    /**
     * Remove a vertex by any identifier.<br> You can pass the ID as a string in
     * the form VERTEX_UID or as an int UID<br>
     *
     * @param identifier Can be the vertex ID, the vertex Title or even the
     * vertex Object
     * @return True if found and removed. False otherwise
     */
    public static boolean removeVertex(Object identifier, Graph g, GraphView gv) {
        //Store the vertex to remove
        GVertex rv = null;

        //Check if we are removing by the object directly
        if (identifier instanceof GVertex) {
            //Isolate the vertex
            isolateVertex((GVertexView) getView(identifier, gv), gv, g);

            //Remove the vertex view
            if (getView((GVertex) identifier, gv) != null) {
                gv.getVertices().remove((GVertexView) getView((GVertex) identifier, gv));
            }

            //Than finally remove from the model
            if (g != null) {
                return g.getVertices().remove((GVertex) identifier);
            } else {
                return false;
            }
        }

        //Check if we are removing by the vertex view
        if (identifier instanceof GVertexView) {
            GVertexView v = (GVertexView) identifier;
            if (v.getVertex() != null) {
                isolateVertex(v, gv, g);
                return gv.getVertices().remove(v) && g.getVertices().remove(v.getVertex());
            } else {
                gv.getVertices().remove(v);
                return false;
            }
        }

        //Check this instance we are handling
        if (identifier instanceof String) {
            //If it is a string it can be the title or the ID
            for (GVertex v : g.getVertices()) {
                //Check the title
                if (v.getVertexTitle().equals((String) identifier)) {
                    rv = v;
                    break;
                }
                //Check the ID
                if (v.getID().equals((String) identifier)) {
                    rv = v;
                    break;
                }
            }
        }
        //Check if this instance is a ID as an Int
        if (identifier instanceof Integer) {
            for (GVertex v : g.getVertices()) {
                //Check the ID
                if (Integer.parseInt(v.getID().split("_")[1]) == (Integer) identifier) {
                    rv = v;
                    break;
                }
            }
        }
        //If found a v then remove it and return success
        if (rv == null) {
            return false;
        } else {
            return removeVertex(rv, g, gv);
        }
    }
}
