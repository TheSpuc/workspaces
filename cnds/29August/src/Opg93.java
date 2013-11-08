package src;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.Iterator;
import java.util.Map;


public class Opg93 {
	
	public static void main(String[] args) throws IOException{
		URL url = new URL("http://cnds.students.dk/example3.php");
		URLConnection conn = url.openConnection();
		conn.setDoOutput(true);
		
		//writing the information to the webpage by using a OutPutStream
		OutputStreamWriter out = new OutputStreamWriter(conn.getOutputStream());
		out.write("year=2013&month=august");
		out.flush();
		
		//getting the respond information on the webpage with a inputStream
		InputStreamReader in = new InputStreamReader(conn.getInputStream());
		BufferedReader r = new BufferedReader(in);
		
		String str;
		while((str = r.readLine()) != null){
			System.out.println(str);
		}
		System.out.println();
		
		Map map = conn.getHeaderFields();
		Iterator it = map.entrySet().iterator();
		
		while(it.hasNext()){
			System.out.println(it.next());
		}
		
		r.close();
		out.close();
	}
}
