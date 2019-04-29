package L3_C5_thgraphes_pathfinding;


import java.util.Arrays;
import java.util.ArrayList;



public class L3_C5_Bellman 
{
    private class k_line extends ArrayList<ArrayList<Integer>> {}

    private k_line new_kline(int c)
    {
        k_line nkline = new k_line();
        for(int i = 0; i < c; i++)
        {
            ArrayList<Integer> tmp = new ArrayList<>();
            tmp.add(0);
            tmp.add(-1);
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
    ArrayList<k_line> bellman_array;
    
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
        k_line a = null;
        k_line b = new_kline(vcount);
        
        
        b.set(0, new ArrayList<>(Arrays.asList(0,start)));
        

        do
        {
            bellman_array.add(b);
            a = b;
            b = new_kline(vcount);
            // je parcours a et b de 0 à vcount -1;
            for(int i = 0; i < vcount; i++)
            {
                b.set(i,get_new_iter(a,i));
            }
        }while(!k_equal(a,b));
    }
    
    private ArrayList<Integer> get_new_iter(k_line km1, int v)
    {
        k_line stock = new k_line();
        for(int i = 0; i < vcount; i++)
        {
            if(!km1.get(1).equals(-1))
            for(int j = 0; j < km1.get(i).size(); j++)
            {
                ArrayList<Integer> res = null; //FIXME
                if(res != null)
                {
                    stock.add(res);
                }
            }
        }
        
        if(stock.isEmpty())
        {
            return new ArrayList<>(Arrays.asList(0,-1));
        }
        int min = stock.get(0).get(0);
        ArrayList<Integer> min_ids = new ArrayList<>();
        for(int i = 0; i < stock.size(); i++)
        {
            int t = stock.get(i).get(0);
            if(min > t)
            {
                min = t;
            }
        }
        ArrayList<Integer> return_value = new ArrayList<>();
        return_value.add(min);
        for(int i = 0; i < stock.size(); i++)
        {
            if(stock.get(i).get(0) == min)
            {
                for(int j = 1; j < stock.get(i).size(); j++)
                {
                    return_value.add(stock.get(i).get(j));
                }
            }
        }
        
        return sort_arr_list(return_value);
        
    }
    
    
    
    
    /**
     * Afficher les résulstats de l'algorithme
     */
    public void print()
    {
        
    }
    
    /**
     * Compare deux k_equal (permet de vérifier si on a fini ou non les ittérations)
     * @param a element 1
     * @param b element 2
     * @return faux si différent
     */
    private boolean k_equal(k_line a, k_line b)
    {
        if(a == null || b == null)
            return false;
        // les deux k_lines ont FORCEMENT la meme taille
        assert a.size() == b.size();
        for(int i = 0; i < a.size(); i++)
        {
            if(a.get(i).size() != b.get(i).size())
            {
                return false;
            }
            for(int j = 0; j < a.get(i).size();j++)
            {
                if(!(a.get(i).get(j).equals(b.get(i).get(j))))
                {
                    return false;
                }
            }
        }
        return true;
    }
   
    
    private ArrayList<Integer>  sort_arr_list(ArrayList<Integer> a)
    {
        ArrayList<Integer> new_arr = new ArrayList<>();
        new_arr.add(a.get(0));
        for(int i = 1; i < a.size(); i++)
        {
           int j =1;
           int val = a.get(i);
           while(j < new_arr.size() && val < new_arr.get(j))
           {
               j++;
           }
           if(!a.get(i).equals(new_arr.get(j)))
           {
               new_arr.add(i,val);
           }
        }
        return new_arr;
    }
    
}