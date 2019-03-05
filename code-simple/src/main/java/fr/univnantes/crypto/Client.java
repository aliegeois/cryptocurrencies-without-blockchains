package fr.univnantes.crypto;

public class Client {
	private int id;

	public Client(int id) {
		this.id = id;
	}

	public boolean isMine(int coin) {
		//

		return false;
	}

	public void send(int coin, Client to) {
		sendToAllNotaries_M1(coin, to);
	}

	private void sendToAllNotaries_M1(int coin, Client to) {
		Network.notaries.forEach(notary -> notary.receive(coin, to, this));
	}

	@Override
	public boolean equals(Object o) {
		if(o instanceof Client)
			return ((Client)o).id == id;
		return false;
	}
}