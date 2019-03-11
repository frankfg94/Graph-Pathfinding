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
public class Arc 
{
    public Vertex initialExtremity;
    public Vertex terminalExtremity;
    public int initExtremityValue = -1;
    public int termExtremityValue = -1;
    public int value = -1;
    
    public Arc(int value, Vertex initExtremity, Vertex termExtremity)
    {
    }
    
    public Arc(int value, int initExtremityValue, int termExtremityValue)
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
