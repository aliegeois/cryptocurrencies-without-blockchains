package fr.univnantes.coins.messages;

import fr.univnantes.coins.coins.SignedChunk;

public class MAcceptTransaction implements Message {
	public final int senderId;
	public final SignedChunk sc;
	
	public MAcceptTransaction(int senderId, SignedChunk sc) {
		this.senderId = senderId;
		this.sc = sc;
	}	
}
