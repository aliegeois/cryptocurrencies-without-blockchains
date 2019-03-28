const Network = require('./Network'),
	Client = require('./Client'),
	{ nbCoins, nbNotaries } = require('./constants');

/**
 * @typedef {{coin: number, from: Client, to: Client}} Transaction
 * @typedef {{sequenceNumber: number, clientId: number}} Accept
 */
class Notary {
	/**
	 * @param {Network} network 
	 */
	constructor(network) {
		this.id = Notary.id++;
		/**
		 * @type {Accept[]}
		 * @private
		 */
		this.accepts = new Array(nbCoins).fill(0).map(() => {
			return { sequenceNumber: 0, clientId: 0 };
		});

		/** @private */
		this._network = network;

		/**
		 * @type {Transaction[]}
		 * @private
		 */
		this.transactionsWaiting = [];
	}

	/**
	 * 
	 * @param {number} coin 
	 * @param {Client} from 
	 * @returns {number} sequenceNumber
	 */
	condition(coin, from) {
		/** @type {Map<number, number>} */
		let occurences = new Map();
		this.accepts.forEach(({ sequenceNumber, clientId }) => {
			if(clientId === from.id) {
				if(occurences.has(sequenceNumber))
					occurences.set(sequenceNumber, occurences.get(sequenceNumber) + 1);
				else
					occurences.set(sequenceNumber, 1);
			}
		});

		let valide = 0, sequenceNumber = -1;
		for(let [sn, count] of occurences.entries()) {
			if(count > valide) {
				valide = count;
				sequenceNumber = sn;
			}
		}

		return valide > this._network.notaries.length * 2 / 3 ? sequenceNumber : -1;
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

	/**
	 * 
	 * @param {function(number, Accept[][]): void} callback 
	 */
	updateColleague(callback) {
		callback(this.id, this.accepts);
	}

	updateMePlease() {
		/** @type {Accept[][]} */
		let myAccept = new Array(nbCoins).fill(0).map(() => {
			return new Array(nbNotaries).fill(0).map(() => {
				return null;
			});
		});

		let received = 0;

		/** @type {Accept[]} */
		let columnsReceived = new Array(nbNotaries).fill(null);
		/** @type {Accept[]} */
		let columnsNotReceived = new Array(nbNotaries).fill({ sequenceNumber: -1, clientId: -1 });
		
		this._network.askAllNotariesForUpdate((notaryId, notaryAccept) => {
			received++;
			if(received >= nbNotaries * 2 / 3) {
				for(let i = 0; i < nbCoins; i++) {
					if(columnsReceived[i] === null) {
						let occurences = [];
						for(let j = 0; j < nbNotaries; j++) {
							
						}
					}
				}

				
			} else {
				for(let i = 0; i < nbCoins; i++)
					myAccept[i][notaryId] = notaryAccept[i][notaryId];
				
				columnsReceived[notaryId] = notaryAccept[notaryId];
				columnsNotReceived[notaryId] = null;
			}
		});
	}
}
Notary.id = 0;

module.exports = Notary;