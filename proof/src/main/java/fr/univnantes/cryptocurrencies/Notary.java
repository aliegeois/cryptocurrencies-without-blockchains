package fr.univnantes.cryptocurrencies;

import static fr.univnantes.cryptocurrencies.Consts.*;

public class Notary {

	private int myNumber;

	private Notary[] notariesList/* = new Notary[TOTAL_NOTARIES]*/;

	private Acceptance[][] accepts;

	public Notary(int myNumber, Notary[] notariesList, Acceptance[][] accepts) {
		this.myNumber = myNumber;
		this.notariesList = notariesList;
		//this.accepts = new Acceptance[TOTAL_COINS][TOTAL_NOTARIES];
		this.accepts = accepts;
	}

	public void requestFromClient(Coin coin, Client receiver, Client sender) {
		receiveFromClient(coin, receiver, sender);
	}

	public void receiveFromClient(Coin coin, Client receiver, Client sender) {
		int mySequenceNumber = accepts[coin.getCoinId()][myNumber].getSequenceNumber();
		//Client owner = accepts[coin.getCoinId()][myNumber].getOwner();
//                    System.out.println(owner);

		//if (coin.getOwner() == sender && mySequenceNumber == coin.getSequenceNumber() && owner == coin.getOwner()) {
			sendToAllNotaries(coin, receiver, mySequenceNumber);
		//}
	}

	public void sendToAllNotaries(Coin coin, Client receiver, int sequenceNumber) {
		for (int i = 0; i < TOTAL_NOTARIES; i++) {
			if (i != myNumber)
				notariesList[i].receiveFromNotary(coin, receiver, sequenceNumber);
		}
	}

	public void receiveFromNotary(Coin coin, Client receiver, int sequenceNumber) {
		accepts[coin.getCoinId()][myNumber] = new Acceptance(sequenceNumber + 1, receiver);
		//coin.setOwner(receiver);
		//coin.setSequenceNumber(accepts[coin.getCoinId()][myNumber].getSequenceNumber());
	}

	public Acceptance[][] getAccepts() {
		return accepts;
	}
}
