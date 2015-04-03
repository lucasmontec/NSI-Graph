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
public interface ITool {
    public String getTitle();
    public void unload(GraphControl gc);
    public void press(GraphControl gc, Point pos, int button);
    public void release(GraphControl gc, Point pos);
    public void draw(GraphControl gc, Graphics g);
    public void mouseMoved(GraphControl gc, Point pos);
}
