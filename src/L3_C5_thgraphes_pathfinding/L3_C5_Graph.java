/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package L3_C5_thgraphes_pathfinding;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author franc
 */
public final class L3_C5_Graph {


    
    
    
    private int vertexCount = 0; // Nombre de sommets
    
    public static ArrayList<L3_C5_Graph> graphesMem  = new ArrayList<L3_C5_Graph>();
    public static L3_C5_Graph selectedGraph = null;
    private ArrayList<L3_C5_Arc> allArcs = new ArrayList<L3_C5_Arc>();
    public ArrayList<Vertex> allVertex = new ArrayList<Vertex>();
    private String fileName; // Le fichier qui permet de charger le graphe   
    private  boolean[][] adjMatrix;
    private int[][] incidenceMatrix;
   
        /**
     * @return the adjMatrix
     */
    public boolean[][] getAdjMatrix() {
        return adjMatrix;
    }

    /**
     * @return the incidenceMatrix
     */
    public int[][] getIncidenceMatrix() {
        return incidenceMatrix;
    }
    
    public L3_C5_Graph(String graphFilePath)
    {
         String[] fileParts =  graphFilePath.split("\\\\");
         fileName = fileParts[fileParts.length-1];
         
         File f = new File(graphFilePath);
         try (BufferedReader br = new BufferedReader(new FileReader(f))) {
         String line;
         boolean vertexCountAllocated = false;
         System.out.println("Début lecture");
         if(f.length()>0)
         {
               while ((line = br.readLine()) != null && !"".equals(line.trim()))
          {
              // On assigne le nombre de sommets
              if(!vertexCountAllocated)
              {
                  try{vertexCount = Integer.parseInt(line); }catch(NumberFormatException e){System.out.println("Erreur lors de la lecture du nombre de sommets");e.printStackTrace();}
                  vertexCountAllocated = true;
             }
              else
              {
                         // On analyse une ligne quelconque du graphe qui nous donne un arc
             String[] arcValuesString = line.split(" "); // on obtient un tableau comportant en  0 : extrémité_initiale en 1 valeur_de_l’arc et en 2 extrémité_terminale
            
              
             
             // avec ce tableau, on crée un arc car on veut 1 arc par ligne
             L3_C5_Arc arc = new L3_C5_Arc(Integer.parseInt(arcValuesString[1]),Integer.parseInt(arcValuesString[0]),Integer.parseInt(arcValuesString[2])); 
            System.out.println("arc crée : "+arc);
             allArcs.add(arc);
              }
      
          }
         }
         else
         {
             System.out.println("Le fichier " + fileName + " existe mais est vide");
         }
}         catch (IOException ex) {
            Logger.getLogger(L3_C5_Graph.class.getName()).log(Level.SEVERE, null, ex);
        }
         
              ArrayList<Integer> createdVertexValues = new ArrayList<Integer>();
              ArrayList<Vertex> vertexList = new ArrayList<Vertex>();
              
              //On obtient la valeur maximale du dernier sommet avec au moins un arc
              int lastIndicatedVertexValue = allArcs.get(allArcs.size()-1).initExtremityValue;
              
              // A l'aide de tous nos arcs, on crée les sommets
              for (int i = 0; i< vertexCount;i++)
              {
                  Vertex v = new Vertex(i);
                  for(L3_C5_Arc a : allArcs)
                  {
                      if(v.ID == a.initExtremityValue || v.ID == a.termExtremityValue)
                      v.arcs.add(a);
                  }
                  vertexList.add(v);
              }
              
              // On regarde si il existe des sommets sans arcs
              int unlinkedVertexCount =  vertexList.size()-vertexCount;
              int i =0;
              while(unlinkedVertexCount>0) // Création de sommets sans arcs
              {
                  vertexList.add(new Vertex(vertexCount-unlinkedVertexCount)); 
              }
              for(Vertex v : vertexList)
              {
                  v.predValues = GetPredecessorsValues(v) ;
                  v.succValues = GetSuccValues(v);
              }
              allVertex = vertexList;
              graphesMem.add(this);
    }
    
    @Override
    public String toString() // On affiche comme dans le fichier texte (nombre sommets + les arcs)pour le graphe choisi
    {
        String s ="Affichage du Graphe" + System.lineSeparator();
        s+="Nombre de sommets : " + vertexCount + System.lineSeparator();
        for( L3_C5_Arc a : allArcs)
        {
            s+=a.toString()+System.lineSeparator();
        }
        s+="Fin d'affichage";
        return s;
    }
    
    
    public void printStructure()
    {
        System.out.println("---------------Début de l'affichage long--------------");
        System.out.println("Nom du fichier : " + fileName);
        System.out.println(this);
        System.out.println("Affichage des Sommets  : " + System.lineSeparator());
        int i =0;
        for(Vertex v : allVertex )
        {
            System.out.println("Affichage sommet n°" + i);
            System.out.println(v.toString());

            i++;
        }
        System.out.println("---------------Fin de l'affichage long--------------");
    }


    
    //  matrice d’adjacence booléenne, avec, préférablement, les 0 remplacés par du vide ou des traits
   // A rendre compatible avec les boucles
    void printAdjMatrix()
    {
        System.out.println("Affichage de la matrice d'adjacence");
        System.out.println("Nombre de sommets : "+vertexCount);
        System.out.println("Nombre d'arcs     : "+allArcs.size());
        // si la matrice d'adjacence n'a pas encore été créee on le fait maintenant
        if(getAdjMatrix()==null || getAdjMatrix().length==0)
        {
             adjMatrix = new boolean[vertexCount][vertexCount];
        for(int i  = 0 ; i < vertexCount ; i++)
        {
            for(int j =0; j < vertexCount ; j++)
            {
                for(L3_C5_Arc a : Vertex.FindVertexWithValue(i,this).arcs )
                {
                    if(i == a.initExtremityValue && j==a.termExtremityValue)
                    {
                         adjMatrix[i][j] = true;
                    }
                }
            }
        }        
        }
                    System.out.print("\t");
                    for(int i = 0; i < vertexCount;i++)
                        System.out.print(" " + i+"\t");
                    System.out.print("\td+"+System.lineSeparator());
                    for(int i =0; i < vertexCount;i++)
                    {
                        System.out.print((i) +"\t|");
                        int lineSum = 0;
                        for(int j=0; j < vertexCount;j++)
                        {
//                            if(i==0)
//                            System.out.print((j+1) + "\t|"); // affichage des numéros de lignes pour les colonnes
//                            else
                            
                            String cellDisplay;
                            if(!getAdjMatrix()[i][j]) cellDisplay="-";
                            else 
                            {
                                cellDisplay = "1";
                                lineSum++;
                            }
                              
                        System.out.print( cellDisplay + "\t|");
                         if(j==vertexCount-1)
                                System.out.print("\t"+lineSum);

                        }
                        System.out.println();
                    }
       String finalLine = "d-";
        for (int i = 0; i < vertexCount; i++) {
                    int columnSum = 0;
              for (int j = 0; j < vertexCount; j++) {
                if(getAdjMatrix()[j][i]) columnSum++;
                if(j==vertexCount-1)
                    finalLine+="\t|"+columnSum;
            }
        }
        System.out.println(finalLine);
    }
    
    void printValuesMatrix()
    {
        
    }
    
    // une matrice des valeurs où là aussi les arcs non existants sont préférablement remplacés par du vide ou des traits
        void printIncidenceMatrix()
    {
          System.out.println("Affichage de la matrice d'incidence");
          System.out.println("Nombre de sommets : "+vertexCount);
          System.out.println("Nombre d'arcs     : "+allArcs.size());
        // si la matrice d'adjacence n'a pas encore été créee on le fait maintenant
        if(getIncidenceMatrix()==null || getIncidenceMatrix().length==0)
        {
            
            // ASSIGNATION DU TABLEAU
             incidenceMatrix = new int[vertexCount][allArcs.size()];
            int curVertexId;
            for(int arcIndex = 0; arcIndex < allArcs.size() ; arcIndex++)
            {
                for(int vertIndex = 0; vertIndex < vertexCount; vertIndex++)
                {
                      curVertexId = Vertex.FindVertexWithValue(vertIndex, this).ID;
                      if(allArcs.get(arcIndex).initExtremityValue == curVertexId )
                      incidenceMatrix[vertIndex][arcIndex] = 1;
                      else if  (allArcs.get(arcIndex).termExtremityValue == curVertexId)   
                          incidenceMatrix[vertIndex][arcIndex] = -1;
                      else incidenceMatrix[vertIndex][arcIndex] = 0;
                }
            }
        }
        
        // AFFICHAGE DU TABLEAU
        
                    System.out.print("\t");
                    for(int i = 0; i < allArcs.size();i++)
                        System.out.print(" " +i+"("+ + allArcs.get(i).value+")\t");
                    System.out.print("\t"+System.lineSeparator());
             for(int vertIndex = 0; vertIndex < vertexCount ; vertIndex++)
            {
                System.out.print((vertIndex) +"\t|");
                for(int arcIndex = 0; arcIndex < allArcs.size(); arcIndex++)
                      System.out.print(   incidenceMatrix[vertIndex][arcIndex]+"\t");
                System.out.println();
            }
    
    }
    
    // Predecesseurs directs (rang n-1)
     public ArrayList<Integer> GetPredecessorsValues(Vertex v)
    {
        ArrayList<Integer> preds = new ArrayList<>();
        for(L3_C5_Arc a : v.arcs)
            if( a.termExtremityValue==v.ID)
                preds.add(v.ID);
        return preds;
    }
     
    public ArrayList<Integer> GetSuccValues(Vertex v)
    {
        ArrayList<Integer> succs = new ArrayList<>();
        for(L3_C5_Arc a : v.arcs)
            if( a.initExtremityValue==v.ID)
            {
                succs.add(a.termExtremityValue);
            }
        return succs;
    }
     
    public L3_C5_PathAlgorithm GetRecommendedPathAlgorithm()
    {
        for(L3_C5_Arc a : allArcs)
            if(a.value<0) return L3_C5_PathAlgorithm.Bellman; // Si au moins 1 arc a une valeur négative, alors Bellman est obligatoire 
        return L3_C5_PathAlgorithm.Dijkstra;
    }
    

    //Dijkstra seulement
    int getWeightForPath(ArrayList<Vertex> vertices)
    { 
        
        int weight = 0;
        HashSet<L3_C5_Arc> arcs = new HashSet<>();
        for (int i = 0; i < vertices.size(); i++) 
        {
          arcs.addAll(vertices.get(i).arcs);
        }
        System.out.println("weight : "  + arcs);
        return weight;
    }
    
    int getWeightForPathIntegers(ArrayList<Integer> verticesValues)
    { 
        int weight = 0;
        HashSet<L3_C5_Arc> arcs = new HashSet<>();
                    Vertex v;
        for (int i = 0; i < verticesValues.size(); i++) 
        {
            v = Vertex.FindVertexWithValue(verticesValues.get(i), this);
            arcs.addAll(v.arcs);
            weight+=v.arcs.get(i).value;
        }
        System.out.println(arcs);
        System.out.println("weight = "  + weight);
        
        return weight;
    }
    
    public void CalculateAndShowPathfinding(L3_C5_PathAlgorithm algotype, boolean showStepByStepDetails)
    {
        ArrayList<Integer> vertexPathsValues = new ArrayList<>();
        ArrayList<Integer> arcPathValues = new ArrayList<>();
        Vertex lastPathVertex = Vertex.FindVertexWithValue(0, this); // Indique le dernier sommet par lequel est passé notre pathfinding
        vertexPathsValues.add(lastPathVertex.ID);
        ArrayList<String> codes = new ArrayList<>();
        System.out.println("Algorithme utilisé pour la recherche de chemin : "+ algotype.name());
        switch(algotype)
        {
            case Dijkstra: 
                for(L3_C5_Arc a : Vertex.FindVertexWithValue(0,this).getOutgoingArcs() )
                {
                    System.out.println(a);
                }
                int pathTotalValue = 0;
                
                String s = "CC";
                // Première ligne du tableau
                for(int i = 0; i< vertexCount;i++)
                    s+= "|\t"+i;
                System.out.println(s);
                String lineString = "#\t";
                                        ArrayList<Integer> succValues = GetSuccValues(allVertex.get(0));
                for(Integer succ : succValues)
                lineString+= succ + "\t";
                String[][] pathArray = new String[vertexCount][vertexCount];
               
                // Initialisation
                for(int i =0; i< vertexCount; i++)
                    for(int j =0; j < vertexCount;j++)
                        pathArray[i][j] = "∞";
                
                 for(int tabLineIndex =0; tabLineIndex< vertexCount; tabLineIndex++)
                 {
                                             System.out.println("LIGNE ACTUELLE : " + tabLineIndex);
                     boolean lastVertexAssigned = false;
                    succValues = GetSuccValues(allVertex.get(tabLineIndex));
                    for(int curVertexIndex =0; curVertexIndex < vertexCount;curVertexIndex++)
                    {
                        if(curVertexIndex==0 )
                        pathArray[tabLineIndex][curVertexIndex] = "0";
                        if(succValues.contains(curVertexIndex))
                        {
                            if(tabLineIndex == 0)
                            {
                                String cellDisplay = "";
                                for(L3_C5_Arc arc : Vertex.FindVertexWithValue(tabLineIndex,this).getOutgoingArcs())
                                {
                                    if(arc.termExtremityValue == curVertexIndex)
                                    {
                                        cellDisplay = arc.value+ "("+ tabLineIndex +")"; // Contenu d'une case
                                    }
                                        lastPathVertex = Vertex.FindVertexWithValue(tabLineIndex, this);
                                }
                                pathArray[tabLineIndex][curVertexIndex] = cellDisplay;

                                    System.out.println("On arrive à la Ligne " + tabLineIndex);
     
                                System.out.println("Dernier sommet : " + lastPathVertex);
                                System.out.println("Le 2eme sommet est : " + lastPathVertex.ID);
                                ArrayList<L3_C5_Arc> succArc = lastPathVertex.getOutgoingArcs();
                               
                                System.out.println("On prend les arcs sortants qui sont ci-dessous : " + succArc);
                                System.out.println("on les mets dans le tableau");
                                System.out.println(vertexPathsValues);
                                    for(L3_C5_Arc arc : succArc)
                                    {
                                        System.out.println("arc " + arc );
                                        if(arc.initExtremityValue == lastPathVertex.ID)
                                        {
                                            int cellValue = arcPathValues.stream().mapToInt(Integer::intValue).sum() + arc.value; // faire la somme en une ligne  
                                            cellDisplay = cellValue+ "("+ tabLineIndex +")";
                                            System.out.println("Insertion OK" + cellDisplay);
                                            // Recherchons le numero du sommet atteint par notre arc actuel
                                            
                                            System.out.println("index : " + arc.termExtremityValue);
                                            pathArray[tabLineIndex][arc.termExtremityValue] = cellDisplay;
                                             
                                            
                                        }
                                    }
                                    L3_C5_Arc minArc = lastPathVertex.getOutgoingArcs().get(0);
                                    for(L3_C5_Arc a: lastPathVertex.getOutgoingArcs())
                                        if(a.value<minArc.value)
                                            minArc=a;
                                    System.out.println("L'arc MIN à partir du premier sommet est : " + minArc.value);
                                    lastPathVertex = minArc.getTerminalExtremity(this);
                                    pathArray[tabLineIndex][minArc.termExtremityValue] = "0";
                                    System.out.println("Le sommet correspondant est : " + lastPathVertex.ID + " on part de celui ci pour trouver ses arcs sortants, donnant la ligne "+ tabLineIndex);
                                    if(!lastVertexAssigned)
                                    {
                                    arcPathValues.add(minArc.value);
                                    vertexPathsValues.add(lastPathVertex.ID);
                                    lastVertexAssigned=true;
                                    
                                    }
                                    codes.add(Integer.toString(lastPathVertex.ID));
                            }
                        }
                         
                    }
                       if(tabLineIndex>0)
                            {
                                     for(int i = 0 ; i < vertexCount; i++)
                                    {
                                          if( pathArray[tabLineIndex-1][i] == "0")
                                            pathArray[tabLineIndex][i] = "0";
                                          else if( pathArray[tabLineIndex-1][i] != "∞")
                                            pathArray[tabLineIndex][i] =  pathArray[tabLineIndex-1][i];
                                    }
                                    System.out.println("On arrive à la Ligne " + tabLineIndex);
     
                                    
                                System.out.println("Dernier sommet : " + lastPathVertex);
                                System.out.println("Le 2eme sommet est : " + lastPathVertex.ID);
                                String cellDisplay = "";
                                ArrayList<L3_C5_Arc> succArc = lastPathVertex.getOutgoingArcs();
                               
                                System.out.println("On prend les arcs sortants qui sont ci-dessous : " + succArc);
                                System.out.println("on les mets dans le tableau");
                                System.out.println(vertexPathsValues);
                                    for(L3_C5_Arc arc : succArc)
                                    {
                                        System.out.println("arc " + arc );
                                        if(arc.initExtremityValue == lastPathVertex.ID)
                                        {
                                            int cellValue = arcPathValues.stream().mapToInt(Integer::intValue).sum() + arc.value; // faire la somme en une ligne  
                                            cellDisplay = cellValue+ "("+ tabLineIndex +")";
                                            System.out.println("Insertion OK" + cellDisplay);
                                            // Recherchons le numero du sommet atteint par notre arc actuel
                                            
                                            System.out.println("verification index ligne au dessus de la case : " + arc.termExtremityValue);
                                           if( pathArray[tabLineIndex-1][arc.termExtremityValue] != "0")
                                             pathArray[tabLineIndex][arc.termExtremityValue] = cellDisplay;
                                        }
                                    }
                                       L3_C5_Arc minArc = lastPathVertex.getOutgoingArcs().get(0);
                                    for(L3_C5_Arc a: lastPathVertex.getOutgoingArcs())
                                        if(a.value<minArc.value)
                                            minArc=a;
                                    
                               
                                    
                                    System.out.println("L'arc MIN à partir du premier sommet est : " + minArc.value);
                                    lastPathVertex = minArc.getTerminalExtremity(this);
                                    pathArray[tabLineIndex][minArc.termExtremityValue] = "0";
                                    System.out.println("Le sommet correspondant est : " + lastPathVertex.ID + " on part de celui ci pour trouver ses arcs sortants, donnant la ligne "+ tabLineIndex);
                                    if(!lastVertexAssigned)
                                    {
                                    arcPathValues.add(minArc.value);
                                    vertexPathsValues.add(lastPathVertex.ID);
                                    lastVertexAssigned=true;
                                    }
                                    if(minArc.value==0)
                                        System.out.println("Il faut repartir en arriere !");
                                    codes.add(Integer.toString(lastPathVertex.ID));
                                    
                              
                            }
                 }
                 
                // Spacing count
                 int spacing = vertexCount/2;
                
                //Affichage
                for(int i =0; i< vertexCount; i++)
                {
                    System.out.println();
                    for(int j =0; j < vertexCount;j++)
                    {
                       if(j==0)
                           if(codes.size() > i)
                           {
                             System.out.print("[0]"+ codes.subList(0, i));
                             for(int curSpacing = 0;curSpacing<spacing-codes.subList(0, i).size()/2;curSpacing++) System.out.print("\t");
                           }
                           else
                           {
                              System.out.print("CODE");
                           }
                       System.out.print(pathArray[i][j] + "\t");
                    }   
                }
                
               break;
            case Bellman: 
               break;
        }
    }
    
    
    
//    GraphPath[] GetPaths()
//    {
//        return null;
//    }

    
    
    
}
