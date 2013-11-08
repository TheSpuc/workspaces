package src;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Opg9 {

	public static void main(String[] args) throws IOException{
		//A assignment
//		URL urlCnds = new URL("http://cnds.students.dk/example1.php");
//		InputStreamReader r = new InputStreamReader(urlCnds.openStream());
//		BufferedReader in = new BufferedReader(r);
//		String str;
//		boolean found = false;
//
//		while((str = in.readLine()) != null && !found){
//
//			Pattern p = Pattern.compile("(([seen]+)(\\s)([0-9]+)(\\s)([times]+)){1}");
//			Matcher m = p.matcher(str);
//			if(m.find()){
//				Pattern pFind = Pattern.compile("[0-9]+");
//				Matcher mFind = pFind.matcher(str);
//				if(mFind.find()){
//					System.out.println(mFind.group());
//					found=true;
//				}
//			}
//		}
//		in.close();
		
		//B assignment
		URL url = new URL("http://www.sydbank.dk/investering/kurser/valuta/aktuelle");
		InputStreamReader input = new InputStreamReader(url.openStream());
		BufferedReader inp = new BufferedReader(input);
		String strBank;
		boolean foundBank = false;
		
		while((strBank = inp.readLine()) != null && !foundBank){
			
		}
		inp.close();
	}
}
