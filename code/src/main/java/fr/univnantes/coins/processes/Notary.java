package fr.univnantes.coins.processes;

import fr.univnantes.coins.main.DynamicArray;
import fr.univnantes.coins.main.Network;
import fr.univnantes.coins.messages.*;
import fr.univnantes.coins.coins.UnsignedChunk;

public class Notary extends CProcess {
	private DynamicArray<Integer> nextTransaction = new DynamicArray<>();
	
	public Notary(int pid) {
		super(pid);
	}
		
	@Override
	protected void receive(Message m) {
		if(m instanceof MRequestTransaction) {
			receive((MRequestTransaction)m);
		} else if(m instanceof MCreateCoin) {
			receive((MCreateCoin)m);
		}
	}

	private void receive(MRequestTransaction m) {
		int next = nextTransaction.getOrCreate(m.coin.coinId, 0);
		if(m.coin.isValid() && m.coin.ownerId == m.fromId & next <= m.coin.sequenceNumber) {
			nextTransaction.set(m.coin.coinId, next + 1);
			UnsignedChunk usc = new UnsignedChunk(m.coin.coinId, m.toId, next + 1);
			MAcceptTransaction response = new MAcceptTransaction(pid, usc.sign(pid));
			Network.getCustomer(m.toId).send(response);
		}
	}

	private void receive(MCreateCoin m) {
		int next = nextTransaction.getOrCreate(m.coinId, 0);
		if(next == 0) { // and creation matches monetary politics...
			nextTransaction.set(m.coinId, 1);
			UnsignedChunk usc = new UnsignedChunk(m.coinId, m.toId, 1);
			MAcceptTransaction response = new MAcceptTransaction(pid, usc.sign(pid));
			Network.getCustomer(m.toId).send(response);
		}
	}
}
