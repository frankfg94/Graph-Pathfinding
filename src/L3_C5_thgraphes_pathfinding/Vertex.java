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
public class Vertex // Sommet
{
    // désigne les successeurs et prédécesseurs
    public ArrayList<L3_C5_Arc> arcs = new ArrayList<>();    
    int ID;     // valeur du sommet
    public ArrayList<Integer> predValues = new ArrayList<>();
    public ArrayList<Integer> succValues = new ArrayList<>();

    
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
    public Vertex(int value) 
    {
       this.ID = value;
    }
    
    public Vertex(int value, ArrayList<L3_C5_Arc> arcs)
    {
        this.arcs = arcs;
        this.ID = value;
    }
    
    public L3_C5_Arc getArcGoingTo(int value)
    {
        for(L3_C5_Arc a : arcs)
            if(a.termExtremityValue == value)
                return a;
        return null;
    }
    
    // Arcs sortants du sommet
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
    public static ArrayList<Vertex> getVerticesFromValues(ArrayList<Integer> values, L3_C5_Graph graph)
    {
        ArrayList<Vertex> vertices= new ArrayList<>();
        for( Integer i : values )
        {
            vertices.add(Vertex.FindVertexWithValue(i, graph));
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
           
        public static Vertex FindVertexWithValue(int vertexValue, L3_C5_Graph graph)
    {
        for(Vertex v : graph.allVertex )
        {
            if(v.ID == vertexValue)
                return v;
        }
        return null;
    }
    

    
}
