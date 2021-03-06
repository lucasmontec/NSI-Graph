/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nsigraph;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

/**
 *
 * @author Lucas
 */
public class GraphPanel extends javax.swing.JPanel {

    //Store a graphview to draw graphs
    private GraphControl control;
    //Create a cursor
    Tool cursor = null;
    //Need for antialiasing
    Graphics2D g2d;
    //Loading graph status
    private boolean loadingGraph = false;
    private float loadingProgress = 0f;

    /**
     * Creates new form GraphPanel and create a fresh graph to work with
     */
    public GraphPanel() {
        initComponents();
        setDoubleBuffered(true);
    }

    public void setGraphController(GraphControl gc) {
        control = gc;
    }

    /**
     * Creates a buffered image from the current content in the panel.
     * @return A buffered image from the current graph in the panel
     */
    public BufferedImage getImage(){
        BufferedImage ret = new BufferedImage(this.getWidth(), this.getHeight(), BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics = ret.createGraphics();
        this.paintAll(graphics);
        return ret;
    }
    
    @Override
    public void paint(Graphics g) {
        if (!loadingGraph) {
            //Set rendering hints
            if (g != null && g instanceof Graphics2D) {
                g2d = (Graphics2D) g;
            }
            
            //Get away null
            if(g == null)
                return;

            if (g2d != null) {
                g2d.setRenderingHint(
                        RenderingHints.KEY_ANTIALIASING,
                        RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setRenderingHint(
                        RenderingHints.KEY_TEXT_ANTIALIASING,
                        RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
            }

            //Draw bg
            g.setColor(this.getBackground());
            g.fillRect(0, 0, this.getWidth(), this.getHeight());

            //Use control
            if (control != null) {
                //Draw all nodes
                if (control.getView() != null) {
                    control.getView().paint(g);
                }

                //Draw graph debug info
                //Update - now this info has a propper menu
               /* if (control.getView() != null) {
                    g.drawString(control.getGraph().toString(), 5, 15);
                }*/
            }
            //Draw the tool
            if (cursor != null) {
                cursor.draw(control, g);
            }
        }else{ //We are now loading a graph in a separate task
            g.setColor(new Color(100,100,190));
            g.fillRect(0, 0, getWidth(), getHeight());
            
            //Loading text
            g.setColor(Color.white);
            g.drawString("Loading...", 20, 20);
            
            //Progress bar
            g.drawRect(20, 25, (int)(getWidth()*0.6f), 10);
            g.fillRect(22, 27, (int)(loadingProgress*getWidth()*0.6f)-4, 6);
        }
    }

    public float getLoadingProgress() {
        return loadingProgress;
    }

    public void setLoadingProgress(float loadingProgress) {
        this.loadingProgress = loadingProgress;
    }

    public void setTool(Tool t) {
        cursor = t;
    }

    public Tool getTool() {
        return cursor;
    }

    public GraphControl getControl(){
        return control;
    }
    
    public boolean isLoadingGraph() {
        return loadingGraph;
    }

    public void setLoadingGraph(boolean loadingGraph) {
        this.loadingGraph = loadingGraph;
    }

    public void refreshContent() {
        repaint();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setBackground(new java.awt.Color(255, 255, 255));
        setForeground(new java.awt.Color(102, 102, 102));
        setCursor(new java.awt.Cursor(java.awt.Cursor.CROSSHAIR_CURSOR));
        setName("Graph Panel");
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                toolPress(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                toolRelease(evt);
            }
        });
        addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                toolMoved(evt);
            }
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                toolMoved(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void toolPress(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_toolPress
        if (cursor != null) {
            cursor.press(control, evt.getPoint(), evt.getButton());
            refreshContent();
        }
    }//GEN-LAST:event_toolPress

    private void toolRelease(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_toolRelease
        if (cursor != null) {
            cursor.release(control, evt.getPoint());
            refreshContent();
        }
    }//GEN-LAST:event_toolRelease

    private void toolMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_toolMoved
        if (cursor != null) {
            cursor.mouseMoved(control, evt.getPoint());
            refreshContent();
        }
    }//GEN-LAST:event_toolMoved
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables

    void setDrawTargets(boolean tr) {
        GEdgeView.drawTargets = tr;
    }

    void setVertexSize(int s) {
        control.getView().setVertexSize(s);
    }

    GraphView getGraphView() {
        return control.getView();
    }
}
