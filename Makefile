default:
	javac src/thgraphes_pathfinding/*.java

clean:
	rm -rf src/thgraphes_pathfinding/*.class

run: default
	cd src && java thgraphes_pathfinding.ThGraphes_pathfinding
