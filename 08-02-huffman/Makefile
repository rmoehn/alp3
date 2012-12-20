target/08-02-huffman-1.0-SNAPSHOT-jar-with-dependencies.jar: src/main/java/alp3/ueb/Huffman.java src/main/java/alp3/ueb/SensibleBitSet.java
	mvn package
	mvn assembly:single

encode: target/08-02-huffman-1.0-SNAPSHOT-jar-with-dependencies.jar 
	java -jar target/08-02-huffman-1.0-SNAPSHOT-jar-with-dependencies.jar encode deutschland-wintermärchen.txt compressed tree 1 

decode: target/08-02-huffman-1.0-SNAPSHOT-jar-with-dependencies.jar 
	java -jar target/08-02-huffman-1.0-SNAPSHOT-jar-with-dependencies.jar decode compressed decompressed tree > bla

clean:
	rm compressed decompressed tree
