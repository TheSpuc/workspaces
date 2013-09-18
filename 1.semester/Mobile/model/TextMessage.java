package model;

import java.util.Date;

/**
 * 
 * @author Mark Medum Bundgaard
 *
 */
public class TextMessage {

	private String receiverNumber;
	private String message;
	private Date time;
	private Contact contact;
	private boolean read;

	public TextMessage(Contact contact, String message, Date time){
		this.contact = contact;
		this.message = message;
		this.time = time;
		read = false;
	}

	public TextMessage(String receiverNumber, String message, Date time){
		this.receiverNumber = receiverNumber;
		this.message = message;
		this.time = time;
		read = false;
	}
	
	public void setRead(boolean read){
		this.read = read;
	}
	
	public Contact getContact(){
		return contact;
	}

	public void setContact(Contact contact){
		if(this.contact != contact){
			this.contact = contact;
		}
	}
}