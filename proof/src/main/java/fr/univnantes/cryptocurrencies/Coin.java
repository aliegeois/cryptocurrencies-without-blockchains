package fr.univnantes.cryptocurrencies;

public class Coin {

	private int coinId, sequenceNumber;

	private Client owner;

	public Coin(int coinId, int sequenceNumber, Client owner) {
		this.coinId = coinId;
		this.sequenceNumber = sequenceNumber;
		this.owner = owner;
	}

	public int getCoinId() {
		return coinId;
	}

	public void setCoinId(int coinId) {
		this.coinId = coinId;
	}

	public int getSequenceNumber() {
		return sequenceNumber;
	}

	public void setSequenceNumber(int sequenceNumber) {
		this.sequenceNumber = sequenceNumber;
	}

	public Client getOwner() {
		return owner;
	}

	public void setOwner(Client owner) {
		this.owner = owner;
	}
}
