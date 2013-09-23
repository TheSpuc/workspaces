package nameServer;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;


public class FindClientThread extends Thread {

	private Service service;
	private DatagramPacket packet;
	private DatagramSocket serverSocket;
	private String sentence;
	private volatile boolean running;

	public FindClientThread(Service service, DatagramPacket packet, DatagramSocket serverSocket, String sentence){
		this.service = service;
		this.packet = packet;
		this.serverSocket = serverSocket;
		this.sentence = sentence;
		running = true;
	}

	public void run(){
		while(running){
			String sendInformation = "";
			if(sentence.startsWith("#502")){
				sendInformation = service.getClients();
			}else{
				NameClient toSend = service.getNameClient(sentence);
				if(toSend != null){
					sendInformation = toSend.getIp() + "";
				}
			}
			InetAddress ip = packet.getAddress();
			int port = packet.getPort();
			byte[] sendData = sendInformation.getBytes();

			DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, ip, port);
			try {
				serverSocket.send(sendPacket);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			running = false;
		}

	}
}
