package nsigraph;

import java.util.ArrayList;

/**
 *
 * @author Lucas
 */
public class Graph {

    private ArrayList<GVertex> vertices;
    private ArrayList<GEdge> edges;
    private String author = "noauthor";

    public Graph() {
        vertices = new ArrayList<>();
        edges = new ArrayList<>();
    }

    public ArrayList<GVertex> getVerticesConnectedTo(GVertex v) {
        ArrayList<GEdge> eges = getEdgesConnectedTo(v);
        ArrayList<GVertex> vert = new ArrayList<>();
        System.out.println("found " + eges.size() + " neighbors.");
        if (eges.size() > 0) {
            for (GEdge e : eges) {
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

    public ArrayList<GEdge> getEdgesConnectedTo(GVertex v) {
        ArrayList<GEdge> eges = new ArrayList<>();
        for (GEdge e : eges) {
            if (e.getOrigin().equals(v) || e.getTarget().equals(v)) {
                eges.add(e);
            }
        }
        return eges;
    }

    /**
     * Don't use this! This will be replaced by a faster system.
     *
     * @param v
     * @return
     */
    public int getDegree(GVertex v) {
        int counter = 0;
        if (edges.size() > 0) {
            for (GEdge e : edges) {
                if (e.getOrigin().equals(v) || e.getTarget().equals(v)) {
                    counter++;
                }
            }
        }
        v.setDegree(counter);
        return counter;
    }

    /**
     * Call this when you load a graph
     */
    public void updateAllVerticesDegrees() {
        for (GVertex v : vertices) {
            getDegree(v);
        }
    }

    public boolean addEdge(GEdge e) {
        if (!edges.contains(e)) {
            edges.add(e);
            return true;
        }
        return false;
    }

    public GVertex getVertexWithID(String ID) {
        for (GVertex v : vertices) {
            if (v.getID().equals(ID)) {
                return v;
            }
        }
        return null;
    }

    public void addVertex(GVertex n) {
        if (!vertices.contains(n)) {
            vertices.add(n);
        }
    }

    public ArrayList<GEdge> getEdges() {
        return edges;
    }

    public ArrayList<GVertex> getVertices() {
        return vertices;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    @Override
    public String toString() {
        if (vertices != null && edges != null) {
            String s = "GRAPH> author: " + author + "\nVertices[";
            for (GVertex v : vertices) {
                s += v.getID() + " ";
            }
            s += "] ";
            s += "\nEdges[";
            for (GEdge e : edges) {
                s += "(";
                s += e.getOrigin().getID();
                s += ",";
                s += e.getTarget().getID();
                if (e.hasCost()) {
                    s += " | ";
                    s += e.getCost();
                }
                s += ")";
            }
            s += "] ";
            //TODO Add the connections
            return s;
        } else {
            return "empty graph";
        }
    }

    public void removeEdge(GEdge edge) {
        if (edges.contains(edge)) {
            edges.remove(edge);
        }
    }
}
