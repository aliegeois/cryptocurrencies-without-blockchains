package fr.univnantes.crypto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Notary {
	private int id;

	ArrayList<ArrayList<Pair<Integer, Client>>> accepts = new ArrayList<ArrayList<Pair<Integer, Client>>>();
	//Pair<Integer, Client>[][] accepts = new Pair<Integer, Client>[Const.nbCoin][Const.nbNotaries];

	public Notary(int id) {
		this.id = id;
	}

	private int condition(int coin, Client from) {
		Map<Integer, Integer> occurences = new HashMap<>();
		for(Pair<Integer, Client> pair : accepts.get(coin)) {
			if(pair.second.equals(from)) {
				if(occurences.containsKey(pair.first)) {
					occurences.put(pair.first, occurences.get(pair.first) + 1);
				} else {
					occurences.put(pair.first, 1);
				}
			}
		}

		int valide = 0, sn = -1;
		for(Map.Entry<Integer, Integer> entry : occurences.entrySet())
			if(entry.getValue() > valide) {
				valide = entry.getValue();
				sn = entry.getKey();
			}

		// return valide > n - t;
		return valide > Network.notaries.size() * 2 / 3.0 ? sn : -1;
	}

	public void receive(int coin, Client to, Client from) {
		try {
			int sn = -1;
			while((sn = condition(coin, from)) != -1) {
				wait();
			}
			sendToAllNotaries(coin, to, sn);
		} catch(InterruptedException e) {

		}
	}

	private void sendToAllNotaries(int coin, Client to, int sn) {
		Network.notaries.forEach(notary -> notary.receive(coin, to, sn, this.id));
	}

	private void receive(int coin, Client to, int sn, int ni) {
		accepts.get(coin).set(ni, new Pair<Integer, Client>(sn + 1, to));
		notify();
	}
}