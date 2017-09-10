import java.util.ArrayList;
import java.util.Arrays;

public class MaxFeeTxHandler{


	private double maxVal;
	private UTXOPool uPool;

	MaxFeeTxHandler(UTXOPool newUTXOPool){
		uPool = new UTXOPool(newUTXOPool);
	}
	
	public Transaction[] handleTxs(Transaction[] possibleTxs){


		double sumOutput = 0;
		double sumInput = 0;
		double difference = 0;
		boolean currTrans = true;

		ArrayList<Transaction> newList = new ArrayList<Transaction>();
		
		maxVal = 0;

		for (Transaction tx : possibleTxs){

			currTrans = true;
			for (int i = 0; i < tx.numInputs(); i++){
				Transaction.Input in = tx.getInput(i);
				UTXO tempUTXO = new UTXO(in.prevTxHash, in.outputIndex);
				if (uPool.contains(tempUTXO)){
					Transaction.Output out = uPool.getTxOutput(tempUTXO);
					sumInput = out.value;
				}
				else{
					currTrans = false;
				}	
			}
		
			for (int j = 0; j < tx.numOutputs(); j++){	
				sumOutput += (tx.getOutput(j)).value;
			}
			
			difference = sumOutput - sumInput;

			if (difference > 0 && currTrans == true){
				newList.add(tx);
			}
		}

		Transaction[] returnArr = new Transaction[newList.size()];
		returnArr = newList.toArray(returnArr);

		return returnArr;		
	}

}
