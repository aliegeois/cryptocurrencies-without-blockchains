package fr.univnantes.coins.coins;

import java.util.Set;
import java.util.TreeSet;

import fr.univnantes.coins.main.DynamicArray;

public class Wallet {
	private DynamicArray<CoinForge> forges = new DynamicArray<>();
	private Set<Coin> coins = new TreeSet<>();

	protected final int ownerId;
	
	public Wallet(int ownerId) {
		this.ownerId = ownerId;
	}

	public Coin getCoin() {
		for(Coin coin : coins) {
			coins.remove(coin);
			return coin;
		}
		return null;
	}
	
	public int stack() {
		return coins.size();
	}

	public void insertSignature(int sender, SignedChunk sc) {
		UnsignedChunk usc = sc.unsign(sender);
		CoinForge forge = forges.getOrCreate(usc.coinId, new CoinForge(usc.coinId, ownerId));
		Coin coin = forge.insert(sender, usc.sequenceNumber, sc);
		if(coin != null) {
			coins.add(coin);
			System.out.println(Thread.currentThread().getName() + " now owns coin " + coin.coinId);
		}
	}
}
