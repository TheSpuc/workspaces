package ass10;
import java.io.*;
import java.net.*;
public class TCPServer {
	
	/**
	 * @param args
	 */
	public static void main(String[] args)throws Exception {
		String clientSentence;
		String capitalizedSentence;
		ServerSocket welcomeSocket = new ServerSocket(6789);
//		welcomeSocket.close(); //For closing the socket right away
		while (true) {
			Socket connectionSocket = welcomeSocket.accept();
//			connectionSocket.setSoTimeout(1000); //For setting a timeout on the connection socket
			System.out.println(connectionSocket.getInetAddress());
			BufferedReader inFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
			DataOutputStream outToClient = new DataOutputStream(connectionSocket.getOutputStream());	
			clientSentence = inFromClient.readLine();
			capitalizedSentence = clientSentence.toUpperCase() + '\n';
			outToClient.writeBytes(capitalizedSentence);
		}
	}

}
