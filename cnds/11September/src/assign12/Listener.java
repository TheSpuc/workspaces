package assign12;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class Listener {

	
	
	public static void main(String[] args) throws Exception{
		List<Person> list = new ArrayList<>();
		ServerSocket socket = new ServerSocket(7531);
		
		Socket welcomeSocket = socket.accept();
		BufferedReader inFromClient = new BufferedReader(new InputStreamReader(welcomeSocket.getInputStream()));
		
		receivePersons(list, inFromClient);
		
		System.out.println(list);
		
		socket.close();
	}
	
	public static void receivePersons(List<Person> list, BufferedReader in) throws Exception{
		String receive = in.readLine();
		StringTokenizer st = new StringTokenizer(receive, ";");
		
		while(st.hasMoreTokens()){
			String[] array = st.nextToken().split(" ");
			list.add(new Person(array[0], array[1], Integer.parseInt(array[2])));
		}
	}
}
