package broadCast;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketTimeoutException;

public class Client {

	public static void main(String[] args) throws Exception{
		DatagramSocket socket = new DatagramSocket();
		socket.setBroadcast(true);
		socket.setSoTimeout(2000);
		int numberOfTimesTried = 0;
		while(numberOfTimesTried<3){
			try{
				byte[] send = new byte[1];
				DatagramPacket sendPackage = new DatagramPacket(send, send.length, InetAddress.getByName("255.255.255.255"), 8888);
				socket.send(sendPackage);
				System.out.println("Package have been sent, now waiting for responds");


				byte[] receive = new byte[1024];
				DatagramPacket receivePackage = new DatagramPacket(receive, receive.length);
				socket.receive(receivePackage);

				String received = new String(receivePackage.getData()).trim();
				System.out.println("Package have been received, ip of nameServer is " + received);
				numberOfTimesTried = 3;
			}catch(SocketTimeoutException ste){
				System.out.println("Problem getting responds, trying on more time");
				numberOfTimesTried++;
			}
		}
		System.out.println("Now closing socket");
		socket.close();
	}
}
