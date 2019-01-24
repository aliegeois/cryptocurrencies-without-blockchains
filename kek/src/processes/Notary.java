package processes;

import main.DynamicArray;
import main.Network;
import messages.MCreateCoin;
import messages.Message;
import messages.MRequestTransaction;
import messages.MAcceptTransaction;


import coins.UnsignedChunk;

public class Notary extends CProcess {

	private DynamicArray<Integer> nextTransaction;
	
	public Notary(int pid){
		super(pid);
		this.nextTransaction = new DynamicArray<Integer>();
	}
		
	@Override
	protected void receive(Message m) {
		if(m.getClass()==MRequestTransaction.class){
			receive((MRequestTransaction) m);
		} else if(m.getClass()==MCreateCoin.class){
			receive((MCreateCoin) m);
		}
	}

	private void receive(MRequestTransaction m) {
		int next = nextTransaction.getOrCreate(m.coin.coinId, 0);
		if(m.coin.isValid() && m.coin.ownerId == m.fromId & next <= m.coin.sequenceNumber){
			nextTransaction.set(m.coin.coinId, next+1);
			UnsignedChunk usc = new UnsignedChunk(m.coin.coinId, m.toId, next+1);
			MAcceptTransaction response = new MAcceptTransaction(pid, usc.sign(pid));
			Network.getCustomer(m.toId).send(response);
		}
	}
	private void receive(MCreateCoin m) {
		int next = nextTransaction.getOrCreate(m.coinId, 0);
		if(next == 0){ // and creation matches monetary politics...
			nextTransaction.set(m.coinId, 1);
			UnsignedChunk usc = new UnsignedChunk(m.coinId, m.toId, 1);
			MAcceptTransaction response = new MAcceptTransaction(pid, usc.sign(pid));
			Network.getCustomer(m.toId).send(response);
		}
	}

}
