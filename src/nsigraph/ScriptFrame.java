/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nsigraph;

import bsh.EvalError;
import bsh.Interpreter;
import java.awt.Color;
import java.awt.Desktop;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.SwingWorker;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 *
 * @author Lucas
 */
public class ScriptFrame extends JFrame {

    DefaultListModel model;
    private final Interpreter interpreter = new Interpreter();
    File files[];
    File scriptsDir;
    Console console;
    ScriptHelper helper = new ScriptHelper();
    boolean isStepped = false;

    /**
     * Creates new form ScriptFrame
     */
    public ScriptFrame() {
        initComponents();

        scriptsDir = new File("scripts");
        this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        console = new Console(scriptconsole);
        gpnl_panel1.setTool(new SelectTool());
        this.setLocationRelativeTo(null);

        //Default interpreter->script variables
        try {
            interpreter.set("helper", helper);
            interpreter.set("console", console);
        } catch (EvalError ex) {
            Logger.getLogger(ScriptFrame.class.getName()).log(Level.SEVERE, null, ex);
        }

        btn_step.setEnabled(false);
        btn_runAlg.setEnabled(false);
        lst_algs.setComponentPopupMenu(pup_scriptOptions);
    }

    public void show(GraphControl gc) {
        gpnl_panel1.setGraphController(gc);
        listAlgorithms();
        prepareListListener();
        this.setVisible(true);
    }

    private void prepareListListener() {
        lst_algs.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    if (lst_algs.getSelectedValue() != null) {
                        btn_runAlg.setEnabled(true);
                        if (((String) lst_algs.getSelectedValue()).contains("step")) {
                            isStepped = true;
                        } else {
                            isStepped = false;
                            btn_step.setEnabled(false);
                        }
                    } else {
                        btn_runAlg.setEnabled(false);
                    }
                }
            }
        });
    }

    private void listAlgorithms() {
        //Say bedug stuff
        //System.out.println("Opening scripts...");
        //Get all files in a certain directory
        if (scriptsDir == null || !scriptsDir.exists()) {
            scriptsDir = new File("scripts");
        }

        //Say bedug stuff
        //System.out.println("Created 100 file store array...");
        //Files in the dir - max 100
        files = new File[100];

        //Say bedug stuff
        //System.out.println("Checking if script directory exists...");
        //Check if directory exists
        if (scriptsDir.exists()) {
            //Say bedug stuff
            //System.out.println("Directory exists: "+scriptsDir.getAbsolutePath());

            //Say bedug stuff
            //System.out.println("Getting first 100 files...");
            //Get all files up to 100
            String subfilenames[] = scriptsDir.list();
            if (subfilenames != null && subfilenames.length > 0) {
                int subfilecount = scriptsDir.list().length;
                //System.out.println("Found potential scripts: "+subfilecount);
                if (subfilecount > 100) {
                    subfilecount = 100;
                }

                //Create a model to our list
                model = new DefaultListModel();

                //Run and get all files
                for (int i = 0; i < subfilecount; i++) {
                    //Add all scripts to the files list
                    if (subfilenames[i].endsWith(".bsh")) {
                        //Say wat are the found scripts
                        //System.out.println("Found script: "+subfilenames[i]);
                        //Store the file paths
                        files[i] = new File(scriptsDir.getAbsolutePath() + "/" + subfilenames[i]);
                        //System.out.println("Saving as: "+files[i].getAbsolutePath());
                        //Store the fila names
                        model.addElement(subfilenames[i].replace(".bsh", ""));
                    }
                }

                //Set the model to the list
                lst_algs.setModel(model);
            } else {
                //Say bedug stuff
                System.out.println("No files in the scriprt directory!");
            }
        } else {
            btn_runAlg.setEnabled(false);
            System.err.println("Couldn't find script directory: " + scriptsDir.getAbsolutePath());
        }
    }

    private File getScriptFile(String scriptName) {
        if (scriptName != null && scriptName.length() > 0) {
            for (int i = 0; i < files.length; i++) {
                if (files[i] != null) {
                    if (files[i].getAbsolutePath().contains(scriptName)) {
                        return files[i];
                    }
                }
            }
        }
        return null;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pup_scriptOptions = new javax.swing.JPopupMenu();
        itm_openScriptNp = new javax.swing.JMenuItem();
        jSplitPane1 = new javax.swing.JSplitPane();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        scriptconsole = new javax.swing.JTextArea();
        btn_clearSel = new javax.swing.JButton();
        btn_runAlg = new javax.swing.JButton();
        btn_step = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        gpnl_panel1 = new nsigraph.GraphPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        lst_algs = new javax.swing.JList();

        itm_openScriptNp.setText("Open in editor");
        itm_openScriptNp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itm_openScriptNpActionPerformed(evt);
            }
        });
        pup_scriptOptions.add(itm_openScriptNp);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jSplitPane1.setDividerLocation(260);
        jSplitPane1.setDividerSize(7);
        jSplitPane1.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);

        scriptconsole.setEditable(false);
        scriptconsole.setBackground(new java.awt.Color(0, 0, 0));
        scriptconsole.setColumns(20);
        scriptconsole.setForeground(new java.awt.Color(153, 204, 0));
        scriptconsole.setLineWrap(true);
        scriptconsole.setRows(5);
        jScrollPane2.setViewportView(scriptconsole);

        btn_clearSel.setText("Limpar seleção script");
        btn_clearSel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_clearSelbtn_action_runAlg(evt);
            }
        });

        btn_runAlg.setText("Rodar");
        btn_runAlg.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_action_runAlg(evt);
            }
        });

        btn_step.setText("Rodar passo");
        btn_step.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_stepActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btn_runAlg, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btn_clearSel, javax.swing.GroupLayout.DEFAULT_SIZE, 208, Short.MAX_VALUE)
                    .addComponent(btn_step, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 506, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jScrollPane2)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(btn_clearSel, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn_step, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn_runAlg, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );

        jSplitPane1.setRightComponent(jPanel2);

        jPanel1.setMinimumSize(new java.awt.Dimension(50, 300));

        javax.swing.GroupLayout gpnl_panel1Layout = new javax.swing.GroupLayout(gpnl_panel1);
        gpnl_panel1.setLayout(gpnl_panel1Layout);
        gpnl_panel1Layout.setHorizontalGroup(
            gpnl_panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 511, Short.MAX_VALUE)
        );
        gpnl_panel1Layout.setVerticalGroup(
            gpnl_panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        lst_algs.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane1.setViewportView(lst_algs);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 207, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(gpnl_panel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(gpnl_panel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 289, Short.MAX_VALUE))
                .addGap(0, 0, 0))
        );

        jSplitPane1.setLeftComponent(jPanel1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSplitPane1, javax.swing.GroupLayout.Alignment.TRAILING)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSplitPane1)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btn_action_runAlg(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_action_runAlg
        //Try to run the algorithm
        File fileToRun = getScriptFile((String) lst_algs.getSelectedValue());

        //Update graph degrees
        gpnl_panel1.getControl().getGraph().updateAllVerticesDegrees();

        //If the file is not null then run
        if (fileToRun != null) {
            //Clear the script selection
            if (gpnl_panel1.getControl() != null) {
                gpnl_panel1.getControl().clearScriptSelection();
                gpnl_panel1.repaint();
            }

            //If is stepped allow stepping
            if (isStepped) {
                btn_step.setEnabled(true);
            }

            ScriptRunner runner = new ScriptRunner(console, interpreter, gpnl_panel1, fileToRun, btn_step, btn_runAlg);
            runner.execute();

            //Update graph degrees
            gpnl_panel1.getControl().getGraph().updateAllVerticesDegrees();
            gpnl_panel1.repaint();
        }
    }//GEN-LAST:event_btn_action_runAlg

    private void btn_clearSelbtn_action_runAlg(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_clearSelbtn_action_runAlg
        if (gpnl_panel1.getControl() != null) {
            gpnl_panel1.getControl().clearScriptSelection();
            gpnl_panel1.repaint();
        }
    }//GEN-LAST:event_btn_clearSelbtn_action_runAlg

    private void btn_stepActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_stepActionPerformed
        helper.step();
    }//GEN-LAST:event_btn_stepActionPerformed

    private void itm_openScriptNpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itm_openScriptNpActionPerformed
        File fileToEdit = getScriptFile((String) lst_algs.getSelectedValue());

        if (fileToEdit != null) {
            //Just splendid!!!!
            //From: http://stackoverflow.com/questions/6273221/open-a-text-file-in-the-default-text-editor-via-java
            //A guy called: Nathan Hughes
            try {
                if (System.getProperty("os.name").toLowerCase().contains("windows")) {
                    String cmd = "rundll32 url.dll,FileProtocolHandler " + fileToEdit.getCanonicalPath();
                    Runtime.getRuntime().exec(cmd);
                } else {
                    Desktop.getDesktop().edit(fileToEdit);
                }
            } catch (IOException ex) {
                Logger.getLogger(ScriptFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else{
            JOptionPane.showMessageDialog(null, "Selecione um algorítimo primeiro!");
        }
    }//GEN-LAST:event_itm_openScriptNpActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_clearSel;
    private javax.swing.JButton btn_runAlg;
    private javax.swing.JButton btn_step;
    private nsigraph.GraphPanel gpnl_panel1;
    private javax.swing.JMenuItem itm_openScriptNp;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JList lst_algs;
    private javax.swing.JPopupMenu pup_scriptOptions;
    private javax.swing.JTextArea scriptconsole;
    // End of variables declaration//GEN-END:variables

    public class Console {

        JTextArea printArea = null;

        public Console(JTextArea ja) {
            printArea = ja;
        }

        public void clear() {
            printArea.setText("");
        }

        public void info(String text) {
            printArea.setForeground(Color.green);
            printArea.append(text + "\n");
        }

        public void err(String err) {
            printArea.setForeground(Color.red);
            printArea.append(err + "\n");
        }
    }

    static class ScriptRunner extends SwingWorker<Void, Void> {

        private final Interpreter interpreter;
        GraphPanel gpanel;
        Console console;
        File fileToRun;
        JButton btn_step;
        JButton btn_run;

        public ScriptRunner(Console c, Interpreter inter, GraphPanel gp, File f, JButton btn_step, JButton btn_rn) {
            super();
            interpreter = inter;
            console = c;
            gpanel = gp;
            fileToRun = f;
            btn_run = btn_rn;
            this.btn_step = btn_step;
        }

        @Override
        protected Void doInBackground() throws Exception {
            //Prevent the retarded users from winning
            btn_run.setEnabled(false);

            //Try to run the algorithm
            try {
                console.clear();
                interpreter.set("control", gpanel.getControl());
                interpreter.set("panel", gpanel);
                interpreter.set("root", ((SelectTool) gpanel.getTool()).root);
                interpreter.set("target", ((SelectTool) gpanel.getTool()).target);
                interpreter.source(fileToRun.getPath());
            } catch (FileNotFoundException ex) {
                Logger.getLogger(ScriptFrame.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(ScriptFrame.class.getName()).log(Level.SEVERE, null, ex);
            } catch (EvalError ex) {
                if (ex != null) {
                    JOptionPane.showMessageDialog(null,
                            ex.getRawMessage() + ex.getScriptStackTrace(), "Script error!",
                            JOptionPane.ERROR_MESSAGE);
                    JOptionPane.showMessageDialog(null,
                            ex.getMessage(), "Script error!",
                            JOptionPane.ERROR_MESSAGE);
                }
                Logger.getLogger(ScriptFrame.class.getName()).log(Level.SEVERE, null, ex);
            }

            //Restore button states
            btn_step.setEnabled(false);
            btn_run.setEnabled(true);

            //Repaint
            gpanel.repaint();

            //Return no shit at all
            return null;
        }
    }
}
