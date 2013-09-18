package files;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Scanner;

public class Assign6 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			System.out.println(linearFileSearch("/Users/MMB/Dropbox/workspace/EaaAssign/files/Assign5/10numbers.txt", 11146));
		} catch (IOException e) {
			
		}

	}
	
	static boolean linearFileSearch(String fileName, int target) throws IOException{
		boolean found = false;
		Scanner sc = new Scanner(new FileInputStream(fileName));
		while(sc.hasNext() && !found){
				found = sc.nextInt() == target;
		}
		sc.close();
		return found;
	}

}
