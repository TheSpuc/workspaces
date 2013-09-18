package service;

import java.util.ArrayList;
import java.util.Date;

import dao.Dao;

import model.Call;
import model.Contact;
import model.TextMessage;

/**
 * 
 * @author Mark Medum Bundgaard
 *
 */
public class Service {

	public static ArrayList<Call> getOutgoingCalls(){
		return Dao.getOutgoingCalls();
	}

	public static ArrayList<Call> getRecievedCalls(){
		return Dao.getRecievedCalls();
	}

	public static ArrayList<TextMessage> getInbox(){
		return Dao.getInbox();
	}

	public static ArrayList<TextMessage> getOutbox(){
		return Dao.getOutbox();
	}

	public static ArrayList<Contact> getContacts(){
		return Dao.getContacts();
	}

	public static ArrayList<Contact> getContacts(String searchString){
		ArrayList<Contact> resultList = new ArrayList<>();
		ArrayList<Contact> tempList = Dao.getContacts();
		int index = 0;
		boolean searching = true;
		while(searching && index < tempList.size()){
			String name = tempList.get(index).getFirstName();
			if(name.length() >= searchString.length()){
				String compareName = name.substring(0, searchString.length());
				if(searchString.compareToIgnoreCase(compareName) == 0){
					resultList.add(tempList.get(index));
				}else if(searchString.compareToIgnoreCase(compareName) < 0){
					searching = false;
				}
			}
			index++;
		}
		return resultList;
	}

	public static ArrayList<Call> getAllIncomingCallsFrom(Contact contact){
		ArrayList<Call> resultList = new ArrayList<>();
		ArrayList<Call> tempList = Dao.getRecievedCalls();
		for(Call c : tempList){
			if(c.getContact().equals(contact)){
				resultList.add(c);
			}
		}
		return resultList;
	}
	
	public static Contact getLastCalledContact(){
		ArrayList<Call> outCall = Dao.getOutgoingCalls();
		return outCall.get(0).getContact();
	}

	public static Call getLastCallFrom(Contact contact){
		ArrayList<Call> tempList = Dao.getRecievedCalls();
		boolean found = false;
		int i = 0;
		Call call = null;
		while(i < tempList.size()  && !found){
			if(contact.compareTo(tempList.get(i).getContact()) == 0){
				call = tempList.get(i);
				found = true;
			}else i++;
		}
		return call;
	}

	public static ArrayList<TextMessage> getAllIncomingTextMessagesFrom(Contact contact){
		ArrayList<TextMessage> resultList = new ArrayList<>();
		ArrayList<TextMessage> tempList = Dao.getInbox();
		for(TextMessage t : tempList){
			if(t.getContact().equals(contact)){
				resultList.add(t);
			}
		}
		return resultList;
	}

	public static TextMessage getLastTextMessageFrom(Contact contact){
		ArrayList<TextMessage> tempList = new ArrayList<>();
		boolean found = false;
		int i = 0;
		TextMessage textMessage = null;
		while(i < tempList.size() && !found){
			if(contact.compareTo(tempList.get(i).getContact()) == 0){
				textMessage = tempList.get(i);
				found = true;
			}else i++;
		}
		return textMessage;
	}

	public static Contact getContactFrom(TextMessage textMessage){
		Contact contact = null;
		if(textMessage.getContact() != null){
			contact = textMessage.getContact();
		}
		return contact;
	}

	public static Contact getContactFrom(Call call){
		Contact contact = null;
		if(call.getContact() != null){
			contact = call.getContact();
		}
		return contact;
	}

	public static TextMessage createTextMessage(Contact contact, String message){
		TextMessage textMessage = new TextMessage(contact, message, new Date());
		if(contact != Dao.getMe()){
			Dao.addOutboxMessage(textMessage);
		}else {
			Dao.addOutboxMessage(textMessage);
			Dao.addInboxMessage(textMessage);
		}
		return textMessage;
	}

	public static TextMessage createTextMessage(String recieverNumber, String message){
		TextMessage textMessage = new TextMessage(recieverNumber, message, new Date());
		Contact me = Dao.getMe();
		if(me.getNumber() != recieverNumber){
			Dao.addOutboxMessage(textMessage);
		}else {
			Dao.addOutboxMessage(textMessage);
			Dao.addInboxMessage(textMessage);
		}
		return textMessage;
	}

	public static void deleteIncomingTextMessage(TextMessage textMessage){
		Dao.removeInboxMessage(textMessage);
	}

	public static void deleteOutgoingTextMessage(TextMessage textMessage){
		Dao.removeOutboxMessage(textMessage);
	}

	public static Call createCall(Contact contact){
		Call call = new Call(contact, new Date());
		Contact me = Dao.getMe();
		if(me != contact){
			Dao.addOutgoingCall(call);
		}else{
			Dao.addOutgoingCall(call);
			Dao.addRecievedCall(call);
		}
		return call;
	}

	public static Call createCall(String recieverNumber){
		Call call = new Call(recieverNumber, new Date());
		Contact me = Dao.getMe();
		if(me.getNumber() != recieverNumber){
			Dao.addOutgoingCall(call);
		}else {
			Dao.addOutgoingCall(call);
			Dao.addRecievedCall(call);
		}
		return call;
	}

	public static void deleteRecievedCall(Call call){
		Dao.removeRecievedCall(call);
	}

	public static void deleteOutgoingCall(Call call){
		Dao.removeOutgoingCall(call);
	}

	public static Contact createContact(String firstName, String lastName, String address, String number){
		Contact contact = new Contact(firstName, lastName, address, number);
		Dao.addContact(contact);
		return contact;
	}

	public static void updateContact(Contact contact, String firstName, String lastName, String address, String number){
		if(contact != null){
			contact.setFirstName(firstName);
			contact.setLastName(lastName);
			contact.setAddress(address);
			contact.setNumber(number);
			Dao.insertionSort(contact);
		}
	}

	public static void deleteContact(Contact contact){
		Dao.removeContact(contact);
	}
	
	public static void createAndStoreSomeObjects(){
		createContact("Lizette", "A", "", "12345678");
		createContact("Sigh", "B", "", "98765432");
		createContact("Mark", "C", "", "98567456");
		createContact("Nikolaj", "D", "", "98123568");
	}
}
