const Network = require('./Network'),
	Client = require('./Client'),
	{ nbClients, nbNotaries } = require('./constants');

class Notary {
	/**
	 * @param {Network} network 
	 */
	constructor(network) {
		this.id = Notary.id++;

		/** @private */
		this._network = network;

		/** @private */
		this._clients = []; // ?

		/** @private */
		this._next = new Array(nbNotaries).fill(0);
	}

	send_message(quantity, from, to) {

	}

	receive_init(j, v, sn) {

	}

	receive_echo(j, v, sn) {

	}

	receive_ready(j, v, sn) {

	}

	/**
	 * 
	 * @param {number} coin 
	 * @param {Client} from 
	 * @param {Client} to 
	 */
	receive_M1(coin, from, to) {
		//console.log(`${this.id}: receive_M1(coin: ${coin}, from: ${from.id}, to: ${to.id})`);
		let sequenceNumber;
		if((sequenceNumber = this.condition(coin, from)) === -1) {
			//console.log('condition en attente');
			this.transactionsWaiting.push({ coin, from, to });
		} else {
			//console.log('condition ok, sn = ' + sequenceNumber);
			this._network.sendToAllNotaries_M2(coin, this, to, sequenceNumber);
		}
	}

	/**
	 * 
	 * @param {number} coin 
	 * @param {Notary} ni 
	 * @param {Client} to 
	 * @param {number} sn 
	 */
	receive_M2(coin, ni, to, sn) {
		console.log(`${this.id}: receive_M2(coin: ${coin}, ni: ${ni.id}, to: ${to.id}, sn: ${sn})`);
		this.accepts[coin][ni.id] = {
			sequenceNumber: sn + 1,
			clientId: to.id
		};

		let transactionsTemporary = this.transactionsWaiting/*.copyWithin(0)*/;
		this.transactionsWaiting = [];
		transactionsTemporary.forEach(({coin, from, to}) => {
			this.receive_M1(coin, from, to);
		});
	}

	/**
	 * 
	 * @param {Client} client 
	 * @param {number} coin 
	 * @param {function(boolean):void} callback
	 * @returns {boolean}
	 */
	isTheir(client, coin, callback) {
		callback(this.accepts[coin][this.id].clientId === client.id);
	}
}
Notary.id = 0;

module.exports = Notary;