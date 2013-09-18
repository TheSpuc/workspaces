package files;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Scanner;

public class Assign1 {

	public static void main(String[] args) throws IOException {
		Scanner sc = new Scanner(new FileInputStream("/Users/MMB/Dropbox/Datamatiker/SK1/week assignments/file/wholeNumbers.txt"));
		
		while(sc.hasNext()){
			int n = sc.nextInt();
			System.out.println(n*2);
		}
		sc.close();
	}

}
