/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package theorie_graphe;

import java.util.ArrayList;
import java.util.Collections;
import java.io.PrintStream;
import java.util.logging.Logger;
/**
 * TODO : 
 * Spacing automatique
 * Menu
 * Fichiers ultra rigoureux 
 * Matrice d'adjacence : boucles
 * Rapport
 * @author franc
 */



public class L3_C5_Dijsktra 
{
    
    int[][] dijkstraMatrix; // Utilisé pour l'affichage du tableau de dijkstra
    ArrayList<L3_C5_Vertex> path; // Chemin actuel du sommet initial (précisé dans le constructeur) vers l'index maximal
    ArrayList<L3_C5_Vertex> listeSommets;  // Liste des sommets passée en copie
    int etape_ID = 0; // constitue l'index de la ligne du tableau de dijkstra
    L3_C5_Graph g; // le graphe sur lequel va être effectué l'algorithme de dijkstra
    int[] distances; // contient les distances pour chaque sommet depuis le sommet initial
    boolean debug = false; // permet d'activer l'affichage détaillé pour voir comment se déroule l'algorithme
    int maxSpaces = 0; // Tabulations pour adapter la taille du tableau
    
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
        L3_C5_Vertex.FindVertexWithID(startID, g).graphPred[0]  = startID;
        this.g = g;
        this.path = new ArrayList<>();
        listeSommets= g.allVertex; 
        
        // Initialisation   
        L3_C5_Vertex premierSommet = L3_C5_Vertex.FindVertexWithID(startID, g);
        if(premierSommet == null) System.err.println("Le premier sommet n'a pas été trouvé");
        this.path.add(premierSommet);
    }
    /**
     * Vérifie si toutes les arcs de ce graphe possèdent des distances égales
     * @return Vrai si tous les poids des arcs sont égaux
     */
    public boolean allDistEquals()
    {
        int firstDist = this.g.allArcs.get(0).value;
        for (L3_C5_Arc a : this.g.allArcs)
        {
            if(a.value != firstDist)
                return false;
        }
        System.out.println("Les distances sont égales ! ");
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
          int dernierSommetCheminID = path.get(path.size()-1).ID;
          for(L3_C5_Vertex sommetSortant : sommetsSortants)
           {
               if(!path.contains(sommetSortant))
               {
                   distances[sommetSortant.ID] = distances[dernierSommetCheminID]+1;
                   sommetSortant.graphPred[etape_ID] = dernierSommetCheminID ;
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
        updateDijkstraMatrix(etape_ID);
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
       if(debug)
       {
        System.out.println("taille de la liste " + path.size());
        System.out.println(path);
       }
        
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
                  System.err.println(e);
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
        if(sommetsSortants == null || sommetsSortants.isEmpty())
        {
            updateDijkstraMatrix(etape_ID);
        }
        else
        {
                for (L3_C5_Vertex sommet : sommetsSortants)
               {
              // On ne touche pas aux sommets qui ont déjà été ajoutés au path pour leurs distances (verrouillage)
              if(!path.contains(L3_C5_Vertex.FindVertexWithID(sommet.ID, g)) )
              {
                  // comment recalcule t'on une distance ? on prend la distance du premier sommet jusqu'à celui ci
                  int newDist = distances[dernierSommetChemin.ID] + sommet.getArcComingFrom(dernierSommetChemin.ID).value;
                        if( newDist < distances[sommet.ID] )
                        {
                            distances[sommet.ID] = newDist;
                            L3_C5_Vertex.FindVertexWithID(sommet.ID, g).graphPred[etape_ID] = dernierSommetChemin.ID;
                        }
                  // Une fois qu'on a recalculé et assigné les distances, on peut passer à l'étape suivante
                  // Il faut ajouter le nouveau sommet au path en comparant toutes nos distances
              }            
          }
        }
      
        // On ajoute le nouveau sommet avec la distance minimale

             int sommetMinIndex = getIDMinDist(); 
             if(sommetMinIndex != Integer.MAX_VALUE)
        addToPath(L3_C5_Vertex.FindVertexWithID(sommetMinIndex, g));
             else 
        addToPath(L3_C5_Vertex.FindVertexWithID(minNotAddedVertexID(), g));  
             updateDijkstraMatrix(etape_ID);

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
    
    private void doSpacing(int count)
    {
        if(count>0)
            System.out.printf("%"+count+"s"," ");
    }
    
    
    /**
     * Affiche le tableau avec une colonne CC ainsi que la liste des identifiants de sommets en argument, pour l'instant le sommet initial est d'index 0
     * et le sommet final est d'index proposé
     * @param showPaths Si à Vrai, affiche les chemins possibles en partant du point de départ
     */
    public void print(boolean showPaths)
    {
        System.out.println("Spacing : " + maxSpaces);
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
        maxSpaces = listeSommets.size();
        for (int i = 0; i < listeSommets.size(); i++) {
            if(i==0)
            {
                String titreCC = "ORDRE";
                                System.out.print(titreCC);
                                doSpacing(maxSpaces - titreCC.length());
            }

            System.out.printf("%9s", "|"+ i);
        }
        //maxSpaces =0;
        
        // Ligne de séparation
        System.out.println();
        for (int i = 0; i < listeSommets.size(); i++) {
            System.out.print("---------");
        }
        
        // Affichage du tableau
        System.out.println();
        for (int i = 0; i < listeSommets.size(); i++) { // Lignes qui rpz les etapes
            System.out.print(getCC(i));
            int charCount = 0;
            for(char c : getCC(i).toCharArray())
            {
                    charCount++;
            }

            doSpacing(listeSommets.size()-charCount);

            
                for (int j = 0; j <  listeSommets.size(); j++) { // Colonnes qui representent les sommets
                    if(dijkstraMatrix[i][j] !=Integer.MAX_VALUE && distances[j] >= 0)
                        System.out.printf("%7s", dijkstraMatrix[i][j] +"("+ L3_C5_Vertex.FindVertexWithID(j, g).graphPred[i] + ")" );
                    else 
                        System.out.printf("%7s",/*"∞"*/ "+"); // on peut afficher le symbole infini mais par sécurité, on affiche le symbole + à la place
                    doSpacing(2); // Ecart entre les cases
            }
            System.out.println();
        }
        
        String vertexText= "Sommets\t\t";
        String distText = "Distances\t";
        if(showPaths)
        {
            for(int i = 0; i < distances.length; i++)// LIGNE
            {
                for(int j = 0; j < distances.length ;j++) //COLONNE
                {
                    if(i == etape_ID)
                    {
                        vertexText+=" " + j;
                        if(distances[j] == Integer.MAX_VALUE || distances[j] < 0)
                            distText+=" +";
                        else
                            distText+=" "+ distances[j];
                    }
                }
            }
            System.out.println(vertexText);
            System.out.println(distText);
        }
        
     /*   for(int i =0 ; i < distances.length;i++)
        {
            printPath(get_path(i),i);
        }*/
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

    public ArrayList<Integer> get_path(int destID)
    {
        if(distances[destID] == Integer.MAX_VALUE) return null;
        ArrayList<Integer> pathOrder = new ArrayList<>(); // Le chemin dijkstra
        int etapeStock = etape_ID;
        int pred = -1; // predecesseur
        int predAnterieur = -1; // predecesseur du predecesseur
        do { 
            if(pred!=-1)
            {
               predAnterieur =  L3_C5_Vertex.FindVertexWithID(pred, g).graphPred[etapeStock]; 
            if(pred == destID || pred ==predAnterieur ) break;
            }
            if(etapeStock  == etape_ID ) 
            {
                pred = L3_C5_Vertex.FindVertexWithID(destID, g).graphPred[etapeStock];               
            }
            else pred = predAnterieur ;
            pathOrder.add(pred);
       etapeStock--; // on remonte donc on decremente les predecesseurs
        } while (etapeStock>0);
        Collections.reverse(pathOrder);
        pathOrder.add(destID);
        return pathOrder;
    }
    
    public void printPath(ArrayList<Integer> foundPath, int dest) 
    {
       if(foundPath == null || this.path.get(0).ID != foundPath.get(0))
       {
                       System.out.println("[X] Impossible de calculer un chemin de  " +  this.path.get(0).ID + " jusqu'à " + dest+ " (Distance infinie)");
                       return;
       }
       System.out.print("En partant du sommet (" + path.get(0).ID  + ") on peut arriver à (" +dest+ ") grâce au chemin : " );
        for(int sommetID : foundPath)
        {
            if(foundPath.size() != 1 && sommetID != foundPath.get(foundPath.size()-1))
            System.out.print(sommetID + " => ");
            else 
                System.out.print(sommetID + " ");
        }
        System.out.println();

    }

    int minNotAddedID = -1;
    private int minNotAddedVertexID() 
    {
        for(L3_C5_Vertex v : g.allVertex)
        {
            if(!path.contains(v))
            {
                if(minNotAddedID < v.ID)
                    minNotAddedID = v.ID;
            }
        }
        return minNotAddedID;
    }

}
