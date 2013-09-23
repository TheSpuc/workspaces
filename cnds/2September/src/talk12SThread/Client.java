package talk12SThread;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.Socket;

import nameServer.NameServerClient;

public class Client {

	private static final String myName = "mmb";
	private static String recieverIp;
	private static BufferedReader in;

	public static void main(String[] args) throws Exception{
		in = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("Finding possible people to chat with");
		String[] nameServerArgs = {"#502"};
		NameServerClient.main(nameServerArgs);
		
		System.out.println("Please input the person you want to connect to");
		String pickPerson = in.readLine();
		String[] nameServerPerson = {pickPerson};
		NameServerClient.main(nameServerPerson);
		
		System.out.println("Please input the newly located ip for connection to client:");
		recieverIp = in.readLine();
		Socket clientSocket = new Socket(recieverIp,7531);

		DataOutputStream outToPeer = new DataOutputStream(clientSocket.getOutputStream()); //For sending messages to the other peer
		BufferedReader inFromPeer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream())); //For getting information from the sending

		String firstConnection = "#418 " + myName + '\n'; 
		outToPeer.writeBytes(firstConnection); //Starting connection with remote peer

		String firstResponds = inFromPeer.readLine(); //reading first reponds from peer
		boolean connection = CheckConnectionInput.ongoingConnection(firstResponds); //checking if peer want a connection
		System.out.println(connection);
		if(connection){
			System.out.println("Connection is now possible, please write your message!");
			OutThread out = new OutThread(outToPeer, in);
			InThread in = new InThread(inFromPeer);
			
			in.start();
			out.start();
			
			in.join();
			out.join();
			
		}
		System.out.println("Closing connection");
		clientSocket.close();
	}


}
