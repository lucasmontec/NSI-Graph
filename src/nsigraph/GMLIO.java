package nsigraph;

import java.io.*;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.SwingWorker;

/**
 * GML parser que cria grafos no modelo do editor
 * através do formato GML.
 * Esse parser também é capaz de salvar grafos em GML.
 *
 * @author Lucas
 */
public abstract class GMLIO {

    /**
     * Salva um grafo para o arquivo no formato GML.
     * @param g O Grafo a ser salvo
     * @param f O Arquivo destino
     * @throws IOException 
     */
    public static void saveGraph(Graph g, File f) throws IOException {
        FileWriter fstream = new FileWriter(f);
        PrintWriter out = new PrintWriter(fstream);
        String author = JOptionPane.showInputDialog("The graph author: ");

        if (author.length() > 0) {
            out.println("Creator \"" + author + "\"");
        }
        out.println("graph");
        out.println("[");

        for (GVertex v : g.getVertices()) {
            out.println("node");
            out.println("[");
            out.println("id " + v.getID().replaceAll("V", ""));
            out.println("label \"" + v.getVertexTitle() + "\"");
            out.println("]");
        }

        for (GEdge e : g.getEdges()) {
            out.println("edge");
            out.println("[");
            out.println("source " + e.getOrigin().getID().replaceAll("V", ""));
            out.println("target " + e.getTarget().getID().replaceAll("V", ""));
            if (e.hasCost()) {
                out.println("value " + e.getCost());
            }
            out.println("]");
        }

        out.println("]");
        out.close();
    }

    private static int countLines(File f) throws IOException {
        int cnt;
        try (LineNumberReader reader = new LineNumberReader(new FileReader(f))) {
            cnt = 0;
            String lineRead = "";
            while ((lineRead = reader.readLine()) != null) {
            }
            cnt = reader.getLineNumber();
        }
        return cnt;
    }

    /**
     * Loads a GML graph from a file to the editor.
     * Async.
     * @param f The GML File that contains the graph
     * @param panel The panel to receive the graph
     * @param control The controller that will make the graph
     * @param gen The view generator to make a GraphView to this graph
     * @return The graph object generated from the GML
     * @throws IOException 
     */
    public static Graph loadGraphFromGMLFile(File f,
            GraphPanel panel, GraphControl control, ViewGenerator gen) throws IOException {
        chill(800);

        //A graph to return
        final Graph graph = new Graph();

        new reader(graph, f, control, panel, countLines(f), gen).execute();

        return graph;
    }

    private static void chill(int ms) {
        //Relax a little
        try {
            Thread.sleep(ms);
        } catch (InterruptedException ex) {
            Logger.getLogger(GMLIO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    static class reader extends SwingWorker<Graph, Void> {

        Graph graph = null;
        GraphControl control;
        GraphPanel panel;
        File f = null;
        int totalLines;
        ViewGenerator gen;

        public reader(Graph g, File f, GraphControl gc,
                GraphPanel pnl, int totalWork, ViewGenerator gn) {
            super();
            graph = g;
            this.f = f;
            panel = pnl;
            control = gc;
            totalLines = totalWork;
            gen = gn;
        }

        @Override
        public void done() {
            //Prevent concurrency
            panel.setLoadingGraph(false);

            //If we could get it then show it
            if (graph != null) {
                //First get the graph view by generating it
                GraphView view = GraphControl.generateViewFromGraph(graph, panel,
                        gen);

                //Then assing a control and update the panel
                control.setGraph(graph);
                control.setView(view);
                //panel.setGraphController(control);

                //Reset loading view
                panel.setLoadingProgress(0f);

                //Repaint
                panel.refreshContent();
            }
        }

        @Override
        protected Graph doInBackground() throws Exception {
            long start = System.currentTimeMillis();
            long linesRead = 0;
            System.out.println("<GML read thread started>");
            System.out.println("<GML parsing start>");
            try (Scanner reader = new Scanner(new FileReader(f))) {
                while (reader.hasNext()) {
                    //Get the entire line
                    String line = reader.nextLine();
                    line = line.trim().replaceAll(" ", "");
                    linesRead++;
                    panel.setLoadingProgress(linesRead * 1f / totalLines * 1f);
                    panel.repaint();

                    //First get the author
                    if (line.contains("Creator")) {
                        //Remove creator and "
                        line = line.replaceAll("Creator", "");
                        line = line.replaceAll("\"", "");

                        //Debug print author
                        System.out.println("Author: " + line);

                        //Line is now the creator name
                        graph.setAuthor(line);
                    }

                    //Now we handle node addition
                    if (line.contains("node")) {
                        //Prepare the node to add
                        GVertex vertex = new GVertex();

                        //Read the entire node
                        while (!line.equals("]")) {
                            //Go to the next line
                            line = reader.nextLine();
                            linesRead++;
                            //Prepare it
                            line = line.trim().replaceAll(" ", "");

                            //Check for ID
                            if (line.startsWith("id")) {
                                //Remove id
                                line = line.replaceAll("id", "");
                                //Read the val
                                int id = Integer.parseInt(line);
                                //Set to the vertex
                                vertex.setID(id);
                            }

                            //Check for label
                            if (line.contains("label")) {
                                //Remove creator and "
                                line = line.replaceAll("label", "");
                                line = line.replaceAll("\"", "");

                                //Read the label
                                vertex.setVertexTitle(line);
                            }
                        }

                        //Add to the graph
                        graph.addVertex(vertex);
                    }

                    //And at last the edge
                    if (line.contains("edge")) {
                        //Prepare the node to add
                        GEdge edge = new GEdge();

                        //Read the entire node
                        while (!line.equals("]")) {
                            //Go to the next line
                            line = reader.nextLine();
                            linesRead++;
                            //Prepare it
                            line = line.trim().replaceAll(" ", "");

                            //Check for source
                            if (line.contains("source")) {
                                //Remove id
                                line = line.replaceAll("source", "");
                                //Read the val
                                int id = Integer.parseInt(line);
                                //Get source vertex
                                GVertex source = graph.getVertexWithID("V" + id);
                                //Set the edge source
                                edge.setOrigin(source);
                            }

                            //Check for target
                            if (line.contains("target")) {
                                //Remove creator and "
                                line = line.replaceAll("target", "");
                                //Read the val
                                int id = Integer.parseInt(line);
                                //Get source vertex
                                GVertex target = graph.getVertexWithID("V" + id);
                                //Set the edge source
                                edge.setTarget(target);
                            }

                            //Check for value
                            if (line.contains("value")) {
                                //Remove creator and "
                                line = line.replaceAll("value", "");
                                //Read the val
                                int id = Integer.parseInt(line);
                                //Set the edge source
                                edge.setCost(id);
                            }
                        }

                        //Add to the graph
                        graph.addEdge(edge);
                    }

                    //Relax a little
                    chill(50);
                }
            }
            System.out.println("<GML read end>");
            System.out.println("<GML total IO time: "
                    + (System.currentTimeMillis() - start) + " ms>");
            //Update graph degrees
            graph.updateAllVerticesDegrees();
            System.out.println("<GML updated all vertice degrees>");
            System.out.println("<Done in "+(System.currentTimeMillis()-start)+"ms>");
            return graph;
        }
    }
}
