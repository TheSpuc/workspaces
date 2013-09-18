package files.FilerE12;

import java.io.IOException;

public class TestFilUtil {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		FilUtil filutil = new FilUtil();
		
		String file10 = "/Users/MMB/Dropbox/workspace/EaaAssign/files/Assign5/10numbers.txt";
		String file2500 = "/Users/MMB/Dropbox/workspace/EaaAssign/files/Assign5/2500numbers.txt";
		
		try {
			System.out.println("10 num: " + filutil.max(file10));
			System.out.println("10 num: " + filutil.min(file10));
			System.out.println("10 num: " + filutil.average(file10));
			System.out.println("2500 num " + filutil.max(file2500));
			System.out.println("2500 num " + filutil.min(file2500));
			System.out.println("2500 num " + filutil.average(file2500));
		} catch (IOException e) {
			
		}

	}

}
