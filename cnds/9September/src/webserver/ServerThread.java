package webserver;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;

public class ServerThread extends Thread {

	private Socket clientSocket;
	private final String directory = "/Users/mmb/Desktop/web";

	public ServerThread(Socket clientSocket){
		this.clientSocket = clientSocket;
	}

	public void run(){
		try {
			BufferedReader inFromClient = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			DataOutputStream outToClient = new DataOutputStream(clientSocket.getOutputStream());

			String readLine = inFromClient.readLine();
			String[] array = readLine.split(" ");
			String fileToFind = array[1];
			if(fileToFind.equals("/")){
				fileToFind += "index.html";
			}
			System.out.println(fileToFind);
			try{
				outToClient.writeBytes("HTTP1/1 200 OK" + "\n" 
										+ contentType(fileToFind)
										+ "Connection: Close" + "\n\n");
				outToClient.write(read(fileToFind));
			}catch(FileNotFoundException fe){
				outToClient.writeBytes("404 Not Found");
			}



		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	private String contentType(String docName){
		if(docName.endsWith(".html") || docName.endsWith(".xhtml")){
			return ("Content-Type: text/html\n");
		}else if(docName.endsWith(".gif")){
			return ("Content-Type: image/gif\n");
		}else if(docName.endsWith(".png")){
			return ("Content-Type: image/png\n");
		}else if(docName.endsWith(".jpg")){
			return ("Content-Type: image/jpg\n");
		}else if(docName.endsWith(".js")){
			return ("Content-Type: text/javascript\n");
		}else if(docName.endsWith(".css")){
			return ("Content-Type: text/css\n");
		}else{
			return ("Content-Type: text/plain\n");
		}
	}

	private byte[] read(String aInputFileName) throws FileNotFoundException{
		// returns the content of a file in a binary array
		System.out.println("Reading in binary file named : " + directory+aInputFileName);
		File file = new File(directory+aInputFileName);
		System.out.println("File size: " + file.length());
		byte[] result = new byte[(int)file.length()];
		try {
			InputStream input = null;
			try {
				int totalBytesRead = 0;
				input = new BufferedInputStream(new FileInputStream(file));
				while(totalBytesRead < result.length){
					int bytesRemaining = result.length - totalBytesRead;
					int bytesRead = input.read(result, totalBytesRead, bytesRemaining); 
					//input.read() returns -1, 0, or more : 
					if (bytesRead > 0) {
						totalBytesRead = totalBytesRead + bytesRead;
					}
				}
				System.out.println("Num bytes read: " + totalBytesRead);
			}
			finally {
				System.out.println("Closing input stream.");
				input.close();
			}
		}
		catch (FileNotFoundException ex) {
			throw ex;
		}
		catch (IOException ex) {
			ex.printStackTrace();
		}
		return result;
	}
}
