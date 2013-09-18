package files;

import java.io.IOException;
import java.io.PrintWriter;

public class Assign3 {

	
	public static void main(String[] args) throws IOException {
		PrintWriter out = new PrintWriter("/Users/MMB/Dropbox/workspace/EaaAssign/files/onetohundred.txt");
		for(int i=0; i<100; i++){
			if(i%2 != 0){
				out.println(i);
			}
		}
		out.close();
	}

}
