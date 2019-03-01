package fr.univnantes.cryptocurrencies;

public class Acceptance {

	private int sequenceNumber;

	private Client owner;

	public Acceptance(int sequenceNumber, Client owner) {
		this.sequenceNumber = sequenceNumber;
		this.owner = owner;
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
