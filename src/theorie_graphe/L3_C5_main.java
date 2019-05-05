/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package theorie_graphe;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.regex.*;
import java.io.PrintStream;
import java.io.PrintWriter;


/**
 *
 * @author franc
 */
public class L3_C5_main {

    
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) 
    {
        
        //testAllGraphs();
        while(menu());
       


/*

      L3_C5_Graph g = new L3_C5_Graph("L3_C5_slide33.txt");
      L3_C5_Dijsktra dij = new L3_C5_Dijsktra(g, 0);
       /* dij.process(false);
        dij.print(false);

    ArrayList<Integer> path = find_path_bellman(g,0,3);
    print_path(path);
    System.out.println(print_path_graphviz(g, path));*/
    }
    
    
    private static void testAllGraphs()
    {
 testGraphs(new String[]{"L3_C5_1.txt","L3_C5_2.txt","L3_C5_3.txt" ,"L3_C5_4.txt","L3_C5_5.txt","L3_C5_6.txt","L3_C5_7.txt","L3_C5_8.txt",
        "L3_C5_9.txt","L3_C5_10.txt","L3_C5_sampleGraph.txt","L3_C5_sampleGraph_1.txt","L3_C5_slide33.txt","L3_C5_slide43.txt"});
    }
    
    /**
     * Affiche le tableau de recherche de chemin pour un ensemble de graphes avec pour sommet de départ le sommet d'identifiant 0
     * Le choix de Bellman ou Dijkstra est fait automatiquement
     * @param graphPaths  L'ensemble des chemins des graphes à analyser
     */
    private static void testGraphs(String[] graphPaths)
    {
        L3_C5_Graph[] graphs = new L3_C5_Graph[graphPaths.length];
        for (int i =0; i< graphPaths.length ;i++) {
            System.out.print("Initialisation graphe n° "  + (i+1) + " : " +  graphPaths[i]  +"\t\t...");
            try {
                if(!graphPaths[i].endsWith(".txt"))
                    graphPaths[i] = graphPaths[i]+=".txt";
                graphs[i] = new L3_C5_Graph(graphPaths[i]);
            } 
            catch (Exception e) 
            {
                System.err.print("Echec" + System.lineSeparator());
                System.out.println(e);
                System.exit(-1);
            }
            System.out.print("OK"+ System.lineSeparator());
        }
        
        for (L3_C5_Graph graph : graphs) 
        {
            for(int sommetID = 0; sommetID < graph.getVertexCount() ; sommetID++)
            graph.testPathfinding(sommetID);
        }
   
    }

 

    private static void print_path(ArrayList<Integer> lst)
    {
        if(lst == null)
        {
            System.out.println("Aucun chemin le plus cours n'a été trouvé");
            return;
        }
        for(int i = 0; i < lst.size(); i++)
        {
            System.out.print(lst.get(i));
            if(i != lst.size() - 1)
                System.out.print(" --> ");
        }
        System.out.println("");
    }

    private static String print_path_graphviz(L3_C5_Graph g,ArrayList<Integer> lst)
    {
        if(lst == null)
            return null;
        String dg = "digraph {\n";

        for(int i = 0; i < g.allArcs.size(); i++)
        {
            L3_C5_Arc a = g.allArcs.get(i);
            dg += "    "+a.initExtremityValue + " -> " + a.termExtremityValue;
            dg += "[label=\""+a.value+"\",weight=\""+a.value+"\"";
            for(int j = 0; j < lst.size() - 1; j++)
            {
                if(a.initExtremityValue == lst.get(j) && a.termExtremityValue == lst.get(j + 1))
                {
                    dg += ",color=red,penwidth=3.0";
                }
            }
            dg += "];\n";
        }
        dg += "}\n";

        return dg;
    }

    private static boolean menu()
    {
        Scanner sc = new Scanner(System.in);
        String PATH = ".";
        File folder = new File(PATH);
        String[] files = folder.list();
        Pattern pat = Pattern.compile("L3_C5_[0-9]+\\.txt");

        ArrayList<String> files_graphs = new ArrayList<>();

        for(int i = 0; i < files.length; i++)
        {
            if(pat.matcher(files[i]).find())
                files_graphs.add(files[i]);
        }

        System.out.println("# Voici le liste des fichiers graphes trouvés: \n");
        for(int i = 0; i < files_graphs.size(); i++)
        {
            System.out.printf("   - %2d: %s\n",i+1,files_graphs.get(i));
        }
        System.out.println("");
        System.out.println("# Vous pouvez ajouter un fichier de graphe");
        System.out.println("# en respectant le format suivant dans le nom: L3_C5_#.txt");
        String str = "";
        int val = 0;
        pat = Pattern.compile("^[0-9]{1,4}$");
        do
        {
            System.out.println("# Entrez le numero du graphe que vous voulez charger:");
            System.out.println("# (nombre entier compris entre 1 et " + files_graphs.size()+")");
            System.out.println("# Entrez 0 pour quitter");
            System.out.print("# > ");

            str = sc.nextLine().trim();

            if(pat.matcher(str).find())
            {
                val = Integer.parseInt(str);
                if(val == 0)
                {
                    return false;
                }
                if(val >= 1 && val <= files_graphs.size())
                {
                    break;
                }

            }
            System.out.println("#! Saisie incorrecte");
        }while(true);

        String file = files_graphs.get(val - 1);
        System.out.println("# Chargement du fichier graphe "+file);
        run_algo(PATH,file, val);

        return true;
    }

    public static void run_algo(String PATH, String file, int i)
    {
        L3_C5_Graph g = new L3_C5_Graph(PATH+"/"+file);

        int start = 0;

        Scanner sc = new Scanner(System.in);




        // ### Affichage matrices
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(baos);
        PrintStream old = System.out;
        System.setOut(ps);

        g.printAdjMatrix();
        g.printValuesMatrix();
        g.printIncidenceMatrix();

        System.out.flush();
        System.setOut(old);

        
        String res = baos.toString();
        baos = new ByteArrayOutputStream();
        ps = new PrintStream(baos);
        System.out.println(res);

        // ### Saisie Sommet de depart
        Pattern pat = Pattern.compile("^([0-9]{1,4})$");
        do
        {
            System.out.println("# Indiquer un sommet de départ:");
            System.out.println("# (entier entre 0 et "+(g.getVertexCount()-1)+")");    
            System.out.print("# > ");

            String str = sc.nextLine().trim();
            if(pat.matcher(str).find())
            {
                if(str.charAt(0) == 'n')
                {
                    return;
                }
                start = Integer.parseInt(str);

                if(start >= 0 && start <= g.getVertexCount()-1)
                {
                    break;
                }
            }
            System.out.println("Saisie incorrecte");
        }while(true);

        PrintWriter out;
        try {
            out = new PrintWriter(PATH+"/"+"L3_C5_trace"+i+"_"+start+".txt");
        } catch (Exception e) {
            System.out.println(("Erreur ecriture fichier"));
            return;
        }

        out.println(res);
        old = System.out;
        System.setOut(ps);
        g.testPathfinding(start);     

        System.setOut(old);
        res = baos.toString();
        baos = new ByteArrayOutputStream();
        ps = new PrintStream(baos);

        System.out.println(res);
        out.println(res);


        // ### Saisie affichage chemin
        pat = Pattern.compile("^((n)|([0-9]{1,4}))$");
            int val = 0;
        do
        {
            System.out.println("# Afficher un chemin partant de "+start+" allant vers un point défini ?");
            System.out.println("# n pour non, Sinon indiquer le sommet (entier entre 0 et "+(g.getVertexCount()-1)+")");    
            System.out.print("# > ");

            String str = sc.nextLine().trim();
            if(pat.matcher(str).find())
            {
                if(str.charAt(0) == 'n')
                {
                    out.close();
                    return;
                }
                val = Integer.parseInt(str);

                if(val >= 0 && val <= g.getVertexCount()-1)
                {
                    break;
                }
            }
            System.out.println("Saisie incorrecte");
        }while(true);

        // ### Affichage chemin
        old = System.out;
        System.setOut(ps);

        System.out.println("Chemin trouvé: ");
        ArrayList<Integer> path = g.get_path(val);
        print_path(path);

        System.setOut(old);
        res = baos.toString();
        baos = new ByteArrayOutputStream();
        ps = new PrintStream(baos);

        System.out.println(res);
        out.println(res);
        out.close();
        if(path != null)
        {
            // ### Saisie Graphviz
            do{
                System.out.println("# Afficher une version graphviz du graphe avec le chemin trouvé ?");
                System.out.println("# Utilisable avec le logiciel graphviz ou directement en ligne sur http://www.webgraphviz.com/");
                System.out.print("# [y/n]\n# > ");
                String str = sc.nextLine().trim();
                
                if(str.charAt(0) == 'n')
                {
                    return;
                }
                else if(str.charAt(0) == 'y')
                {
                    break;
                }
            }while(true);

            // ### Affichage graphviz

            String gpz = print_path_graphviz(g, path);
            System.out.println("======================");
            System.out.print(gpz);
            System.out.println("======================");
            String dot_name = "L3_C5_"+i+"_"+start+"_"+val+".dot";
            System.out.println("Fichier .dot sauvegardé: "+dot_name);
            try {
                out = new PrintWriter(PATH+"/"+dot_name);
            } catch (Exception e) {
                System.out.println(("Erreur ecriture fichier"));
                return;
            }
            out.println(gpz);
            out.close();
        }


        System.out.println("# Appuyez sur ENTER pour continuer...");
        sc.nextLine();

    }

   
}
