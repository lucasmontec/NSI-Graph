/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nsigraph;

import java.awt.*;

/**
 *
 * @author Lucas
 */
public class GVertexView {

    //A enum to store the states
    enum STATE {

        MOUSEOVER, SELECTED, IDLE
    }
    public static int size = 40;
    public static boolean size_for_degree = false;
    private int usedSize = 40;
    private boolean script_selected_draw;
    private Point center;
    private GVertex vertex;
    public final Color defaultScriptColor = Color.orange;
    public Color scriptColor = Color.orange;
    private Color clr_mouseOver = Color.YELLOW, clr_selected = Color.RED;
    private Color faded = new Color(160, 160, 160, 180);
    private int s2 = 0;
    public boolean render = true;
    //TODO add an image
    //States
    STATE state = STATE.IDLE;

    public GVertexView() {
        vertex = null;
        script_selected_draw = false;
        this.center = null;
    }
    
    public GVertexView(Point center, GVertex v) {
        vertex = v;
        script_selected_draw = false;
        this.center = center;
    }

    public void script_select(boolean t) {
        script_selected_draw = t;
    }

    public boolean isScriptSelected() {
        return script_selected_draw;
    }

    /**
     * Paint the graph on the screen
     *
     * @param g Paint on this graph component
     * @param size Paint with this size
     */
    public void paint(Graphics g) {
        if(size_for_degree && vertex.getDegree() > 0){
            usedSize = vertex.getDegree()*10;
        }else{
            usedSize = size;
        }
        
        if (!render) {
            if (g instanceof Graphics2D) {
                ((Graphics2D) g).setStroke(new BasicStroke(1));
            }
            //Draw a blank oval behind all
            g.setColor(faded);
            g.drawOval(center.x - usedSize / 2, center.y - usedSize / 2, usedSize, usedSize);
            if (vertex.getVertexTitle() != null && vertex.getVertexTitle().length() > 0) {
                // get metrics from the graphics
                FontMetrics metrics = g.getFontMetrics(g.getFont());
                int width = metrics.stringWidth(vertex.getVertexTitle());
                int height = metrics.getHeight();
                g.setColor(faded);
                g.drawRoundRect(center.x - (width / 2) - 4, center.y - height / 2, width + 8, height, 5, 5);
                g.drawString(vertex.getVertexTitle(), center.x - (width / 2), center.y + height / 4);
            }
        } else {
            //Draw node circle
            if (center != null) {
                //Draw a blank oval behind all
                g.setColor(Color.white);
                g.fillOval(center.x - usedSize / 2, center.y - usedSize / 2, usedSize, usedSize);

                //Set the correct color
                if (state.equals(STATE.MOUSEOVER)) {
                    g.setColor(clr_mouseOver);
                } else if (state.equals(STATE.SELECTED)) {
                    g.setColor(clr_selected);
                }

                //Draw the indicative oval behind 10% bigger
                if (!state.equals(STATE.IDLE)) {
                    s2 = (int) (usedSize * 1.1f);
                    if (g instanceof Graphics2D) {
                        ((Graphics2D) g).setStroke(new BasicStroke(5));
                    }
                    g.drawOval(center.x - s2 / 2, center.y - s2 / 2, s2, s2);
                }

                //Draw the vertex image (a black oval for now)
                if (!script_selected_draw) {
                    g.setColor(Color.black);
                    if (g instanceof Graphics2D) {
                        ((Graphics2D) g).setStroke(new BasicStroke(2));
                    }
                    g.drawOval(center.x - usedSize / 2, center.y - usedSize / 2, usedSize, usedSize);
                } else {
                    g.setColor(scriptColor);
                    if (g instanceof Graphics2D) {
                        ((Graphics2D) g).setStroke(new BasicStroke(4));
                    }
                    g.drawOval(center.x - usedSize / 2, center.y - usedSize / 2, usedSize, usedSize);
                }
                if (vertex.getVertexTitle() != null && vertex.getVertexTitle().length() > 0) {
                    // get metrics from the graphics
                    FontMetrics metrics = g.getFontMetrics(g.getFont());
                    int width = metrics.stringWidth(vertex.getVertexTitle());
                    int height = metrics.getHeight();
                    g.setColor(Color.white);
                    g.fillRoundRect(center.x - (width / 2) - 4, center.y - height / 2, width + 8, height, 5, 5);
                    g.setColor(Color.black);
                    g.drawRoundRect(center.x - (width / 2) - 4, center.y - height / 2, width + 8, height, 5, 5);
                    g.drawString(vertex.getVertexTitle(), center.x - (width / 2), center.y + height / 4);
                }
            }
        }
    }

    /**
     * Set the vertex model title
     *
     * @param t The title to be set
     */
    public void setTitle(String t) {
        vertex.setVertexTitle(t);
    }

    public String getTitle() {
        return vertex.getVertexTitle();
    }

    public String getID() {
        return vertex.getID();
    }

    public Point getCenter() {
        return center;
    }

    public GVertex getVertex() {
        return vertex;
    }

    public void setCenter(Point center) {
        this.center = center;
    }

    public Color getColorMouseOver() {
        return clr_mouseOver;
    }

    public void setColorMouseOver(Color clr_mouseOver) {
        this.clr_mouseOver = clr_mouseOver;
    }

    public Color getColorSelected() {
        return clr_selected;
    }

    public void setColorSelected(Color clr_selected) {
        this.clr_selected = clr_selected;
    }

    public void resetScriptColor() {
        scriptColor = defaultScriptColor;
    }

    public void setScriptColor(Color scriptColor) {
        this.scriptColor = scriptColor;
    }
    
}
