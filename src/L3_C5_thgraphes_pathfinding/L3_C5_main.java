/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package L3_C5_thgraphes_pathfinding;

/**
 *
 * @author franc
 */
public class L3_C5_main {

    
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) 
    {
        testGraphs(new String[]{"L3_C5_8.txt"});    
        testAllGraphs();
    }
    
    
    
    private static void testAllGraphs()
    {
        testGraphs(new String[]{"L3_C5_1.txt","L3_C5_2.txt","L3_C5_3.txt" ,"L3_C5_4.txt","L3_C5_5.txt","L3_C5_6.txt",/*"L3_C5_7.txt",*/"L3_C5_8.txt",
            "L3_C5_9.txt"/*,"L3_C5_10.txt"*/,"L3_C5_sampleGraph.txt","L3_C5_sampleGraph_1.txt","L3_C5_slide33.txt","L3_C5_slide43.txt"});
        //L3_C5_Graph g = new L3_C5_Graph("L3_C5_g02.txt");
//        L3_C5_Graph g = new L3_C5_Graph("L3_C5_g02.txt");
        /*g.printStructure();
        g.printAdjMatrix();
        g.printIncidenceMatrix();*/
        //L3_C5_dijsktra dij = new L3_C5_Dijsktra(g);
        
//        L3_C5_Bellman bell = new L3_C5_Bellman(g,0);
//        bell.process();
//        bell.print();
        //g.CalculateAndShowPathfinding(L3_C5_PathAlgorithm.Dijkstra, true);
    }
    
    /**
     * Affiche le tableau de recherche de chemin pour un ensemble de graphes avec pour sommet de départ le sommet d'identifiant 0
     * Le choix de Bellman ou Dijkstra est fait automatiquement
     * @param graphPaths  L'ensemble des chemins des graphes à analyser
     */
    private static void testGraphs(String[] graphPaths)
    {
        L3_C5_Graph[] graphs = new L3_C5_Graph[graphPaths.length];
        for (int i =0; i< graphPaths.length ;i++) {
            System.out.print("Initialisation graphe : "  + (i+1) + " " +  graphPaths[i]  +"\t\t...");
            try {
                if(!graphPaths[i].endsWith(".txt"))
                    graphPaths[i] = graphPaths[i]+=".txt";
                graphs[i] = new L3_C5_Graph(graphPaths[i]);
            } 
            catch (Exception e) 
            {
                System.err.print("Echec" + System.lineSeparator());
                System.out.println(e);
                System.exit(-1);
            }
            System.out.print("OK"+ System.lineSeparator());
        }
        
        for (L3_C5_Graph graph : graphs) 
        {
            
            graph.testPathfinding(0);
        }
   
    }

   
}
