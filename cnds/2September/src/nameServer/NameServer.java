package nameServer;

import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class NameServer {

	public static void main(String[] args) throws Exception{
		DatagramSocket socket = new DatagramSocket(9876);
		Service service = Service.getInstance();
		while(true){
			byte[] receive = new byte[1024];
			//creating packet and receiving it
			DatagramPacket packet = new DatagramPacket(receive, receive.length);
			socket.receive(packet);
			
			//Thereby getting the information needed for either searching or saving client 
			String sentence = new String(packet.getData()).trim();

			if(sentence.startsWith("#501")){
				System.out.println("Adding the new information to the DNS");
				(new AddClientThread(service, sentence.substring(5), packet.getAddress())).start();
			}else{ 
				System.out.println("Searching for the information in the DNS " + sentence);
				(new FindClientThread(service, packet, socket, sentence)).start();
			}
		}
	}
}

