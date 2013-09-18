package model;

import java.util.Date;

/**
 * 
 * @author Mark Medum Bundgaard
 *
 */
public class Call {

	private Contact contact;
	private boolean answered;
	private Date date;
	private String number;

	public Call(Contact contact, Date date) {
		this.contact = contact;
		this.date = date;
		number = contact.getNumber();
		answered = false;
	}

	public Call(String number, Date date){
		this.number = number;
		this.date = date;
		answered = false;
	}

	public Contact getContact(){
		return contact;
	}
	
	public void setAnswered(boolean answered){
		this.answered = answered;
	}

	public void setContact(Contact contact) {
		if (this.contact != contact){
			this.contact = contact;
		}
	}
	
	public String toString(){
		return contact + ", " + number;
	}
}