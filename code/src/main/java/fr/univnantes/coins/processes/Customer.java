package fr.univnantes.coins.processes;

import fr.univnantes.coins.main.Network;
import fr.univnantes.coins.messages.*;
import fr.univnantes.coins.coins.Coin;
import fr.univnantes.coins.coins.Wallet;

public class Customer extends CProcess {
	private final Wallet wallet;

	public Customer(int pid) {
		super(pid);
		wallet = new Wallet(pid);
	}

	@Override
	protected void receive(Message m) {
		if(m instanceof MAcceptTransaction) {
			receive((MAcceptTransaction)m);
		} else if(m instanceof MOrder) {
			receive((MOrder)m);
		}
	}

	private void receive(MAcceptTransaction m) {
		wallet.insertSignature(m.senderId, m.sc);
	}

	private void receive(MOrder m) {
		Coin coin = wallet.getCoin();
		if(coin != null) {
			MRequestTransaction rm = new MRequestTransaction(coin, pid, m.toId);
			for(int i = 0; i < Network.numberNotaries(); i++) {
				Network.getNotary(i).send(rm);
			}
		}
	}

}
