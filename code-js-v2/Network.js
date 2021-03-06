/** @module Network */

const Notary = require('./Notary'),
	Client = require('./Client'),
	{ inducedDelay } = require('./constants');

class Network {
	constructor() {
		/** @type {Client[]} */
		this.clients = [];
		/** @type {Notary[]} */
		this.notaries = [];
		/** @type {number} */
		this.inducedDelay = inducedDelay;
	}

	/**
	 * Envoie une pièce d'un client à un autre
	 * @param {number} coin La pièce à envoyer
	 * @param {Client} from Le client qui envoie la pièce
	 * @param {Client} to Le client qui reçoit la pièce
	 */
	sendToAllNotaries_M1(coin, from, to) {
		this.notaries.forEach(notary =>
			setTimeout(
				() => notary.receive_M1(coin, from, to),
				this.inducedDelay + (Math.random() - .5) * this.inducedDelay / 10
			)
		);
	}

	/**
	 * Envoie une pièce 
	 * @param {number} coin La pièce à envoyer
	 * @param {Notary} ni Le notaire qui envoie la pièce
	 * @param {Client} to Le client qui reçoit la pièce
	 * @param {number} sn Le numéro de séquence de la pièce
	 */
	sendToAllNotaries_M2(coin, ni, to, sn) {
		this.notaries.forEach(notary =>
			setTimeout(
				() => notary.receive_M2(coin, ni, to, sn),
				this.inducedDelay + (Math.random() - .5) * this.inducedDelay / 10
			)
		);
	}

	/**
	 * 
	 * @param {Client} client 
	 * @param {number} coin 
	 * @param {function(boolean): void} callback 
	 */
	askAllNotariesIfMine(client, coin, callback) {
		this.notaries.forEach(notary =>
			setTimeout(
				() => notary.isTheir(client, coin, callback),
				this.inducedDelay + (Math.random() - .5) * this.inducedDelay / 10
			)
		);
	}

	/**
	 * 
	 * @param {function(number, {sequenceNumber: number, clientId: number}[][]): void} callback 
	 */
	askAllNotariesForUpdate(callback) {
		this.notaries.forEach(notary =>
			notary.updateColleague(callback)
		);
	}
}

module.exports = Network;