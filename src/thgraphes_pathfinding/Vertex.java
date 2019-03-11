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
public class Vertex // Sommet
{
    // désigne les successeurs et prédécesseurs
    public ArrayList<Arc> arcs = new ArrayList<>();    
    int value;     // valeur du sommet
    public ArrayList<Integer> predValues = new ArrayList<>();
    public ArrayList<Integer> succValues = new ArrayList<>();

    
    @Override
    public String toString()
    {
        String s = "Sommet : (" + value + ")" + System.lineSeparator();
        for(Arc a: arcs)
        {
            s+= a.toString() + System.lineSeparator();
        }
        return s;
    }
    
    // Nécéssaire pour créer nos sommets à l'aide d'arcs
    public Vertex(int value) 
    {
       this.value = value;
    }
    
    public Vertex(int value, ArrayList<Arc> arcs)
    {
        this.arcs = arcs;
        this.value = value;
    }
    
    public Arc getArcGoingTo(int value)
    {
        for(Arc a : arcs)
            if(a.termExtremityValue == value)
                return a;
        return null;
    }
    
    // Arcs sortants du sommet
    public ArrayList<Arc> getOutgoingArcs()
    {
        ArrayList<Arc> arcList = new ArrayList();
        for(Arc a : arcs)
        {
            if(a.initExtremityValue == value)
                arcList.add(a);
        }
        return arcList;
    }
    
    // Arcs entrants du sommet
        public ArrayList<Arc> getEnteringArcs()
    {
        ArrayList<Arc> arcsList = new ArrayList();
        for(Arc a : arcs)
        {
            if(a.termExtremityValue == value)
                arcs.add(a);
        }
        return arcs;
    }
    
 
    
}
