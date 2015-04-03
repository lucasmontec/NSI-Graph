package nsigraph;

import java.awt.Graphics;
import java.awt.Point;

/**
 * Uma ferramenta para mover vertices na tela.
 * 
 * @author Lucas
 */
public class ArrangeTool extends Tool {

    //TODO Arrange multiple vertices
    //Store the dragging vertice
    GVertexView dragging;
    
    @Override
    public String getTitle() {
        return "Arrange";
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
        }
    }

    @Override
    public void unload(GraphControl gc) {
    }
}
