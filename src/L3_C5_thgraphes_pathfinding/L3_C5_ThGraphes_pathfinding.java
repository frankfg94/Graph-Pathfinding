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
public class L3_C5_ThGraphes_pathfinding {

    
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        TestAllGraphs();
    }
    
    
    private static void TestAllGraphs()
    {
        L3_C5_Graph g = new L3_C5_Graph("L3_C5_sampleGraph.txt");
        g.printStructure();
        g.printAdjMatrix();
        g.printIncidenceMatrix();
        //g.CalculateAndShowPathfinding(L3_C5_PathAlgorithm.Dijkstra, true);
    }
   
}