package broadCast;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class RespondsThread extends Thread {
	
	
	private DatagramSocket server;
	private InetAddress respondsIp;
	private int port;
	private String nameServerIp;
	private volatile boolean running;
	
	public RespondsThread(DatagramSocket server, InetAddress respondsIp, int port, String nameServerIp){
		this.server = server;
		this.respondsIp = respondsIp;
		this.port = port;
		this.nameServerIp = nameServerIp;
		running = true;
	}
	
	public void run(){
		while(running){
			byte[] send = nameServerIp.getBytes();
			DatagramPacket sendPackage = new DatagramPacket(send, send.length, respondsIp, port);
			try {
				server.send(sendPackage);
				System.out.println("Package have been send to " + respondsIp);
			} catch (IOException e) {
				System.err.println("Not possible to send package");
				e.printStackTrace();
			}
			running = false;
		}
	}

}
