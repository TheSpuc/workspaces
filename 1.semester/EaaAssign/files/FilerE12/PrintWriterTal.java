package files.FilerE12;

import java.io.*;
import java.util.*;


public class PrintWriterTal {

	public static void main(String[] args) throws Exception {
		String fileName="/Users/MMB/Dropbox/workspace/EaaAssign/files/Assign5/2500numbers.txt";
		PrintWriter printWriter = new PrintWriter(fileName);
		
		// indlaes antal tal i filen
		Scanner scan = new Scanner(System.in);
		System.out.print("Antal tal der skal skrives i filen: ");
		int antal=scan.nextInt();

		// skab tilfaeldige tal generator Random		
		Random rnd = new Random();
		
		// generer og skriv de tilfaeldige tal
		for (int n=1; n<=antal; n++) {
			int tal=rnd.nextInt(10000);
			printWriter.println(tal);
		}
			
		// flush og skriv filen
		printWriter.flush();
		printWriter.close();
		scan.close();
	}
}
