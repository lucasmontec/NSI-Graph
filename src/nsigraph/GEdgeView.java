/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nsigraph;

import java.awt.*;
import java.util.Objects;

/**
 *
 * @author Lucas
 */
public class GEdgeView {
    //A enum to store the states

    enum STATE {

        MOUSEOVER, SELECTED, IDLE
    }
    public static boolean drawTargets = true;
    private boolean script_selected_draw = false;
    private GVertexView origin;
    private GVertexView target;
    private GEdge edge;
    public final Color defaultScriptColor = Color.orange;
    private Color clr_mouseOver = Color.BLUE, clr_selected = Color.RED, clr_script = Color.ORANGE;
    public boolean render = true;
    private Color faded = new Color(160, 160, 160, 180);
    //States
    STATE state = STATE.IDLE;

    public GEdgeView(GEdge e, GVertexView vorigin, GVertexView vtarget) {
        origin = vorigin;
        target = vtarget;
        edge = e;
    }

    public void script_select(boolean t) {
        script_selected_draw = t;
    }

    public boolean isScriptSelected() {
        return script_selected_draw;
    }

    public GVertexView getTarget() {
        return target;
    }

    public GVertexView getOrigin() {
        return origin;
    }

    public GEdge getEdge() {
        return edge;
    }
  
    public void resetScriptColor() {
        clr_script = defaultScriptColor;
    }

    public void setScriptColor(Color scriptColor) {
        this.clr_script = scriptColor;
    }

    public Point getCenter() {
        return new Point((origin.getCenter().x + target.getCenter().x) / 2,
                (origin.getCenter().y + target.getCenter().y) / 2);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }

        if (!(o instanceof GEdgeView)) {
            return false;
        }

        GEdge oc = ((GEdgeView) o).edge;

        return this.edge.equals(oc);
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + Objects.hashCode(this.edge);
        return hash;
    }

    /**
     * Paint the graph on the screen
     *
     * @param g Paint on this graph component
     * @param size Paint with this size
     */
    public void paint(Graphics g) {
        if (!render) {
            g.setColor(faded);
            //Draw normally for graph viewing
            if (g instanceof Graphics2D) {
                ((Graphics2D) g).setStroke(new BasicStroke(1));
            }
            g.drawLine(origin.getCenter().x, origin.getCenter().y, target.getCenter().x, target.getCenter().y);
        } else {
            //Draw node circle
            if (origin != null && target != null) {
                if (state.equals(STATE.MOUSEOVER)) {
                    g.setColor(clr_mouseOver);
                } else if (state.equals(STATE.SELECTED)) {
                    g.setColor(clr_selected);
                } else {
                    g.setColor(Color.black);
                }

                //Draw the edge line
                if (!script_selected_draw) {
                    //Draw normally for graph viewing
                    if (g instanceof Graphics2D) {
                        ((Graphics2D) g).setStroke(new BasicStroke(2));
                    }
                    g.drawLine(origin.getCenter().x, origin.getCenter().y, target.getCenter().x, target.getCenter().y);
                } else {
                    //Draw for script selection
                    g.setColor(clr_script);
                    if (g instanceof Graphics2D) {
                        ((Graphics2D) g).setStroke(new BasicStroke(4));
                    }
                    g.drawLine(origin.getCenter().x, origin.getCenter().y, target.getCenter().x, target.getCenter().y);
                }

                //Draw the cost
                if (edge.hasCost()) {
                    int cx = (origin.getCenter().x + target.getCenter().x) / 2;
                    int cy = (origin.getCenter().y + target.getCenter().y) / 2;
                    // get metrics from the graphics
                    FontMetrics metrics = g.getFontMetrics(g.getFont());
                    int width = metrics.stringWidth(Float.toString(edge.getCost()));
                    int height = metrics.getHeight();
                    g.setColor(Color.white);
                    g.fillRoundRect(cx - (width / 2) - 4, cy - height / 2, width + 8, height, 5, 5);
                    if (!script_selected_draw) 
                        g.setColor(Color.black);
                    else
                        g.setColor(clr_script);
                    g.drawRoundRect(cx - (width / 2) - 4, cy - height / 2, width + 8, height, 5, 5);
                    g.drawString(Float.toString(edge.getCost()), cx - (width / 2), (int)(cy + height*0.35f));
                }

                g.setColor(Color.black);

                //Draw the arrow
                if (target != null && drawTargets) {
                    float tx = target.getCenter().x;
                    float ty = target.getCenter().y;
                    float ox = origin.getCenter().x;
                    float oy = origin.getCenter().y;
                    float r = 1.02f * (GVertexView.size / 2);
                    float theta = (float) Math.atan2((tx - ox), (ty - oy));

                    float x = (float) (tx - Math.sin(theta) * r) - 3;
                    float y = (float) (ty - Math.cos(theta) * r) - 3;

                    g.fillOval((int) x, (int) y, 9, 9);
                }
            }
        }
    }
}
