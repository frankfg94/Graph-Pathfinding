/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package theorie_graphe;
import java.util.ArrayList;


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
        
       testAllGraphs();

      /*ArrayList<Integer> path = find_path_bellman(g,0,3);
    print_path(path);*/
    }
    
    
    private static void testAllGraphs()
    {

        testGraphs(new String[]{"L3_C5_1.txt","L3_C5_2.txt","L3_C5_3.txt" ,"L3_C5_4.txt","L3_C5_5.txt","L3_C5_6.txt","L3_C5_7.txt","L3_C5_8.txt",
        "L3_C5_9.txt","L3_C5_10.txt","L3_C5_sampleGraph.txt","L3_C5_sampleGraph_1.txt","L3_C5_slide33.txt","L3_C5_slide43.txt"});
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
            System.out.print("Initialisation graphe n° "  + (i+1) + " : " +  graphPaths[i]  +"\t\t...");
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
            for(int sommetID = 0; sommetID < graph.getVertexCount() ; sommetID++)
            graph.testPathfinding(sommetID);
        }
   
    }

    private static ArrayList<Integer> find_path_bellman(L3_C5_Graph g, int src, int dst)
    {
        L3_C5_Bellman bell = new L3_C5_Bellman(g, src);
        bell.process();
        bell.print();
        return bell.get_path(dst);
    }

    private static void print_path(ArrayList<Integer> lst)
    {
        if(lst == null)
        {
            System.out.println("Aucun chemin le plus cours n'a été trouvé");
            return;
        }
        for(int i = 0; i < lst.size(); i++)
        {
            System.out.print(lst.get(i));
            if(i != lst.size() - 1)
                System.out.print(" --> ");
        }
        System.out.println("");
    }

    private static String print_path_graphviz(L3_C5_Graph g,ArrayList<Integer> lst)
    {
        if(lst == null)
            return null;
        String dg = "digraph {\n";

        for(int i = 0; i < g.allArcs.size(); i++)
        {
            L3_C5_Arc a = g.allArcs.get(i);
            dg += a.initExtremityValue + " -> " + a.termExtremityValue;
            dg += "[label=\""+a.value+"\",weight=\""+a.value+"\"";
            for(int j = 0; j < lst.size() - 1; j++)
            {
                if(a.initExtremityValue == lst.get(j) && a.termExtremityValue == lst.get(j + 1))
                {
                    dg += ",color=red,penwidth=3.0";
                }
            }
            dg += "];\n";
        }
        dg += "}\n";

        return dg;
    }

   
}
