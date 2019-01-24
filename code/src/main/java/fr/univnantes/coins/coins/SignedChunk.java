package fr.univnantes.coins.coins;

/*
 * Should use encryption
 */
public class SignedChunk {
	private final UnsignedChunk data;
	
	public SignedChunk(int privateKey, UnsignedChunk data) {
		this.data = data;
	}
	
	public UnsignedChunk unsign(int publicKey) {
		return data;
	}
}
