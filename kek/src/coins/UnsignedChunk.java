package coins;

/*
 * Should actually be signed...
 */
public class UnsignedChunk {

	final public int coinId;
	final public int ownerId;
	final public int sequenceNumber;

	public UnsignedChunk(int coinId, int ownerId, int sequenceNumber){
		this.coinId=coinId;
		this.ownerId = ownerId;
		this.sequenceNumber = sequenceNumber;
	}
	
	public SignedChunk sign(int privateKey){
		return new SignedChunk(privateKey, this);
	}
}
