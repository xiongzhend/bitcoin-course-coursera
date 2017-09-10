import java.util.ArrayList;
import java.util.Arrays;

public class TxHandler {

    /**
     * Creates a public ledger whose current UTXOPool (collection of unspent transaction outputs) is
     * {@code utxoPool}. This should make a copy of utxoPool by using the UTXOPool(UTXOPool uPool)
     * constructor.
     */


	private UTXOPool uPool;

    public TxHandler(UTXOPool utxoPool) {
        uPool = new UTXOPool(utxoPool);
    }

    /**
     * @return true if:
     * (1) all outputs claimed by {@code tx} are in the current UTXO pool, 
     * (2) the signatures on each input of {@code tx} are valid, 
     * (3) no UTXO is claimed multiple times by {@code tx},
     * (4) all of {@code tx}s output values are non-negative, and
     * (5) the sum of {@code tx}s input values is greater than or equal to the sum of its output
     *     values; and false otherwise.
     */
    public boolean isValidTx(Transaction tx) {

		double sumOutput = 0;
		double sumInput = 0;

		Crypto tempCrypto = new Crypto();

		//Defensive copy of UTXOpool. Checks for doublespend via removal of "used" UTXOs. Actual removal from uPool done in handleTxs.
		UTXOPool doubleSpendChecker = new UTXOPool(uPool);
		Transaction.Output[] tempOutput = new Transaction.Output[tx.numInputs()];
	

		// Checks by creating a copy of UTXO from 1 input, and testing it against UTXOPool
		for (int i = 0; i < tx.numInputs(); i++){

			Transaction.Input in = tx.getInput(i);
			UTXO tempUTXO = new UTXO(in.prevTxHash, in.outputIndex);
			if (doubleSpendChecker.contains(tempUTXO) == false){
				return false;
			}
			else{
				tempOutput[i] = uPool.getTxOutput(tempUTXO);

				if (!(tempCrypto.verifySignature(tempOutput[i].address, tx.getRawDataToSign(i), in.signature)))
				{
					return false;
				}
				doubleSpendChecker.removeUTXO(tempUTXO);
			}

			// Summing total input
			tempOutput[i] = uPool.getTxOutput(tempUTXO);
			sumInput += tempOutput[i].value;

		}

		// Test for negative output here while summing total output
		for (int k = 0; k < tx.numOutputs(); k++){

			double tempAmount = tx.getOutput(k).value;

			if (tempAmount < 0){
				return false;
			}			
			else{
				sumOutput += tempAmount;
			}
		}

		if (sumOutput > sumInput){
			return false;
		}

		return true;
    }

    /**
     * Handles each epoch by receiving an unordered array of proposed transactions, checking each
     * transaction for correctness, returning a mutually valid array of accepted transactions, and
     * updating the current UTXO pool as appropriate.
     */
    public Transaction[] handleTxs(Transaction[] possibleTxs) {
        // IMPLEMENT THIS

		ArrayList<Transaction> newList = new ArrayList<Transaction>();

		for (Transaction tx : possibleTxs){

			if (isValidTx(tx)){
				newList.add(tx);
				
				// Approve and remove UTXO's being used here
				for (int j = 0; j < tx.numInputs(); j++){
					Transaction.Input tempInput = tx.getInput(j);
					UTXO tempUTXO = new UTXO(tempInput.prevTxHash, tempInput.outputIndex);
					uPool.removeUTXO(tempUTXO);
				}

				// Adding outputs from current Tx to UTXO
				for (int k = 0; k < tx.numOutputs(); k++){
					Transaction.Output tempOutput = tx.getOutput(k);
					UTXO tempUTXO2 = new UTXO(tx.getHash(), k);
					uPool.addUTXO(tempUTXO2, tempOutput);
				}
						
			}
		}

		Transaction[] returnArray = new Transaction[newList.size()];
		returnArray = newList.toArray(returnArray);

		return returnArray;
    }

}
