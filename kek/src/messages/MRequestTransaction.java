package messages;

import coins.Coin;


public class MRequestTransaction implements Message {
	public final Coin coin;
	public final int fromId;
	public final int toId;
	
	public MRequestTransaction (Coin coin, int fromId, int toId){
		this.coin = coin;
		this.fromId = fromId;
		this.toId = toId;
	}
}
