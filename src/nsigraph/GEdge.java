package nsigraph;

import java.util.Objects;

/**
 *
 * @author Lucas
 */
public class GEdge {

    private GVertex origin;
    private GVertex target;
    private boolean originToTarget = true;
    private boolean oriented;
    private boolean hasCost;
    private float cost = 0.0f;

    /**
     * Dont use this default. This is for the GML IO!
     */
    public GEdge(){
        this(null, null);
    }
    
    public GEdge(GVertex o, GVertex t) {
        oriented = false;
        origin = o;
        target = t;
        hasCost = false;
    }

    public GEdge(GVertex o, GVertex t, float c) {
        oriented = false;
        origin = o;
        target = t;
        hasCost = true;
        cost = c;
    }

    public void selectTarget(GVertex v) {
        if (v == origin) {
            GVertex buffer = target;
            target = origin;
            origin = buffer;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }

        if (!(o instanceof GEdge)) {
            return false;
        }

        GEdge oc = (GEdge) o;
        
        if (oc.cost != cost) {
            return false;
        }
        
        /**
         * Remove this when i learn how to draw arcs to not simple graphs
         */
        if (!oc.origin.equals(origin) && !oc.origin.equals(target)) {
            return false;
        }
        
        if (!oc.target.equals(target) && !oc.target.equals(origin)) {
            return false;
        }
        
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 83 * hash + Objects.hashCode(this.origin);
        hash = 83 * hash + Objects.hashCode(this.target);
        hash = 83 * hash + Float.floatToIntBits(this.cost);
        return hash;
    }

    public void setCost(float c) {
        if(c > 0)
            hasCost = true;
        cost = c;
    }

    public float getCost() {
        return cost;
    }

    public boolean hasCost() {
        return hasCost;
    }

    public boolean isOriented() {
        return oriented;
    }

    public GVertex getOrigin() {
        return origin;
    }

    public GVertex getTarget() {
        return target;
    }

    public void setOrigin(GVertex origin) {
        this.origin = origin;
    }

    public void setTarget(GVertex target) {
        this.target = target;
    }
    
    public boolean isOriginToTarget() {
        return originToTarget;
    }

    public void setOriginToTarget(boolean originToTarget) {
        this.originToTarget = originToTarget;
    }
    
    
}
