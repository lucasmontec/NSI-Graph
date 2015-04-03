/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nsigraph;

import java.awt.Graphics;
import java.awt.Point;

/**
 *
 * @author Lucas
 */
public class RemoveTool extends Tool{

    @Override
    public String getTitle() {
        return "Remove tool";
    }

    @Override
    public void press(GraphControl gc, Point pos, int button) {
        if(gc.getView().selectEdge(pos) != null){
            gc.removeEdge(gc.getView().selectEdge(pos));
        }
        
        if(gc.getView().selectVertex(pos) != null){
            gc.removeVertex(gc.getView().selectVertex(pos));
        }
    }

    @Override
    public void release(GraphControl g, Point pos) {
        return;
    }

    @Override
    public void draw(GraphControl graph, Graphics g) {
    }

    @Override
    public void mouseMoved(GraphControl gc, Point pos) {
        gc.getView().mouseMovedOver(pos);
    }

    @Override
    public void unload(GraphControl gc) {
    }
    
}
