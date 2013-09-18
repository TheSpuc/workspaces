package files;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class Assign4 {

	public static void main(String[] args) throws IOException {
		Scanner sc = new Scanner(System.in);
		PrintWriter out = new PrintWriter("/Users/MMB/Dropbox/workspace/EaaAssign/files/writeNumbers.txt");
		boolean done = false;
		while(!done){
			int n = sc.nextInt();
			if(n == -1){
				done = true;
			}else{
				out.println(n);
			}
		}
		sc.close();
		out.close();

	}

}
