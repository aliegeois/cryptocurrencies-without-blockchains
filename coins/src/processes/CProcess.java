package processes;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import messages.Message;




public abstract class CProcess implements Runnable {

	public final int pid;
	private final BlockingQueue<Message> mailBox;
	private volatile boolean running;

	public CProcess (int pid){
		this.pid = pid;
		this.mailBox = new ArrayBlockingQueue<Message>(100);
	}

	public void stop() {
		this.running = false;
	}

	public void send(Message m){
		while(true){
			try {
				mailBox.put(m);
				return;
			} catch (InterruptedException e) {
				// Try again
			}
		}
	}

	public void run() {
		running = true;
		while (running){
			try {
				Message m = mailBox.take();
				receive(m);
			} catch (InterruptedException e) {
				// Try again
			}
		}
	}

	protected abstract void receive(Message m) ;

}
