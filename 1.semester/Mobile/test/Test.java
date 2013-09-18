package test;

import service.Service;

public class Test {

	public static void main(String[] args){
		Service.createContact("Mark", "Medum", "ssdg", "sdhs");
		Service.createContact("Maria", "Medum", "", "");
		Service.createContact("Sig", "", "", "");
		System.out.println(Service.getContacts(""));
	}
}
