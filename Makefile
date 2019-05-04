default: all

clean:
	rm -rf make_build

all: 
	rm -rf make_build
	mkdir -p make_build/theorie_graphe
	cp *.txt make_build
	cp src/theorie_graphe/*.java make_build/theorie_graphe
	cd make_build/theorie_graphe && javac *.java

run: all
	cd make_build && java theorie_graphe.L3_C5_main

