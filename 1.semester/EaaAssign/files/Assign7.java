package files;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class Assign7 {


	public static void main(String[] args) {
		try {
			mergeAllWholeNumbers("/Users/MMB/Dropbox/workspace/EaaAssign/files/Assign78/sort1.txt", "/Users/MMB/Dropbox/workspace/EaaAssign/files/Assign78/sort2.txt", "/Users/MMB/Dropbox/workspace/EaaAssign/files/Assign78/sorted.txt");
			mergeIncommmen("/Users/MMB/Dropbox/workspace/EaaAssign/files/Assign78/sort1.txt", "/Users/MMB/Dropbox/workspace/EaaAssign/files/Assign78/sort2.txt", "/Users/MMB/Dropbox/workspace/EaaAssign/files/Assign78/incommen.txt");
		} catch (IOException e) {
			System.out.println("Moo");
		}
		
	}

	static void mergeAllWholeNumbers(String fileName1, String fileName2, String targetFile) throws IOException{
		Scanner sc = new Scanner(new FileInputStream(fileName1));
		Scanner sc2 = new Scanner(new FileInputStream(fileName2));
		PrintWriter out = new PrintWriter(targetFile);

		int n1 = sc.nextInt();
		int n2 = sc2.nextInt();
		while(sc.hasNext() || sc2.hasNext()){
			if(n1 < n2){
				out.println(n1);
				n1 = sc.nextInt();
			}else{
				out.println(n2);
				n2 = sc2.nextInt();
			}
		}
		sc.close();
		sc2.close();
		out.close();
	}
	
	static void mergeIncommmen(String fileName1, String fileName2, String targetFile) throws IOException{
		Scanner sc = new Scanner(new FileInputStream(fileName1));
		Scanner sc2 = new Scanner(new FileInputStream(fileName2));
		PrintWriter out = new PrintWriter(targetFile);
		
		int n1 = sc.nextInt();
		int n2 = sc2.nextInt();
		while(sc.hasNext() || sc2.hasNext()){
			if(n1 == n2){
				out.println(n1);
				n1 = sc.nextInt();
				n2 = sc2.nextInt();
			}else if(n1 < n2){
				n1 = sc.nextInt();
			}else n2 = sc2.nextInt();
		}
		sc.close();
		sc2.close();
		out.close();
	}
}
