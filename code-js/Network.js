const Notary = require('./Notary'),
	Client = require('./Client');

module.exports = class Network {
	constructor() {
		/**
		 * @type {Client[]}
		 */
		this.clients = [];
		/**
		 * @type {Notary[]}
		 */
		this.notaries = [];
	}

	/**
	 * Envoie une pièce d'un client à un autre
	 * @param {number} coin La pièce à envoyer
	 * @param {Client} from Le client qui envoie la pièce
	 * @param {Client} to Le client qui reçoit la pièce
	 */
	sendToAllNotaries_M1(coin, from, to) {
		this.notaries.forEach(notary => notary.receive_M1(coin, from, to));
	}

	/**
	 * Envoie une pièce 
	 * @param {number} coin La pièce à envoyer
	 * @param {Notary} ni Le notaire qui envoie la pièce
	 * @param {Client} to Le client qui reçoit la pièce
	 * @param {number} sn Le numéro de séquence de la pièce
	 */
	sendToAllNotaries_M2(coin, ni, to, sn) {
		this.notaries.forEach(notary => notary.receive_M2(coin, ni, to, sn));
	}

	/**
	 * 
	 * @param {Client} client 
	 * @param {number} coin 
	 * @param {function(boolean):void} callback 
	 */
	askAllNotariesIfMine(client, coin, callback) {
		this.notaries.forEach(notary => notary.isTheir(client, coin, callback));
	}
};