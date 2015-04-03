/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nsigraph;

import java.util.Iterator;
import java.util.Map;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Lucas
 */
public class ProprietiesModel extends DefaultTableModel {

    //Store the map we are messing with
    private Map map;

    public ProprietiesModel(Map map) {
        //Create a table model
        super(new Object[]{"Propriedade", "Valor"}, 0);

        //Store map
        this.map = map;

        //Populate the model
        populate();
        
        //Prepare the model to self update
        prepareSelfUpdater();
    }

    /**
     * A method to allow STA's (solução técnica alternativa/ POG)
     */
    private void populate(){
        //Populate the table
        for (Iterator it = map.entrySet().iterator(); it.hasNext();) {
            Map.Entry entry = (Map.Entry) it.next();
            this.addRow(new Object[]{entry.getKey(), entry.getValue()});
        }
    }
    
    /**
     * Add a self updater
     */
    private void prepareSelfUpdater() {
        this.addTableModelListener(new TableModelListener() {
            
            @Override
            public void tableChanged(TableModelEvent e) {
                //Prevent changing keys - STA
                if(e.getColumn() == 0){
                    for(int i=0;i<getRowCount();i++)
                        removeRow(i);
                    populate();
                }
                
                //Move the changes correctly
                for (int i = 0; i < getRowCount(); i++) {
                    if (map.containsKey(getValueAt(i, 0))) {
                        map.put(getValueAt(i, 0), getValueAt(i, 1));
                    }
                }
            }
        });
    }
}
