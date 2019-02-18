package fr.univnantes.cryptocurrencies;

import static fr.univnantes.cryptocurrencies.Consts.TOTAL_NOTARIES;

public class Client {

    private Notary[] notariesList/* = new Notary[TOTAL_NOTARIES]*/;

    private int clientId;

    public Client(int id, Notary[] notariesList) {
        this.clientId = id;
        this.notariesList = notariesList;
    }

    public void sendCoin(Coin coin, Client receiver) {
        askAllNotaries(coin, receiver);
    }

    public void askAllNotaries(Coin coin, Client receiver) {
        for (int i = 0; i < TOTAL_NOTARIES; i++) {
            notariesList[i].requestFromClient(coin, receiver, this);
        }
    }

    @Override
    public String toString() {
        return "Client{" +
                "clientId=" + clientId +
                '}';
    }
}
