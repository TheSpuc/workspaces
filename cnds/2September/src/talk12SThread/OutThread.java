package talk12SThread;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;

public class OutThread extends Thread {

	private volatile boolean running = true;
	private DataOutputStream outToPeer;
	private BufferedReader in;
	
	public OutThread(DataOutputStream outToPeer, BufferedReader in){
		this.outToPeer = outToPeer;
		this.in = in;
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
				String peer = in.readLine();
				outToPeer.writeBytes(peer + '\n');
				running = CheckConnectionInput.ongoingConnection(peer);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
