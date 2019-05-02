/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package L3_C5_thgraphes_pathfinding;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author franc
 */
public class L3_C5_main {

    
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        /*L3_C5_Dijsktra dij = new L3_C5_Dijsktra(new L3_C5_Graph("L3_C5_slide33.txt"));
        dij.process();
        dij.print();*/
        testAllGraphs();
    }
    
    
    
    private static void testAllGraphs()
    {
        /*
        //L3_C5_Graph g = new L3_C5_Graph("L3_C5_g02.txt");
        L3_C5_Graph g = new L3_C5_Graph("L3_C5_g02.txt");
        /*g.printStructure();
        g.printAdjMatrix();
        g.printIncidenceMatrix();
        //L3_C5_dijsktra dij = new L3_C5_Dijsktra(g);
        
        L3_C5_Bellman bell = new L3_C5_Bellman(g,0);
        bell.process();
        bell.print();
        //g.CalculateAndShowPathfinding(L3_C5_PathAlgorithm.Dijkstra, true);*/
        for(int i = 1; i <= 10; i++)
        {
            System.out.println("## Graphe n "+i+" ##");
            String id = String.format("%02d", i);
            L3_C5_Graph g = new L3_C5_Graph("L3_C5_g"+id+".txt");
            int vc = g.getVertexCount();
            for(int j = 0; j < vc; j++)
            {
                System.out.println("== Graphe n "+i+" | Sommet n "+j+" ==");
                L3_C5_Bellman bell = new L3_C5_Bellman(g,j);
                bell.process();
                bell.print();
            }

           // pressAnyKeyToContinue();
        }
        
    }
    
     private static void pressAnyKeyToContinue()
 { 
        System.out.println("Press Enter key to continue...");
        try
        {
            System.in.read();
        }  
        catch(Exception e)
        {}  
 }

   
}
