# building the Simulation class

JCC = javac
JFLAGS = -g


default: Simulation.class


Simulation.class: Simulation.java CompliantNode.java MaliciousNode.java Candidate.java Node.java Transaction.java
				$(JCC) $(JFLAGS) Simulation.java 

#Transaction.class: Transaction.java
#				$(JCC) $(JFLAGS) Transaction.java

#UTXO.class: UTXO.java
#				$(JCC) $(JFLAGS) UTXO.java

#UTXOPool.class: UTXOPool.java
#				$(JCC) $(JFLAGS) UTXOPool.java

clean: 
		rm -f *.class
