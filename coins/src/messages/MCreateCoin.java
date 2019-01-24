package messages;


public class MCreateCoin implements Message {
	
	public final int coinId;
	public final int toId;
	
	public MCreateCoin (int coinId, int toId){
		this.coinId = coinId;
		this.toId = toId;
	}	
}
