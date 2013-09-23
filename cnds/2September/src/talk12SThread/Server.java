package talk12SThread;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

	private static BufferedReader in;

	public static void main(String[] args) throws Exception{
		in = new BufferedReader(new InputStreamReader(System.in));

		//NameServer part:
		System.out.println("Please input your client name");
		String sentence = "#501 " + in.readLine();
		DatagramSocket clientSocket = new DatagramSocket();
		byte[] sendData = new byte[1024]; 
		sendData = sentence.getBytes(); 
		
		InetAddress IPAddress = InetAddress.getByName("localhost"); 
		DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, 9876); 
		clientSocket.send(sendPacket); 
		clientSocket.close();

		
		//Chat server
		ServerSocket welcomeSocket = new ServerSocket(7531);
		boolean runServer = true;
		while(runServer){ //for making it possible for the server to run unlimited.
			Socket connectionSocket = welcomeSocket.accept(); //accepting the new incoming connection
			System.out.println("Client accepted " + connectionSocket.getInetAddress() + connectionSocket.getPort());

			DataOutputStream outToClient = new DataOutputStream(connectionSocket.getOutputStream());
			BufferedReader inFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));

			//Method for checking that we have a genuine connection 
			//and thereby deciding whether or not it should be recieved
			boolean connection = false;
			String responds = "";
			String clientSentence = inFromClient.readLine();
			if(clientSentence.startsWith("#")){
				if(clientSentence.substring(1, 4).equals("418")){
					System.out.println(clientSentence + " wants to connect!");
					responds = in.readLine();
					outToClient.writeBytes(responds + '\n');
					
					//Have to check the reponds, thereby checking if the user actually wants to start the connection.
					connection = CheckConnectionInput.ongoingConnection(responds);
					if(connection){
						System.out.println("Starting connection");
						OutThread out = new OutThread(outToClient, in);
						InThread in = new InThread(inFromClient);
						
						out.start();
						in.start();
						
						out.join();
						in.join();
					}
				}
			}
			
			System.out.println("Closing connection");
			connectionSocket.close();
			
			System.out.println("Keep running server? 0 for no, 1 for yes");
			String runOrNot = in.readLine();
			if(runOrNot.equals("0")){
				runServer = false;
			}
		}
		welcomeSocket.close();
	}
	
}
