package talk12S;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

	private static BufferedReader in;

	public static void main(String[] args) throws Exception{
		in = new BufferedReader(new InputStreamReader(System.in));

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
					connection = ongoingConnection(responds);
					if(connection){
						System.out.println("Starting connection, waiting for message");
					}
				}
			}
			
			//connection loop for responds and recieve message, 
			//remembering to check if the message closes the connection
			while(connection){
				String client = inFromClient.readLine();
				System.out.println("Message:" + client);
				connection = ongoingConnection(client);
				if(connection){
					String server = in.readLine();
					outToClient.writeBytes(server + '\n');
					connection = ongoingConnection(server);
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
