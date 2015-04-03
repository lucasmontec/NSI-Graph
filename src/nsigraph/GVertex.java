/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nsigraph;

import java.util.Objects;

/**
 * A vertex model for the graph viwer
 *
 * @author Lucas
 */
public class GVertex {

    public static int UID = 0;
    private String ID = "V";
    private String vertexTitle = "";
    private int degree = 0;

    public GVertex() {
        ID += GVertex.UID++;
    }

    public GVertex(String title) {
        vertexTitle = title;
        ID += GVertex.UID++;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }

        if (!(o instanceof GVertex)) {
            return false;
        }

        GVertex v = (GVertex) o;

        if (!v.ID.equals(ID)) {
            return false;
        }

        if (v.vertexTitle != null && vertexTitle == null) {
            return false;
        }

        if (v.vertexTitle == null && vertexTitle != null) {
            return false;
        }

        if (!v.vertexTitle.equals(vertexTitle)) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + Objects.hashCode(this.ID);
        hash = 97 * hash + Objects.hashCode(this.vertexTitle);
        return hash;
    }

    public void setVertexTitle(String nodeTitle) {
        this.vertexTitle = nodeTitle;
    }

    public String getVertexTitle() {
        return vertexTitle;
    }

    /**
     * This is for the GML IO loader. You shouldn't use this.
     *
     * @param ID The GML id of this vertex
     */
    public void setID(int ID) {
        this.ID = "V" + ID;
    }

    /**
     * @return Return this vertex unique ID
     */
    public String getID() {
        return ID;
    }

    /**
     * DONT USE THIS METHOD! THIS IS FOR AUTOMATION!
     *
     * @param deg
     */
    public void setDegree(int deg) {
        degree = deg;
    }

    /**
     * DONT USE THIS METHOD! THIS IS FOR AUTOMATION!
     */
    public void decdeg() {
        degree--;
    }

    /**
     * DONT USE THIS METHOD! THIS IS FOR AUTOMATION!
     */
    public void incdeg() {
        degree++;
    }

    /**
     * Not very save. But less costly than Graph.getDegree(vertex)
     *
     * @return This vertex degree i hope
     */
    public int getDegree() {
        return degree;
    }
}
