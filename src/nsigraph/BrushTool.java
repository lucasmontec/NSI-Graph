package nsigraph;

import java.awt.Graphics;
import java.awt.Point;

/**
 * A ferramenta padrão de criação de nós e arestas
 * 
 * @author Lucas
 */
public class BrushTool extends Tool {

    //Store first selected to enter connect mode
    GVertexView selected_to_connect;
    //Store current mouse pos
    Point mousePos;

    public BrushTool(String title) {
        this.setPropriety("Title", title);
        this.setPropriety("Cost", "-1");
    }

    public BrushTool(String title, String cost) {
        this.setPropriety("Title", title);
        this.setPropriety("Cost", cost);
    }

    @Override
    public void press(GraphControl g, Point pos, int button) {
        if (button == 1) {
            //Check if we can select someone
            GVertexView sv = g.getView().selectVertex(pos);
            //Check if we are already in selectmode
            if (selected_to_connect != null) {
                //Check if we can select to enter the connect mode
                //Simple graphs only
                if (sv != null && !sv.equals(selected_to_connect)) {
                    if (!((String) getPropriety("Cost")).contains("-")) {
                        g.addEdge(selected_to_connect, sv, "" + getPropriety("Cost"));
                    } else {
                        g.addEdge(selected_to_connect, sv);
                    }
                    selected_to_connect = null;
                    g.getView().unselectAll();
                    return;
                } else {
                    selected_to_connect = null;
                    g.getView().unselectAll();
                    return;
                }
            }
            if (g != null && sv != null) {
                //Since we selected someone thn we will enter conect mode
                selected_to_connect = sv;
            } else {
                //else we add vertices
                g.addVertex(pos, (String) proprieties.get("Title"));
                System.out.println("Added node at: " + pos.toString() + " with title: " + (String) proprieties.get("Node Title"));
            }
        }
        if (button == 3) {
            if (g.getView().selectEdge(pos) != null) {
                g.removeEdge(g.getView().selectEdge(pos));
            }

            if (g.getView().selectVertex(pos) != null) {
                g.removeVertex(g.getView().selectVertex(pos));
            }
        }
    }

    @Override
    public String getTitle() {
        return "Add Node";
    }

    @Override
    public void release(GraphControl g, Point pos) {
    }

    @Override
    public void draw(GraphControl gp, Graphics g) {
        //Draw a line if we are in connect mode
        if (selected_to_connect != null && mousePos != null) {
            g.drawLine(selected_to_connect.getCenter().x, selected_to_connect.getCenter().y, mousePos.x, mousePos.y);
        }
    }

    @Override
    public void mouseMoved(GraphControl gp, Point pos) {
        mousePos = pos;
        gp.getView().mouseMovedOver(pos);
    }

    @Override
    public void unload(GraphControl gc) {
    }
}
