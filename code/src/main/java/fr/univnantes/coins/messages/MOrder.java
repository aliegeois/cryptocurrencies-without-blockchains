package fr.univnantes.coins.messages;

public class MOrder implements Message {
	public final int senderId;
	public final int toId;
	
	public MOrder(int senderId, int toId) {
		this.senderId = senderId;
		this.toId = toId;
	}
}
