package L3_C5_thgraphes_pathfinding;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.Set;
import java.util.LinkedHashSet;


public class L3_C5_Bellman 
{
    
    private class Pair
    {
        int i;
        int val;
        
        Pair(int i, int val)
        {
            this.i = i;
            this.val = val;
        }
    }
    private class Bellcase
    {
        int poid;
        ArrayList<Integer> sommets;
        
        Bellcase() {}
        
        Bellcase(int poid)
        {
            this.poid = poid;
            this.sommets = new ArrayList<>();
        }
    }
    // k_line correspond à une ligne d'étape. On réduit ici le nom de son type.
    private class k_line extends ArrayList<Bellcase> {}

 
    /**
     * Initialise un k_line
     * @param c nombre d'élément
     * @return un nouveau k_line
     */
    private k_line new_kline(int c)
    {
        k_line nkline = new k_line();
        for(int i = 0; i < c; i++)
        {
            nkline.add(i,new Bellcase());
            nkline.get(i).poid = 0;
            nkline.get(i).sommets = new ArrayList<>();
        }
        return nkline;
    }
    
    private void print_kline(k_line l)
    {
        if(l == null)
        {
            System.out.println("NULL");
            return;
        }
        
        
        for(int i = 0; i < l.size(); i++)
        {
            System.out.print("["+l.get(i).poid+"] ");
            for(int j = 0; j < l.get(i).sommets.size(); j++)
            {
                System.out.print(l.get(i).sommets.get(j)+" ");
            }
            System.out.println(" ");
        }
        
        System.out.println("Press Enter key to continue...");
        try
        {
            System.in.read();
        }  
        catch(Exception e)
        {}  
    }
    
    private boolean cmp_kline(k_line a, k_line b)
    {
        if(a == null || b == null)
        {
            return false;
        }
        
        if(a.size() != b.size())
        {
            return false;
        }
        
        for(int i = 0; i < a.size(); i++)
        {
            if(a.get(i).poid != b.get(i).poid)
            {
                return false;
            }
        }
        return true;
    }
    
    private k_line cp_kline(k_line a)
    {
        k_line nkline = new k_line();
        for(int i = 0; i < a.size(); i++)
        {
            Bellcase tmp = new Bellcase();
            tmp.poid = a.get(i).poid;
            tmp.sommets = new ArrayList<>();
            for(int j = 0; j < a.get(i).sommets.size(); j++)
            {
                tmp.sommets.add(a.get(i).sommets.get(j));
            }
            nkline.add(tmp);
        }
        return nkline;
    }
    
    //le graphe
    L3_C5_Graph g = null;  
    //l'ID du sommer de départ
    int start = 0;
    int vcount = 0;
    //Double ArrayList (tableau de bellman)
    ArrayList<k_line> bellman_array = new ArrayList<>();
    
    /**
     * Constructeur
     * @param g, le graph
     * @param start, l'ID du sommet de départ
     */
    public L3_C5_Bellman(L3_C5_Graph g, int start)
    {
        this.g = g;
        this.start = start;
        this.vcount = g.getVertexCount();
    }

    /**
     * Execute l'algorithme de Bellman
     */
    public void process()
    {
        k_line init = new_kline(vcount);
        init.get(0).poid = 0;
        init.get(0).sommets.add(start);
        print_kline(init);
        
        k_line a = null;
        k_line b = init;
        ArrayList<Integer> next_somm = new ArrayList(Arrays.asList((0)));
        do
        {
            bellman_array.add(a);
            print_kline(b);
            a = b;
            b = cp_kline(a);
            ArrayList<L3_C5_Arc>  succ = get_succ(next_somm);
            next_somm = new ArrayList();
            
            for(int i = 0; i < succ.size(); i++)
            {
                int tterm = succ.get(i).termExtremityValue;
                int tinit = succ.get(i).initExtremityValue;
                int npds = b.get(tinit).poid + succ.get(i).value;
                
                //System.out.println(i+" "+tterm+" "+tinit);
                //System.out.println(a.get(tterm).sommets.isEmpty()+" "+a.get(tterm).poid+" "+npds );
                int diff = npds - b.get(tterm).poid;
                if(b.get(tterm).sommets.isEmpty() || diff < 0)
               {
                    Bellcase bctmp = new Bellcase(npds);
                    bctmp.sommets.add(tinit);
                    b.set(tterm,bctmp);
                    next_somm.add(tterm);

                } else if(b.get(tterm).poid == npds)
                {
                    b.get(tterm).sommets.add(tinit);
                    next_somm.add(tterm);
                }
            }
            
        }while(!(cmp_kline(a,b)));
    }
    
    
    private ArrayList<L3_C5_Arc> get_succ(ArrayList<Integer> somm)
    {
        ArrayList<L3_C5_Arc> arcs = new ArrayList<>();
        for(int i = 0; i < somm.size(); i++)
        {
            L3_C5_Vertex v = L3_C5_Vertex.FindVertexWithID(somm.get(i),g);
            arcs.addAll(v.getOutgoingArcs());
        }
        return arcs;
    }
    
    

}

