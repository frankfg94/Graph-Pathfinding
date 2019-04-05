/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package L3_C5_thgraphes_pathfinding;

import java.util.ArrayList;



/**
 *
 * @author franc
 */
public class L3_C5_Arc 
{
    public Vertex initialExtremity; // pas utilisé pour l'instant à par dans le constructeur L3_C5_Arc(int ID, Vertex initExtremity, Vertex termExtremity)
    public Vertex terminalExtremity; // pas utilisé pour l'instant à par dans le constructeur L3_C5_Arc(int ID, Vertex initExtremity, Vertex termExtremity)
    public int initExtremityValue = -1;
    public int termExtremityValue = -1;
    public int value = -1; // Nombre qui identifie notre sommet
    
    public L3_C5_Arc(int value, Vertex initExtremity, Vertex termExtremity)
    {
        this.value=value;
        this.initialExtremity = initExtremity;
        this.terminalExtremity = termExtremity;
        this.initExtremityValue = initExtremity.ID;
        this.termExtremityValue = termExtremity.ID;
    }
    
    public L3_C5_Arc(int value, int initExtremityValue, int termExtremityValue)
    {
        this.value = value;
        this.initExtremityValue = initExtremityValue;
        this.termExtremityValue = termExtremityValue;
    }
    
    @Override
    public String toString()
    {
        return String.format("(%d)--(%d)-->(%d)",initExtremityValue,value,termExtremityValue);
    }
    
//    public static ArrayList<L3_C5_Arc> findWithValue(ArrayList<L3_C5_Arc> arcs)
//    {
//       
//    }

    public Vertex getInitialExtremity(L3_C5_Graph graph) 
    {
      return Vertex.FindVertexWithValue(initExtremityValue, graph);
    }
    
    public Vertex getTerminalExtremity(L3_C5_Graph graph) 
    {
      return Vertex.FindVertexWithValue(termExtremityValue, graph);
    }
}
