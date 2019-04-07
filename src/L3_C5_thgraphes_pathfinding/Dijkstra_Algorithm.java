/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package L3_C5_thgraphes_pathfinding;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;

/**
 *
 * @author franc
 */
public class Dijkstra_Algorithm 
{
    int[] distances;
    ArrayList<Vertex> finalPath;
    L3_C5_Graph graph;
    int srcVertexID = -1;
    int lastPredValue = -1;
    int[] visited;
    public Dijkstra_Algorithm(L3_C5_Graph g, Vertex srcVertex, Vertex destVertex)
    {
        finalPath = new ArrayList<Vertex>();
        graph = g;
        visited  = new int[g.allVertex.size()];
        distances = new int[g.allVertex.size()];
        for (int i = 0; i < distances.length; i++) {
            distances[i] = 0;
        }
                // Ajout du sommet 0
        ArrayList<Vertex> path = new ArrayList<>();
        Vertex curVertex = srcVertex;
        srcVertexID = srcVertex.ID;
        lastPredValue = srcVertexID;
        ShowVisitedCount(visited);
        path.add(srcVertex);
        finalPath.add(srcVertex);
        curVertex = minDistNeighbours(srcVertex);
        if(curVertex == null)
        {
             System.out.println("Erreur le second sommet n'a pas été trouvé");
             return;
        }
        distances[0] = 0;
        RecalculateDistances(path,true);
                
                
                
        // Ajout du sommet 4
        path.add(curVertex);
        finalPath.add(srcVertex);
        for (Vertex v : graph.allVertex) {
            if(!path.contains(v))
            {
                distances[v.ID] = 0;
            }
        }
        ShowPath(path);
        ShowDistances();
        RecalculateDistances(path,false);
        ShowPath(path);   

        ShowDistances();
        
       // Ajout du sommet 3
       curVertex = minDistNeighbours(curVertex);
        path.add(curVertex);   
        ShowPath(path);
        
        ShowDistances();
        // Ajout du sommet 1
        curVertex = minDistanceVertexNotInList(path);
        int curVertexIndex = curVertex.ID;
         path.add( curVertex);
               for (Vertex v : graph.allVertex) {
            if(!path.contains(v))
            {
                distances[v.ID] = 0;
            }
        }
        RecalculateDistances(path,false);
        ShowPath(path);
//      curVertex = path.get(path.size()-2);
//              // Ajout du sommet 1
        curVertex = minDistanceVertexNotInList(path);
        
        path.add(curVertex);   
        ShowPath(path);

        
//        System.out.println("Le meileur sommet à prendre serait le sommet "+ g.allVertex.get(minIndex)+" :  (distance de "+  distances[minIndex]+")");
    }
    

    

    private void ShowDistances()
    {
        for (int i = 0; i < distances.length; i++) {
            System.out.println(String.format("Distance du sommet {%d} --> {%d} ",i,distances[i]));
        }
    }

    private void RecalculateDistances(ArrayList<Vertex> path, boolean firstIteration)
    {
        System.out.println("RecalculateDistances()");
        ArrayList<Vertex> verticesToUpdate = new ArrayList<>(); // ce qui est en vert sur l'exemple du cours
        if(firstIteration)
        {
            verticesToUpdate.addAll(Vertex.FindVertexWithID(srcVertexID, graph).getAllNeighbours(graph));
        }
        else 
        {
            
            verticesToUpdate.addAll(getAllNeighboursForPath(path));
            verticesToUpdate.removeAll(path);
        }
                for (int i = 0; i < verticesToUpdate.size(); i++) {
                    System.out.println("A recalculer --> " + verticesToUpdate.get(i).ID);}
        int arcBetweenCurAndPrecDist = Integer.MAX_VALUE;
        for (int i = 0; i < verticesToUpdate.size(); i++) {
            int curVertexID = verticesToUpdate.get(i).ID;
            if(firstIteration)
                distances[curVertexID] += verticesToUpdate.get(i).getArcComingFrom(srcVertexID).value ;
            else
            {
                                Vertex vertexToUpdate = verticesToUpdate.get(i);
                     System.out.println("//////////////////MISE A JOUR DU SOMMET  : "+ vertexToUpdate.ID+"/////////////////////");

                           int precDist = distances[path.get(path.size()-1).ID];
                System.out.println("Sa distance précédente est " + precDist);
                    int precVertex = path.get(path.size()-1).ID;
                System.out.println("Son sommet précédent est  " + precVertex );
//                if(Vertex.FindVertexWithID(precVertex, graph).getArcGoingTo(i) == null)
//                {
//                    System.out.println("Erreur calcul des distances pour tous les sommets");
//                        precVertex = GetVertexFromLowestIngoingArcValueFromPath(path, i);
//                }
                try
                {
                    arcBetweenCurAndPrecDist = vertexToUpdate.getArcComingFrom(precVertex).value;
                }
                catch(Exception e)
                {
                        System.out.println("Erreur calcul des distances pour tous les sommets on regarde les sommets voisins faisant partie du path");
                        precVertex = GetVertexFromLowestIngoingArcValueFromPath(path, verticesToUpdate.get(i).ID);
                        arcBetweenCurAndPrecDist = vertexToUpdate.getArcComingFrom(precVertex).value;
                   }
              
                System.out.println(String.format("Donc par somme : (%d) + (%d) = %d",precDist,arcBetweenCurAndPrecDist,precDist+arcBetweenCurAndPrecDist));
                System.out.println("L'arc venant "+path.get(path.size()-1).ID+" de jusqu'à ce sommet a pour valeur " + arcBetweenCurAndPrecDist);
                distances[curVertexID] = precDist + arcBetweenCurAndPrecDist ;

            }
                        
            System.out.println("Maj Sommet :  " + verticesToUpdate.get(i).ID  + " avec une valeur de " + distances[verticesToUpdate.get(i).ID] );
        }
    }
    
    private void ShowPath(ArrayList<Vertex> path)
    {
        System.out.println("Affichage du chemin pour l'itération : " + path.size());
        for (int i = 0; i < path.size(); i++) {
            if(i < path.size()-1)
            System.out.print(path.get(i).ID + "  => ");
            else 
            System.out.print(path.get(i).ID + "\n");
        }
    }
    
    private void ShowVisitedCount(int[] visited)
    {
         if(visited.length == 0)
             System.out.println("Aucun sommet n'a été visité");
         else 
             System.out.println("Le nombre de sommets visité est " + visited.length);
    }
    
    public void GetPath()
    {
        
    }

    private Vertex minDistNeighbours(Vertex srcVertex) {
        int minDist = Integer.MAX_VALUE;
        Vertex minNeighbour = null;
        for(Vertex neighbour : srcVertex.getAllNeighbours(graph))
        {
           int neighbourArcValue =  distances[srcVertex.ID]+ neighbour.getArcComingFrom(srcVertex.ID).value;
           distances[srcVertex.ID] = neighbourArcValue;
            if( neighbourArcValue < minDist)
            {
                minNeighbour = neighbour;
                minDist = neighbourArcValue;
            }
        }
        return minNeighbour;
    }

    private ArrayList<Vertex> addNeighbours(ArrayList<Vertex> verticesToUpdate) {
        HashSet<Vertex> neighbours = new HashSet<>();
        for(Vertex v : verticesToUpdate)
        {
            neighbours.addAll(v.getAllNeighbours(graph));
        }
        return new ArrayList<>(neighbours);
    }
    

// 

    //On récupère l'indice du sommet du path rouge qui possede le plus petit arc qui entre dans le sommet indique
    // Pour ça on compare un minimum
    private int GetVertexFromLowestIngoingArcValueFromPath(ArrayList<Vertex> path, int vertexID  ) {
        int minArcValue = Integer.MAX_VALUE;
        int minIndex = Integer.MAX_VALUE;
        for (Vertex v : path)
        {
            System.out.println("Analyse sommet  :" + v.ID);
            L3_C5_Arc arc = v.getArcGoingTo(vertexID);
            if( arc != null)
            {
                if(arc.value < minArcValue)
                {
                   minArcValue = arc.value;
                   minIndex = arc.getInitialExtremity(graph).ID;
                }
            }
        }
        return minIndex;
    }

    private Vertex minDistanceVertexNotInList(ArrayList<Vertex> path) {
        Vertex minVertexNotInPath = null;
        int minDist = Integer.MAX_VALUE;
        ArrayList<Vertex> vertices = graph.allVertex;
        vertices.removeAll(path);
        for (Vertex v : vertices) 
        {
            if( distances[v.ID] < minDist  )
            {
                minVertexNotInPath = v;
                minDist = distances[v.ID];
            }
        }
        return minVertexNotInPath;
    }

    private ArrayList<? extends Vertex> getAllNeighboursForPath(ArrayList<Vertex> path) 
    {
        HashSet set =  new HashSet();
        for(Vertex v   : path)
        {
            set.addAll(v.getAllNeighbours(graph));
        }
        return new ArrayList<Vertex>(set);
    }
    
    
}
