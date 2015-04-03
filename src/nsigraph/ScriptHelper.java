package nsigraph;

import java.awt.Color;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * To use in the scripts...
 *
 * @author Lucas M Carvalhaes
 */
public class ScriptHelper {

    public final Color lookingAtColor = new Color(50, 50, 255);
    public final Color negateColor = new Color(255, 50, 50);
    private final Object lock = new Object();

    /**
     * Stops the thread for a certain time in ms.
     *
     * @param ms How many MS should we wait?
     */
    public void delay(int ms) {
        //Relax a little
        try {
            Thread.sleep(ms);
        } catch (InterruptedException ex) {
            Logger.getLogger(GMLIO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Call this at a step point in your script
     */
    @SuppressWarnings("empty-statement")
    public void stepPoint() {
        synchronized (lock) {
            try {
                lock.wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    /**
     * To be used in the script frame to step once in the script
     */
    public void step() {
        synchronized (lock) {
            lock.notify();
        }
    }

}
