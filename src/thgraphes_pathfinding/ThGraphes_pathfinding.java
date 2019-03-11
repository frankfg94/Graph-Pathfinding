/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thgraphes_pathfinding;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author franc
 */
public class ThGraphes_pathfinding {

    
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        TestAllGraphs();
    }
    
    
    private static void TestAllGraphs()
    {
        Graph g = new Graph("C:\\Users\\franc\\OneDrive\\Documents\\NetBeansProjects\\ThGraphes_pathfinding\\src\\thgraphes_pathfinding\\sampleGraph.txt");
        g.printStructure();
        g.printAdjMatrix();
        g.CalculatePathfinding(PathAlgorithm.Dijkstra, true);
    }
   
}
