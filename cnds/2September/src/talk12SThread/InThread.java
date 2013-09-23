package talk12SThread;

import java.io.BufferedReader;
import java.io.IOException;

public class InThread extends Thread {
	
	private volatile boolean running = true;
	private BufferedReader inFromPeer;

	public InThread(BufferedReader inFromPeer){
		this.inFromPeer = inFromPeer;
	}
	
	public void terminate(){
		running = false;
	}
	
	public boolean getRunning(){
		return running;
	}
	
	public void run(){
		while(running){
			try {
				String responds = inFromPeer.readLine();
				System.out.println("Message:" + responds);
				running = CheckConnectionInput.ongoingConnection(responds);
			} catch (Exception e) {
				running = false;
			}
		}
	}
}
