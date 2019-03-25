/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thgraphes_pathfinding;



/**
 *
 * @author franc
 */
public class L3_C5_Arc 
{
    public L3_C5_Vertex initialExtremity; // pas utilisé pour l'instant à par dans le constructeur L3_C5_Arc(int value, L3_C5_Vertex initExtremity, L3_C5_Vertex termExtremity)
    public L3_C5_Vertex terminalExtremity; // pas utilisé pour l'instant à par dans le constructeur L3_C5_Arc(int value, L3_C5_Vertex initExtremity, L3_C5_Vertex termExtremity)
    public int initExtremityValue = -1;
    public int termExtremityValue = -1;
    public int value = -1;
    
    public L3_C5_Arc(int value, L3_C5_Vertex initExtremity, L3_C5_Vertex termExtremity)
    {
        this.value=value;
        this.initialExtremity = initExtremity;
        this.terminalExtremity = termExtremity;
        this.initExtremityValue = initExtremity.value;
        this.termExtremityValue = termExtremity.value;
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
}
