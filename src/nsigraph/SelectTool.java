/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nsigraph;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

/**
 *
 * @author Lucas
 */
public class SelectTool extends Tool {

    public GVertexView root;
    public GVertexView target;
    private Color targetColor = new Color(50, 180, 50);
    private Color sourceColor = new Color(50, 50, 180);

    @Override
    public String getTitle() {
        return "notitle";
    }

    @Override
    public void unload(GraphControl gc) {
        for(GVertexView vv : gc.getView().getVertices()){
            vv.scriptColor = Color.orange;
        }
    }

    @Override
    public void press(GraphControl gc, Point pos, int button) {
        if (button == 1) {
            if (root != null) {
                root.scriptColor = Color.orange;
                root.script_select(false);
            }
            root = gc.getView().selectVertex(pos);
            if (root != null) {
                root.scriptColor = sourceColor;
                root.script_select(true);
            }
        }
        if (button == 3) {
            if (target != null) {
                target.scriptColor = Color.orange;
                target.script_select(false);
            }
            target = gc.getView().selectVertex(pos);
            if (target != null) {
                target.scriptColor = targetColor;
                target.script_select(true);
            }
        }
    }

    @Override
    public void release(GraphControl gc, Point pos) {
    }

    @Override
    public void draw(GraphControl gc, Graphics g) {
    }

    @Override
    public void mouseMoved(GraphControl gc, Point pos) {
    }
}
