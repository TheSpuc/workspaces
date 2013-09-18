package files;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Assign2 {

	public static void main(String[] args) throws IOException {
		Scanner sc = new Scanner(new FileInputStream("/Users/MMB/Dropbox/workspace/EaaAssign/files/wholeNumbers.txt"));
		ArrayList<Integer> resultList = new ArrayList<>();
		
		while(sc.hasNext()){
			resultList.add(sc.nextInt());
		}
		sc.close();
		for(int i=resultList.size()-1; i>=0; i--){
			System.out.println(resultList.get(i));
		}
	}

}
