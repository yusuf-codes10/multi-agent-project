run:
	java -cp bin:lib/jade.jar jade.Boot -agents "Alice:RecepteurAgent;Bob:EmetteurAgent"

compile:
	javac -cp lib/jade.jar -d bin src/*.java

clean:
	rm -rf bin/*.class