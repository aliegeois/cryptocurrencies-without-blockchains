const Network = require('./Network');
const { nbNotaries} = require('./constants');

class Client {
	/**
	 * @param {Network} network 
	 */
	constructor(network) {
		this.id = Client.id++;

		/** @private */
		this._network = network;
	}

	/**
	 * Demande si la pièce nous appartient
	 * @param {number} coin 
	 * @returns {boolean}
	 */
	isMine(coin) {
		let validations = 0;
		this._network.askAllNotariesIfMine(this, coin, (mine) => {
			validations += mine ? 1 : 0;
			if(validations >= nbNotaries * 2 / 3)
				console.log(`Client[${this.id}]: coin ${coin} is mine`);
		});
	}

	/**
	 * Envoie une pièce vers un autre client
	 * @param {number} coin 
	 * @param {Client} to 
	 */
	send(coin, to) {
		this._network.sendToAllNotaries_M1(coin, this, to);
	}
}
Client.id = 0;

module.exports = Client;