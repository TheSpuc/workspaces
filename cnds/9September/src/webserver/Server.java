package webserver;

import java.net.ServerSocket;
import java.net.Socket;

public class Server{


	public static void main(String[] args) throws Exception{
		ServerSocket serverSocket = new ServerSocket(7531);
		while(true){
			Socket clientSocket = serverSocket.accept();
			(new ServerThread(clientSocket)).start();
		}
	}
}
