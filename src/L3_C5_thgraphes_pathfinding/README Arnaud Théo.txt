
Voici à quoi servent les fichiers 
--------------------------------------

#ThGraphes_pathfinding --> Fichier main / point d'entrée du programme
#Arc --> Contient la classe Arc  : un arc est entre 2 sommets, l'arc possède une valeur
#PathAlgorithm --> Contient juste l'enum PathAlgorithm
#Vertex --> Contient la classe Vertex, qui veut dire sommet, il y'a des fonctions pour obtenir les arcs entrants sortants. 
#Graph --> Contient la classe Graph qui peut construire un graphe à partir d'un chemin d'un fichier texte. 
-Un graphe peut pour l'instant afficher toute sa structure avec printStructure(). 
-Il peut aussi afficher sa matrice d'adjacence (qui ignore la valeur multipliée par 2 pour les boucles pour le moment ) avec printAdjMatrix()
-Il peut afficher le tableau de recherche de chemins avec CalculatePathfinding() pour l'instant , utiliser cette fonction 
affiche juste la première ligne correctement pour Dijkstra
#sampleGraph --> fichier txt qui est donné dans le sujet de projet de graphes pour générer un graphe de test, c'est lui qui est chargé
automatiquement pour l'instant

Remarque
----------
Chaque objet possède une fonction toString() personnalisée, ça veut dire que vous pouvez directement un avoir un affichage console
pour chaque objet :

par exemple si vous faîtes:

Arc a = new Arc(3,1,18);
System.out.println(a);

Cela nous affiche directement dans la console : 

(1)--(3)-->(18)
