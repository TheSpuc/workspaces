package nameServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class Client {

	public static void main(String[] args) throws IOException{
		BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in)); 
		DatagramSocket clientSocket = new DatagramSocket();
		//information to either be searched for or added to DNS
		String sentence = inFromUser.readLine(); 

		byte[] sendData = new byte[1024]; 
		byte[] receiveData = new byte[1024]; 
		sendData = sentence.getBytes(); 

		//Get ip of this machine
		InetAddress IPAddress = InetAddress.getByName("localhost"); 
		DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, 9876); 
		clientSocket.send(sendPacket); 

		if(!sentence.startsWith("#501")){
			DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length); 
			clientSocket.receive(receivePacket); 

			String received = new String(receivePacket.getData()); 
			System.out.println("FROM SERVER:" + received); 
		}
		clientSocket.close(); 
	}
}
