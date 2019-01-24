package fr.univnantes.coins.coins;

import java.util.stream.Stream;

import fr.univnantes.coins.main.Network;

public class Coin extends UnsignedChunk implements Comparable<Coin> {
	private final SignedChunk signatures[] = Stream.generate(() -> null).limit(Network.numberNotaries()).toArray(SignedChunk[]::new);
	private transient int count = 0;
	
	public Coin(int coinId, int sequenceNumber, int ownerId) {
		super(coinId, ownerId, sequenceNumber);
	}
	
	public boolean isValid() {
		this.count = 0;

		for(int i = 0; i < signatures.length; i++) {
			if(signatures[i] == null) {
				count ++;
			} else {
				UnsignedChunk usc = signatures[i].unsign(i);
				if(usc.coinId != coinId || usc.ownerId != ownerId || usc.sequenceNumber != sequenceNumber) {
					count++;
				}
			}
		}

		return count < Network.faultTolerance();
	}

	public boolean setSignature(int senderId, SignedChunk sc) {
		UnsignedChunk usc = sc.unsign(senderId);
		if(signatures[senderId] != null || usc.coinId != coinId || usc.ownerId != ownerId || usc.sequenceNumber != sequenceNumber) {
			return false;
		} else {
			signatures[senderId] = sc;
			count++;
			return count == Network.numberNotaries() - Network.faultTolerance();
		}
	}

	/*
	 * Only used to store coins in a TreeSet<Coin>
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	public int compareTo(Coin coin) {
		return coinId - coin.coinId;
	}
}
