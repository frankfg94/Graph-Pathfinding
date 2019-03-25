/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thgraphes_pathfinding;

import java.util.ArrayList;

/**
 *
 * @author franc
 */
public class L3_C5_Vertex // Sommet
{
    // désigne les successeurs et prédécesseurs
    public ArrayList<L3_C5_Arc> arcs = new ArrayList<>();    
    int value;     // valeur du sommet
    public ArrayList<Integer> predValues = new ArrayList<>();
    public ArrayList<Integer> succValues = new ArrayList<>();

    
    @Override
    public String toString()
    {
        String s = "Sommet : (" + value + ")" + System.lineSeparator();
        for(L3_C5_Arc a: arcs)
        {
            s+= a.toString() + System.lineSeparator();
        }
        return s;
    }
    
    // Nécéssaire pour créer nos sommets à l'aide d'arcs
    public L3_C5_Vertex(int value) 
    {
       this.value = value;
    }
    
    public L3_C5_Vertex(int value, ArrayList<L3_C5_Arc> arcs)
    {
        this.arcs = arcs;
        this.value = value;
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
            if(a.initExtremityValue == value)
                arcList.add(a);
        }
        return arcList;
    }
    
    // Arcs entrants du sommet
        public ArrayList<L3_C5_Arc> getEnteringArcs()
    {
        ArrayList<L3_C5_Arc> arcsList = new ArrayList();
        for(L3_C5_Arc a : arcs)
        {
            if(a.termExtremityValue == value)
                arcs.add(a);
        }
        return arcs;
    }
    
 
    
}
