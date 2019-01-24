package processes;

import main.Network;
import messages.Message;
import messages.MOrder;
import messages.MRequestTransaction;
import messages.MAcceptTransaction;


import coins.Coin;
import coins.Wallet;

public class Customer extends CProcess {
	
	private final Wallet wallet;

	public Customer(int pid){
		super(pid);
		wallet = new Wallet(pid);
	}

	@Override
	protected void receive(Message m) {
		if (m.getClass() == MAcceptTransaction.class){
			receive((MAcceptTransaction) m);
		} else if (m.getClass() == MOrder.class){
			receive((MOrder) m);
		}
	}

	private void receive(MAcceptTransaction m) {
		wallet.insertSignature(m.senderId, m.sc);
	}

	private void receive(MOrder m) {
		Coin coin = wallet.getCoin();
		if(coin !=null){
			MRequestTransaction rm = new MRequestTransaction(coin, pid, m.toId);
			for(int i = 0; i < Network.numberNotaries(); i++){
				Network.getNotary(i).send(rm);
			}
		}
	}

}
