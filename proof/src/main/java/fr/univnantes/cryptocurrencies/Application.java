package fr.univnantes.cryptocurrencies;

import static fr.univnantes.cryptocurrencies.Consts.*;

public class Application {

	public static void main(String[] args) {
		Notary[] notariesList = new Notary[TOTAL_NOTARIES];
		Coin[] coinsList = new Coin[TOTAL_COINS];
		Client[] clientsList = new Client[TOTAL_CLIENTS];
		Acceptance[][] acceptances = new Acceptance[TOTAL_COINS][TOTAL_NOTARIES];

		for (int i = 0; i < TOTAL_CLIENTS; i++) {
			clientsList[i] = new Client(i, notariesList);
		}

		for (int i = 0; i < TOTAL_COINS; i++) {
			coinsList[i] = new Coin(i/*, 0, clientsList[i % TOTAL_CLIENTS]*/);
		}

		for (int i = 0; i < TOTAL_COINS; i++) {
			for (int j = 0; j < TOTAL_NOTARIES; j++) {
				acceptances[i][j] = new Acceptance(0, clientsList[0]);
			}
		}

		for (int i = 0; i < TOTAL_NOTARIES; i++) {
			notariesList[i] = new Notary(i, notariesList, acceptances.clone());
		}




		clientsList[0].sendCoin( coinsList[0], clientsList[1] );

		for (int i = 0; i < TOTAL_NOTARIES; i++) {
			System.out.println("Notary n°" + i + ", sequenceNumber for Coin 0 : " + notariesList[i].getAccepts()[0][i].getSequenceNumber()  + ", & owner : " + notariesList[i].getAccepts()[0][i].getOwner());
		}

		System.out.println("\n");
		clientsList[1].sendCoin( coinsList[0], clientsList[2] );

		for (int i = 0; i < TOTAL_NOTARIES; i++) {
			System.out.println("Notary n°" + i + ", sequenceNumber for Coin 0 : " + notariesList[i].getAccepts()[0][i].getSequenceNumber() + ", & owner : " + notariesList[i].getAccepts()[0][i].getOwner());
		}

	}
}
