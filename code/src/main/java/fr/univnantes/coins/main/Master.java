package fr.univnantes.coins.main;

import fr.univnantes.coins.messages.MCreateCoin;
import fr.univnantes.coins.messages.MOrder;
import fr.univnantes.coins.messages.Message;

public class Master {
	private static void createCoin(int coinId, int toId) {
		Message m = new MCreateCoin(coinId, toId);
		for(int i = 0; i < Network.numberNotaries(); i++) {
			Network.getNotary(i).send(m);
		}
	}
	
	private static void order(int senderId, int toId) {
		Message m = new MOrder(senderId, toId);
		Network.getCustomer(senderId).send(m);
	}

	public static void main(String[] args) {
		// Create 7 notaries and 4 customers
		Network.initialize(7, 4);
		Network.startAll();

		// Create 20 coins and spread them uniformly
		System.out.println("*********************Coins creation*********************");
		for(int coinId = 0; coinId < 20; coinId++) {
		 	createCoin(coinId, coinId % 4);
		}

		// Wait until the coins are created
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		// Ask the customers to exchange their coins
		System.out.println("*********************Coins exchange*********************");
		for(int from = 0; from < 4; from++) {
			for(int to = 0; to < 4; to++) {
				order(from, to);
			}
		}
	}
}
