package files.FilerE12;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Scanner;

public class FilUtil {

	
	public int max(String fileName) throws IOException{
		int result = Integer.MIN_VALUE;
		Scanner sc = new Scanner(new FileInputStream(fileName));
		while(sc.hasNext()){
			int n = sc.nextInt();
			if(n > result){
				result = n;
			}
		}
		sc.close();
		return result;
	}
	
	public int min(String fileName) throws IOException{
		int result = Integer.MAX_VALUE;
		Scanner sc = new Scanner(new FileInputStream(fileName));
		while(sc.hasNext()){
			int n = sc.nextInt();
			if(n < result){
				result = n;
			}
		}
		sc.close();
		return result;
	}
	
	public double average(String fileName) throws IOException{
		int average = 0;
		int nrOfInput = 0;
		Scanner sc = new Scanner(new FileInputStream(fileName));
		while(sc.hasNext()){
			average += sc.nextInt();
			nrOfInput++;
		}
		sc.close();
		return average/nrOfInput;
	}
}
