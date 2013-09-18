package broadCast;

import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class Server {
	
	private static String nameServerIp;
	
	public static void main(String[] args) throws Exception{
		nameServerIp = "127.0.0.1";
		DatagramSocket server = new DatagramSocket(8888);
		server.setBroadcast(true);
		while(true){
			byte[] receive = new byte[1024];
			DatagramPacket receivePacket = new DatagramPacket(receive, receive.length);
			server.receive(receivePacket);
			String sentence = new String(receivePacket.getData()).trim();
			
			System.out.println("Received information from " + receivePacket.getAddress() + ", now starting responding");
			(new RespondsThread(server, receivePacket.getAddress(), receivePacket.getPort(), nameServerIp)).start();
		}
	}
}
