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
public class L3_C5_Arc 
{
    public int initExtremityValue = -1; // identifiant du vertex de départ
    public int termExtremityValue = -1; // identifiant du vertex d'arrivée
    public int value = -1; // valeur du poid de l'arc.
    

    
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

    // retourne l'objet vertex entrant de l'arc
    public L3_C5_Vertex getInitialExtremity(L3_C5_Graph graph) 
    {
      return L3_C5_Vertex.FindVertexWithID(initExtremityValue, graph);
    }
    
    // retourne l'objet vertex sortant de l'arc
    public L3_C5_Vertex getTerminalExtremity(L3_C5_Graph graph) 
    {
      return L3_C5_Vertex.FindVertexWithID(termExtremityValue, graph);
    }
}
