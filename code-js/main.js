const Network = require('./Network'),
	Client = require('./Client'),
	Notary = require('./Notary'),
	{ nbClients, nbNotaries } = require('./constants');

function showCoin(id) {
	let clients = network.notaries[0].accepts[id];
	return `Coin[${id}]: ${clients.map(({sequenceNumber, clientId}) => { return `[${sequenceNumber}, ${clientId}]`; })}`;
}

let network = new Network();
for(let i = 0; i < nbClients; i++)
	network.clients.push(new Client(network));
for(let i = 0; i < nbNotaries; i++)
	network.notaries.push(new Notary(network));

//console.log(network.notaries[0]);

network.clients[0].send(0, network.clients[1]);

console.log(showCoin(0));
console.log(showCoin(1));
console.log(network.clients[0].isMine(0));
console.log(network.clients[1].isMine(0));