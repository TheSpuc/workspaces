package files;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Assign9 {

	public static void main(String[] args) {
		try {
			System.out.println(linearSearchLastNameArray("/Users/MMB/Dropbox/workspace/EaaAssign/files/Assign9/persons.txt", "Thomsen"));
			System.out.println(linearSearchLastNameEndsWith("/Users/MMB/Dropbox/workspace/EaaAssign/files/Assign9/persons.txt", "Andersen"));
			System.out.println(linearSearchLastArrayList("/Users/MMB/Dropbox/workspace/EaaAssign/files/Assign9/persons.txt", "Jensen"));
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	static String linearSearchLastNameArray(String fileName, String target) throws IOException{
		Scanner sc = new Scanner(new FileInputStream(fileName));
		String result = "Name doesn't exist";
		boolean found = false;
		while(sc.hasNext() && !found){
			String[] name = sc.nextLine().trim().split(" ");
			if(name[name.length-1].equals(target)){
				found = true;
				String temp = "";
				for(String s : name){
					temp += s + " ";
				}
				result = temp;
			}
		}
		sc.close();
		return result;
	}

	static String linearSearchLastNameEndsWith(String fileName, String target) throws IOException{
		Scanner sc = new Scanner(new FileInputStream(fileName));
		String result = "Name doesn't exist";
		boolean found = false;
		while(sc.hasNext() && !found){
			String name = sc.nextLine().trim();
			if(name.endsWith(target)){
				found = true;
				result = name; 
			}
		}
		sc.close();
		return result;
	}

	static ArrayList<String> linearSearchLastArrayList(String fileName, String target) throws IOException{
		Scanner sc = new Scanner(new FileInputStream(fileName));
		ArrayList<String> resultList = new ArrayList<>();
		while(sc.hasNext()){
			String name = sc.nextLine().trim();
			if(name.endsWith(target)){
				resultList.add(name);
			}
		}
		sc.close();
		return resultList;
	}

}
