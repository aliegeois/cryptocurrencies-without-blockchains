package fr.univnantes.coins.coins;

import fr.univnantes.coins.main.DynamicArray;

/**
 *
 * Stores all signatures that can be used to forge one coin in the future, for a given coin identifier
 *
 * @author Matthieu Perrin
 *
 */
public class CoinForge {
	private final int coinId;
	private final int ownerId;
	private final DynamicArray<Coin> ores = new DynamicArray<Coin>();
	private int sequenceNumber = 0;
	
	public CoinForge(int coinId, int ownerId) {
		this.coinId = coinId;
		this.ownerId = ownerId;
	}
	
	public Coin insert(int sender, int sn, SignedChunk sc) {
		if(sn >= sequenceNumber) {
			Coin coin = ores.getOrCreate(sn, new Coin(coinId, sn, ownerId));
			ores.set(sn, coin);
			if(coin.setSignature(sender, sc)) {
				ores.freeUnder(sn);
				return coin;
			}
		}
		return null;
	}
}
