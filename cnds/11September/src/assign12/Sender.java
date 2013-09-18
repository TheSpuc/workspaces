package assign12;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Sender {


	public static void main(String[] args) throws Exception{
		List<Person> persons = new ArrayList<>();
		Person p1 = new Person("Emil", "Aarhus", 1);
		Person p2 = new Person("Mark", "Aarhus", 2);
		Person p3 = new Person("Liv", "Aarhus", 3);
		Person p4 = new Person("Nick", "Aarhus", 4);
		Person p5 = new Person("Julie", "Aarhus", 5);
		Person p6 = new Person("Lone", "Aarhus", 6);
		persons.add(p1);
		persons.add(p2);
		persons.add(p3);
		persons.add(p4);
		persons.add(p5);
		persons.add(p6);
		
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		
		String ip = in.readLine();
		
		Socket clientSocket = new Socket(ip, 7531);
		
		DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
		
		sendPersons(persons, outToServer);
		clientSocket.close();
	}
	
	public static void sendPersons(List<Person> list, DataOutputStream outStream) throws Exception{
		String stringToSend = "";
		for(Person p : list){
			stringToSend += p + ";";
		}
		outStream.writeBytes(stringToSend + '\n');
	}
}
