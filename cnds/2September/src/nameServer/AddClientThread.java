package nameServer;

import java.net.InetAddress;


public class AddClientThread extends Thread {

	private Service service;
	private String sentence;
	private InetAddress ip;
	private volatile boolean running;

	public AddClientThread(Service service, String sentence, InetAddress ip){
		this.service = service;
		this.sentence = sentence;
		this.ip = ip;
		running = true;
	}

	public void run(){
		while(running){
			service.addNameClient(sentence, sentence, ip);
			running = false;
		}
	}
}
