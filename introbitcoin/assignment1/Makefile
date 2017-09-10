# building the TxHandler class

JCC = javac
JFLAGS = -g


default: TxHandler.class


TxHandler.class: TxHandler.java Transaction.java UTXO.java UTXOPool.java Crypto.java
				$(JCC) $(JFLAGS) TxHandler.java 

#Transaction.class: Transaction.java
#				$(JCC) $(JFLAGS) Transaction.java

#UTXO.class: UTXO.java
#				$(JCC) $(JFLAGS) UTXO.java

#UTXOPool.class: UTXOPool.java
#				$(JCC) $(JFLAGS) UTXOPool.java

clean: 
		rm -f *.class
