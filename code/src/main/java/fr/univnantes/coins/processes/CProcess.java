package fr.univnantes.coins.processes;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import fr.univnantes.coins.messages.Message;

public abstract class CProcess implements Runnable {
	private final BlockingQueue<Message> mailBox;
	private volatile boolean running;

	public final int pid;

	public CProcess(int pid) {
		this.pid = pid;
		this.mailBox = new ArrayBlockingQueue<>(100);
	}

	public void stop() {
		running = false;
	}

	public void send(Message m) {
		while(true) {
			try {
				mailBox.put(m);
				return;
			} catch(InterruptedException e) {
				// Try again
			}
		}
	}

	@Override
	public void run() {
		running = true;
		while(running) {
			try {
				Message m = mailBox.take();
				receive(m);
			} catch(InterruptedException e) {
				// Try again
			}
		}
	}

	protected abstract void receive(Message m);
}
