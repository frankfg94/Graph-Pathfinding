/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package theorie_graphe;

import theorie_graphe.L3_C5_Arc;
import theorie_graphe.L3_C5_Graph;
import theorie_graphe.L3_C5_Vertex;
import java.util.ArrayList;

/**
 * TODO : 
 * Spacing automatique
 * Menu
 * Matrice des valeurs
 * Matrice d'adjacence : boucles
 * Rapport
 * 
 * @author franc
 */



public class L3_C5_Dijsktra 
{
    
    int[][] dijkstraMatrix; // Utilisé pour l'affichage du tableau de dijkstra
    ArrayList<L3_C5_Vertex> path; // Chemin actuel du sommet initial (précisé dans le constructeur) vers l'index maximal
    ArrayList<L3_C5_Vertex> listeSommets;  // Liste des sommets passée en copie
    int etape_ID = 0; // constitue l'index de la ligne du tableau de dijkstra
    L3_C5_Graph g;
    int[] distances;
    boolean debug = false;
    boolean stopAlg = false;
    
    /**
     * Le constructeur permet à la fois de lancer de générer et afficher l'algorithme de dijsktra
     * @param g le graphe sur lequel effectuer l'algorithme
     * @param startID sommet de départ
     */
    public L3_C5_Dijsktra(L3_C5_Graph g, int startID)
    {
        int vertexCount = g.allVertex.size();
        dijkstraMatrix = new int[vertexCount][vertexCount];
        distances = new int[vertexCount];
        for (int i = 0; i < distances.length; i++) {
            distances[i] = Integer.MAX_VALUE; // On met toutes les distances à +infini
            L3_C5_Vertex.FindVertexWithID(i, g).graphPred = new int[distances.length];
        }
        distances[startID] = 0;
        this.g = g;
        this.path = new ArrayList<>();
        listeSommets= g.allVertex; 
        
        // Initialisation   
        L3_C5_Vertex premierSommet = L3_C5_Vertex.FindVertexWithID(startID, g);
        if(premierSommet == null) System.err.println("Le premier sommet n'a pas été trouvé");
        else System.out.println("premier sommet :" +premierSommet );
        this.path.add(premierSommet);
    }
    /**
     * Vérifie si toutes les arcs de ce graphe possèdent des distances égales
     * @return Vrai si tous les poids des arcs sont égaux
     */
    public boolean allDistEquals()
    {
        for (L3_C5_Arc a : this.g.allArcs)
        {
            if(a.value != 1)
                return false;
        }
        return true;
    }
    
    /**
     * Remplit le tableau de Dijkstra avec l'algorithme optimisé pour les longueurs égales
     */
    public void equalDistAlg()
    {          
        while(etape_ID != g.allVertex.size())
        {
            System.out.println("Etape : " + etape_ID);
          ArrayList<L3_C5_Vertex> sommetsSortants =  path.get(etape_ID).getAllNeighbours(g);
          int dernierSommetID = path.get(path.size()-1).ID;
          for(L3_C5_Vertex sommetSortant : sommetsSortants)
           {
               if(!path.contains(sommetSortant))
               {
                   distances[sommetSortant.ID] = distances[dernierSommetID]+1;
                   addToPath(sommetSortant);
               }
           }
          
            updateDijkstraMatrix(etape_ID);
            etape_ID++;
        }
        
    }
    
    /**
     * Traite l'algorithme de dijkstra
     */
    void process(boolean  debug)
    {
        if(allDistEquals())
        {
            equalDistAlg();
        }
        else
        {
        this.debug = debug;
        //  System.out.println("Début de la recherche de chemins Dijkstra");
        while(path.size() != listeSommets.size())
        {
            addVertexMinDist();
            etape_ID++;
        }
        updateDijkstraMatrix(etape_ID-1);
       // System.out.println("Terminé !");
       // printPath();
        }      
    }
    
    void addToPath(L3_C5_Vertex sommet)
    {
       path.add(sommet);
    }

    /**
     * Permet d'ajouter au path le sommet avec la distance la plus petite
     */
    private void addVertexMinDist() 
    {
      // On regarde le dernier sommet du path tous les sommets et on regarde celui avec la plus petite distance DEPUIS LE DEBUT 
        System.out.println("taille de la liste " + path.size());
        System.out.println(path);
        // On prend le dernier élement de notre path
      L3_C5_Vertex dernierSommet  =   path.get(path.size()-1);
   //     System.out.println("Dernier sommet : " + dernierSommet.ID);
   

   
      ArrayList<L3_C5_Vertex> sommetsSortants =  L3_C5_Vertex.FindVertexWithID(dernierSommet.ID,g).getAllNeighbours(g);
      updateDists(sommetsSortants,dernierSommet);
    }

    /**
     * Montre les distances de chaque sommet par rapport au sommet initial
     */
    private void printDists()
    {
        for (int i = 0; i < distances.length; i++) {
            System.out.println(String.format("Distance du sommet {%d} --> {%d} ",i,distances[i]));
        }
    }
    
    /**
     * Montre le path actuel pour l'algorithme de dijkstra en itérant la liste path
     */
    private void printPath()
    {
        System.out.println("Affichage du chemin pour l'itération : " + path.size() + "/" + listeSommets.size() );
        for (int i = 0; i < path.size(); i++) {
            if(i < path.size()-1)
            System.out.print(path.get(i).ID + "  => ");
            else 
            System.out.print(path.get(i).ID + "\n");
           }
    }

    /**
     * Renvoie l'ensemble CC pour le graphe passé en argument dans le constructeur
     * @param etape Indique pour quelle étape nous devons afficher CC
     * @return le code CC pour le tableau de dijksta
     */    
    private String getCC(int etape)
    {
        String code = "";
        for (int i = 0; i < etape+1; i++) {
            try {
                            code += Integer.toString(path.get(i).ID);

            } catch (Exception e) {
                code = "Erreur";
            }
        }
        return code;
    }
    
    /**
     * Assigne les distances des sommets sortants par rapport au dernier sommet du path passé en argument
     * @param sommetsSortants
     * @param dernierSommetChemin 
     */
    private void updateDists(ArrayList<L3_C5_Vertex> sommetsSortants, L3_C5_Vertex dernierSommetChemin) 
    {
        for (L3_C5_Vertex sommet : sommetsSortants)
        {
            // On ne touche pas aux sommets qui ont déjà été ajoutés au path pour leurs distances (verrouillage)
            if(!path.contains(L3_C5_Vertex.FindVertexWithID(sommet.ID, g)) )
            {
                // comment recalcule t'on une distance ? on prend la distance du premier sommet jusqu'à celui ci
                int newDist = distances[dernierSommetChemin.ID] + sommet.getArcComingFrom(dernierSommetChemin.ID).value;
                if( newDist < distances[sommet.ID] )
                distances[sommet.ID] = newDist;
                L3_C5_Vertex.FindVertexWithID(sommet.ID, g).graphPred[etape_ID] = dernierSommetChemin.ID;
                // Une fois qu'on a recalculé et assigné les distances, on peut passer à l'étape suivante
                // Il faut ajouter le nouveau sommet au path en comparant toutes nos distances
                   updateDijkstraMatrix(etape_ID);
            }            
        }
        // On ajoute le nouveau sommet avec la distance minimale

             int sommetMinIndex = getIDMinDist(); 
             if(sommetMinIndex != Integer.MAX_VALUE)
        addToPath(L3_C5_Vertex.FindVertexWithID(sommetMinIndex, g));
             else 
        addToPath(L3_C5_Vertex.FindVertexWithID(etape_ID+1, g));
        if(debug) 
        {
            System.out.println("Sommet Ajouté : "+ path.get(path.size()-1));
            printDists(); 
            System.out.println("Taille actuelle " + path.size() + "/" + listeSommets.size() );
        }
    }

    /**
     * Retourne l'identifiant du sommet avec la distance minimale, et qui est en dehors du path
     * @return 
     */
    private int getIDMinDist() 
    {
        int minDist = Integer.MAX_VALUE; // +infini
        int ID_Sommet = Integer.MAX_VALUE; // +infini
        for (int i = 0; i < distances.length; i++) {
            if(!path.contains(L3_C5_Vertex.FindVertexWithID(i, g))) // On vérifie que le sommet n'est pas verrouillé
            {
                 if( distances[i] < minDist)
                 {
                  minDist = distances[i];
                  ID_Sommet = i;
                 }
            }
        }
        return ID_Sommet;
    }
    
    /**
     * Affiche le tableau avec une colonne CC ainsi que la liste des identifiants de sommets en argument, pour l'instant le sommet initial est d'index 0
     * et le sommet final est d'index proposé
     */
    public void print()
    {
        // Correction des predecesseurs dans le graphe
        for(int i =0 ; i < distances.length ;i++)
        {
            L3_C5_Vertex v = L3_C5_Vertex.FindVertexWithID(i, g);
            for (int j = 1; j < distances.length; j++) {
                if(v.graphPred[j] == 0 && v.graphPred[j-1]!=0)
                    v.graphPred[j] = v.graphPred[j-1];
            }
        }
        
        // Affichage de la premiere ligne du tableau
        for (int i = 0; i < listeSommets.size(); i++) {
            if(i==0)
                System.out.print("ORDRE");
            System.out.print("\t|"+ i);
        }
        
        // Ligne de séparation
        System.out.println();
        for (int i = 0; i < listeSommets.size(); i++) {
            System.out.print("---------");
        }
        
        // Affichage du tableau
        System.out.println();
        for (int i = 0; i < listeSommets.size(); i++) { // Lignes qui rpz les etapes
            System.out.print(getCC(i) + "\t|");
            for (int j = 0; j <  listeSommets.size(); j++) { // Colonnes qui representent les sommets
                if(dijkstraMatrix[i][j] !=Integer.MAX_VALUE)
                    System.out.print( dijkstraMatrix[i][j] +"("+ L3_C5_Vertex.FindVertexWithID(j, g).graphPred[i] + ")" );
                else 
                    System.out.print("∞");
                System.out.print("\t");
            }
            System.out.println();
        }
    }
    
    /**
     * Met à jour le tableau 2D dijkstraMatrix  pour une étape donnée avec les bonnes distances pour chacun de ses sommets
     * @param etape ligne du tableau du dijkstra
     */
    private void updateDijkstraMatrix(int etape) {
            for (int sommetID = 0; sommetID < listeSommets.size(); sommetID++) {
                            dijkstraMatrix[etape][sommetID]= distances[sommetID];
            } 
    }

}
