package talk12S;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.Socket;

public class Client {

	private static final String myName = "mmb";
	private static String recieverIp;
	private static BufferedReader in;

	public static void main(String[] args) throws Exception{
		in = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("Please input the ip you want to connect to");
		recieverIp = in.readLine();

		Socket clientSocket = new Socket(recieverIp,7531);

		DataOutputStream outToPeer = new DataOutputStream(clientSocket.getOutputStream()); //For sending messages to the other peer
		BufferedReader inFromPeer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream())); //For getting information from the sending

		String firstConnection = "#418 " + myName + '\n'; 
		outToPeer.writeBytes(firstConnection); //Starting connection with remote peer

		String firstResponds = inFromPeer.readLine(); //reading first reponds from peer
		boolean connection = ongoingConnection(firstResponds); //checking if peer want a connection
		if(connection){
			System.out.println("Connection is now possible, please write your message!");
		}

		while(connection){ //while loop runs until the remote peer sends end connection
			String client = in.readLine();
			connection = ongoingConnection(client);
			
			outToPeer.writeBytes(client + '\n');
			if(connection){
				String responds = inFromPeer.readLine();
				System.out.println("Message:" + responds);
				connection = ongoingConnection(responds);
			}
			
		}
		System.out.println("Closing connection");
		clientSocket.close();
	}

	/**
	 * Method for checking whether or not 
	 * the input is giving a false or true connection
	 * @param input
	 * @return
	 */
	private static boolean ongoingConnection(String input){
		boolean result = false;
		if(input.startsWith(" ")){
			result = true;
		}else if(input.startsWith("#")){
			String test = input.substring(1, 4);
			if(test.equals("200")){
				result = true;
			}else if(test.equals("403") || test.equals("410")){
				result = false;
			}
		}
		return result;
	}
}
