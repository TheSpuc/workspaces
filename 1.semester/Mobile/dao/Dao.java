package dao;

import java.util.ArrayList;

import model.Call;
import model.Contact;
import model.TextMessage;

/**
 * 
 * @author Mark Medum Bundgaard
 *
 */
public class Dao {


	private static ArrayList<TextMessage> inbox = new ArrayList<>();
	private static ArrayList<TextMessage> outbox = new ArrayList<>();
	private static ArrayList<Call> recievedCalls = new ArrayList<>();
	private static ArrayList<Call> outgoingCalls = new ArrayList<>();
	private static ArrayList<Contact> contacts = new ArrayList<>();
	private static final Contact me = new Contact("Anders", "Jensen", "Soenderhoej 30", "24898909");

	public static Contact getMe(){
		return me;
	}
	
	public static ArrayList<TextMessage> getInbox(){
		return new ArrayList<>(inbox);
	}

	public static ArrayList<TextMessage> getOutbox(){
		return new ArrayList<>(outbox);
	}

	public static ArrayList<Call> getRecievedCalls(){
		return new ArrayList<>(recievedCalls);
	}

	public static ArrayList<Call> getOutgoingCalls(){
		return new ArrayList<>(outgoingCalls);
	}

	public static ArrayList<Contact> getContacts(){
		return new ArrayList<>(contacts);
	}

	public static void addInboxMessage(TextMessage textMessage){
			inbox.add(0, textMessage);
	}

	public static void removeInboxMessage(TextMessage textMessage){
		if(inbox.contains(textMessage)){
			inbox.remove(textMessage);
		}
	}

	public static void addOutboxMessage(TextMessage textMessage){
		outbox.add(0 ,textMessage);
	}

	public static void removeOutboxMessage(TextMessage textMessage){
		if(outbox.contains(textMessage)){
			outbox.remove(textMessage);
		}
	}

	public static void addRecievedCall(Call call){
		recievedCalls.add(0, call);
	}

	public static void removeRecievedCall(Call call){
		if(recievedCalls.contains(call)){
			recievedCalls.remove(call);
		}
	}

	public static void addOutgoingCall(Call call){
		outgoingCalls.add(0, call);
	}

	public static void removeOutgoingCall(Call call){
		if(outgoingCalls.contains(call)){
			outgoingCalls.remove(call);
		}
	}

	public static void addContact(Contact contact){
		int i = contacts.size();
		while(i > 0 && contacts.get(i-1).compareTo(contact) > 0){
			i--;
		}
		contacts.add(i, contact);
	}
	
	public static void insertionSort(Contact contact){
		removeContact(contact);
		addContact(contact);
	}

	public static void removeContact(Contact contact){
		if(contacts.contains(contact)){
			contacts.remove(contact);
		}
	}
}
