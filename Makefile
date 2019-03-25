default:
	javac src/L3_C5_thgraphes_pathfinding/*.java

clean:
	rm -rf src/L3_C5_thgraphes_pathfinding/*.class
	rm -rf src/*.txt

run: default
	cp *.txt src/
	cd src && java L3_C5_thgraphes_pathfinding.L3_C5_ThGraphes_pathfinding
