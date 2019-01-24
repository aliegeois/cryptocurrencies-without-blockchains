package coins;

import java.util.Set;
import java.util.TreeSet;

import main.DynamicArray;




public class Wallet {

	protected final int ownerId;
	
	private DynamicArray<CoinForge> forges;
	private Set<Coin> coins;
	
	public Wallet (int ownerId){
		this.ownerId = ownerId;
		
		forges = new DynamicArray<CoinForge>();
		coins = new TreeSet<Coin>();
	}

	public Coin getCoin(){
		for(Coin coin : coins){
			coins.remove(coin);
			return coin;
		}
		return null;
	}
	
	public int stack(){
		return coins.size();
	}

	public void insertSignature(int sender, SignedChunk sc){
		UnsignedChunk usc = sc.unsign(sender);
		CoinForge forge = forges.getOrCreate(usc.coinId, new CoinForge(usc.coinId, ownerId));
		Coin coin = forge.insert(sender, usc.sequenceNumber, sc);
		if (coin!=null){
			coins.add(coin);
			System.out.println(Thread.currentThread().getName() + " now owns coin " + coin.coinId);
		}
	}

}
