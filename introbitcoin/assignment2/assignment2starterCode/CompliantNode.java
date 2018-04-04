import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;

import static java.util.stream.Collectors.toSet;

/* CompliantNode refers to a node that follows the rules (not malicious)*/


/* Consensus algorithm would simply be trying to maximize the set of transactions, since all transactions are valid.

Simple way of achieving this would be comparing all sets from all followers. However, runtime of this would be huge. n followers with k transactions means we have k*O(n) checks per node for a total of k*O(n^2) every round. If there are r rounds, we have O(rkn^2) checks

I'm trying to implement a system where the set of transactions sent is a set of "new" transactions. Runtime: k*O(n) checks per node, k*O(n^2) every round. Number of rounds does not matter anymore as every tx will be "new" only once. 

*/
public class CompliantNode implements Node {


	private boolean[] followees;
	private Set<Transaction> pendingTransactions;

    public CompliantNode(double p_graph, double p_malicious, double p_txDistribution, int _numRounds) {
    }

    public void setFollowees(boolean[] _followees) {
		
		this.followees = followees;
    }

    public void setPendingTransaction(Set<Transaction> pendingTransactions) {
		
		this.pendingTransactions = pendingTransactions;
	}

    public Set<Transaction> sendToFollowers() {
		
		return this.pendingTransactions;

    }

    public void receiveFromFollowees(Set<Candidate> candidates) {
		for (Candidate c : candidates)
			this.pendingTransactions.add(c.tx);
	}
}
