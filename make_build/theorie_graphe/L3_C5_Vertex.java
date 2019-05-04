/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package theorie_graphe;

import java.util.ArrayList;
import java.util.HashSet;

/**
 *
 * @author franc
 */


 public class L3_C5_Vertex // Sommet
{
    // désigne les successeurs et prédécesseurs
    public ArrayList<L3_C5_Arc> arcs = new ArrayList<>();
    // valeur du sommet
    int ID;     
    // Liste ID sommet entrant
    public ArrayList<Integer> predValues = new ArrayList<>();
    // Liste ID sommet sortant
    public ArrayList<Integer> succValues = new ArrayList<>();
    // utilisé dans dijsktra: jistorique sommet précédent
    public int[] graphPred = null;
    
    
    @Override
    public String toString()
    {
        String s = "Sommet : (" + ID + ")" + System.lineSeparator();
        for(L3_C5_Arc a: arcs)
        {
            s+= a.toString() + System.lineSeparator();
        }
        return s;
    }
    
    // Nécéssaire pour créer nos sommets à l'aide d'arcs
    public L3_C5_Vertex(int value) 
    {
       this.ID = value;
    }
    
    public L3_C5_Vertex(int value, ArrayList<L3_C5_Arc> arcs)
    {
        this.arcs = arcs;
        this.ID = value;
    }
    
    // prend en entrée un graphe
    // retourne les sommets voisin sortant du sommet instancié. 
    public ArrayList<L3_C5_Vertex> getAllNeighbours(L3_C5_Graph graph)
    {
        HashSet neighbours = new HashSet();
        for(L3_C5_Arc arc : arcs)
        {
           neighbours.add(L3_C5_Vertex.FindVertexWithID(arc.termExtremityValue, graph));
        }
        neighbours.remove(this);
        
        return new ArrayList<>(neighbours);
    }

    // retourne l'arc allant du sommet instancié au sommet "value"
    public L3_C5_Arc getArcGoingTo(int value)
    {
        for(L3_C5_Arc a : arcs)
            if(a.termExtremityValue == value)
                return a;
        return null;
    }
    
    // retourne l'arc allant du sommet "value" au sommet instancié
    public L3_C5_Arc getArcComingFrom(int value)
    {
        for(L3_C5_Arc a : arcs)
            if(a.initExtremityValue == value)
                return a;
        return null;
    }
    

    
    // Arcs sortants du sommet instancié
    public ArrayList<L3_C5_Arc> getOutgoingArcs()
    {
        ArrayList<L3_C5_Arc> arcList = new ArrayList();
        for(L3_C5_Arc a : arcs)
        {
            if(a.initExtremityValue == ID)
                arcList.add(a);
        }
        return arcList;
    }
    
    
    // Permet d'obtenir directement une liste de sommets à partir de la liste des numéros de ces sommets
    public static ArrayList<L3_C5_Vertex> getVerticesFromValues(ArrayList<Integer> values, L3_C5_Graph graph)
    {
        ArrayList<L3_C5_Vertex> vertices= new ArrayList<>();
        for( Integer i : values )
        {
            vertices.add(L3_C5_Vertex.FindVertexWithID(i, graph));
        }
        return vertices;
    }
    
    // Arcs entrants du sommet
        public ArrayList<L3_C5_Arc> getEnteringArcs()
    {
        ArrayList<L3_C5_Arc> arcsList = new ArrayList();
        for(L3_C5_Arc a : arcs.toArray(new L3_C5_Arc[arcs.size()]))
        {
            if(a.termExtremityValue == ID)
                arcs.add(a);
        }
        return arcs;
    }
        // retourne l'objet vertex ayant comme index "vertexID"
        public static L3_C5_Vertex FindVertexWithID(int vertexID, L3_C5_Graph graph)
    {
        for(L3_C5_Vertex v : graph.allVertex )
        {
            if(v.ID == vertexID)
                return v;
        }
        System.out.println("Nothing found");
        return null;
    }
    

    
}
