/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package L3_C5_thgraphes_pathfinding;

import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author franc
 */



public class dijsktra_new 
{
    
    int[][] dijkstraMatrix;
    ArrayList<Vertex> chemin;
    ArrayList<Vertex> listeSommets;
    int etape_ID = 0;
    L3_C5_Graph g;
    int[] distances;
    public dijsktra_new(L3_C5_Graph g)
    {
        int vertexCount = g.allVertex.size();
        dijkstraMatrix = new int[vertexCount][vertexCount];
        distances = new int[vertexCount];
        for (int i = 0; i < distances.length; i++) {
            distances[i] = Integer.MAX_VALUE; // On met toutes les distances à +infini
            Vertex.FindVertexWithID(i, g).graphPred = new int[distances.length];
        }
        distances[0] = 0;
        this.g = g;
        this.chemin = new ArrayList<>();
        listeSommets= g.allVertex; 
        
        
        // Initialisation   
        Vertex premierSommet = Vertex.FindVertexWithID(0, g);
        this.chemin.add(premierSommet);
        
        // Recherche
        System.out.println("Début de la recherche de chemins Dijkstra");
        while(chemin.size() != listeSommets.size())
        {
            AjouterAuCheminLeSommetAvecLaDistanceDepuisLeDebutLaPlusPetite();
            etape_ID++;
        }
        ActualiserMatriceDeDijkstra(etape_ID-1);
        System.out.println("Terminé !");
        VoirLeChemin();
        AfficherTableauDijsktra();
    }
    
    void AjouterAuChemin(Vertex sommet)
    {
       chemin.add(sommet);
    }

    private void AjouterAuCheminLeSommetAvecLaDistanceDepuisLeDebutLaPlusPetite() 
    {
      // On regarde le dernier sommet du chemin tous les sommets et on regarde celui avec la plus petite distance DEPUIS LE DEBUT 
       
        // On prend le dernier élement de notre chemin
      Vertex dernierSommet  =   chemin.get(chemin.size()-1);
        System.out.println("Dernier sommet : " + dernierSommet.ID);
      ArrayList<Vertex> sommetsSortants =  Vertex.FindVertexWithID(dernierSommet.ID,g).getAllNeighbours(g);
      AssignerDistancesDesSommetsSortantsParRapportAuDernierSommet(sommetsSortants,dernierSommet);
    }

    private void VoirLesDistances()
    {
        for (int i = 0; i < distances.length; i++) {
            System.out.println(String.format("Distance du sommet {%d} --> {%d} ",i,distances[i]));
        }
    }
    
    private void VoirLeChemin()
    {
        System.out.println("Affichage du chemin pour l'itération : " + chemin.size() + "/" + listeSommets.size() );
        for (int i = 0; i < chemin.size(); i++) {
            if(i < chemin.size()-1)
            System.out.print(chemin.get(i).ID + "  => ");
            else 
            System.out.print(chemin.get(i).ID + "\n");
           }
    }
    
    private String ObtenirCode(int etape)
    {
        String code = "";
        for (int i = 0; i < etape+1; i++) {
            code += Integer.toString(chemin.get(i).ID);
        }
        return code;
    }
    
    private void AssignerDistancesDesSommetsSortantsParRapportAuDernierSommet(ArrayList<Vertex> sommetsSortants, Vertex dernierSommetChemin) 
    {
        for (Vertex sommet : sommetsSortants)
        {
            // On ne touche pas aux sommets qui ont déjà été ajoutés au chemin pour leurs distances (verrouillage)
            if(!chemin.contains(Vertex.FindVertexWithID(sommet.ID, g)))
            {
                // comment recalcule t'on une distance ? on prend la distance du premier sommet jusqu'à celui ci
                distances[sommet.ID] = distances[dernierSommetChemin.ID] + sommet.getArcComingFrom(dernierSommetChemin.ID).value;
                Vertex.FindVertexWithID(sommet.ID, g).graphPred[etape_ID] = dernierSommetChemin.ID;
                // Une fois qu'on a recalculé et assigné les distances, on peut passer à l'étape suivante
                // Il faut ajouter le nouveau sommet au chemin en comparant toutes nos distances
                   ActualiserMatriceDeDijkstra(etape_ID);
            }            
        }
        // On ajoute le nouveau sommet avec la distance minimale
             int sommetMinIndex = ObtenirLeSommetHorsCheminAvecLaDistanceMinimale();  
                AjouterAuChemin(Vertex.FindVertexWithID(sommetMinIndex, g));
        System.out.println("Sommet Ajouté : "+ chemin.get(chemin.size()-1));
        VoirLesDistances();
        System.out.println("Taille actuelle " + chemin.size() + "/" + listeSommets.size() );
    }

    private int ObtenirLeSommetHorsCheminAvecLaDistanceMinimale() {
        int minDist = Integer.MAX_VALUE;
        int ID_Sommet = Integer.MAX_VALUE;
        for (int i = 0; i < distances.length; i++) {
            if(!chemin.contains(Vertex.FindVertexWithID(i, g))) // On vérifie que le sommet n'est pas verrouillé
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

    private void AfficherTableauDijsktra()
    {
        // Correction des predecesseurs dans le graphe
        for(int i =0 ; i < distances.length ;i++)
        {
            Vertex v = Vertex.FindVertexWithID(i, g);
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
            System.out.print(ObtenirCode(i) + "\t|");
            for (int j = 0; j <  listeSommets.size(); j++) { // Colonnes qui representent les sommets
                if(dijkstraMatrix[i][j] !=Integer.MAX_VALUE)
                    System.out.print( dijkstraMatrix[i][j] +"("+ Vertex.FindVertexWithID(j, g).graphPred[i] + ")" );
                else 
                    System.out.print("∞");
                System.out.print("\t");
            }
            System.out.println();
        }
    }
    
    // Permet de remplir la maitrice Dijkstra
    private void ActualiserMatriceDeDijkstra(int etape) {
            for (int sommet = 0; sommet < listeSommets.size(); sommet++) {
                            dijkstraMatrix[etape][sommet]= distances[sommet];
            } 
    }

}
