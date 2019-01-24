package main;

import processes.Customer;
import processes.Notary;

public class Network {

	private static Notary notaries [];
	private static Customer customers [];

	public static void initialize(int nbNotaries, int nbCustomers){
		notaries = new Notary[nbNotaries];
		for(int i = 0; i< nbNotaries; i++){
			notaries[i]=new Notary(i);
		}
		customers = new Customer[nbCustomers];
		for(int i = 0; i< nbCustomers; i++){
			customers[i] = new Customer(i);
		}
	}

	public static void startAll(){
		for(int i = 0; i < notaries.length; i++){
			new Thread(notaries[i], "Notary " + i).start();
		}
		for(int i = 0; i< customers.length; i++){
			new Thread(customers[i], "Customer " + i).start();
		}
	}

	public static int numberNotaries(){
		return notaries.length;
	}

	public static Notary getNotary(int id){
		return notaries[id];
	}
	
	public static int numberCustomers(){
		return customers.length;
	}

	public static Customer getCustomer(int id){
		return customers[id];
	}

	public static int faultTolerance(){
		return notaries.length / 3;
	}
}
