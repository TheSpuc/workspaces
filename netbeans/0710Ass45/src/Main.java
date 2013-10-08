
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author mmb
 */
public class Main {
    
    public static void main(String[] args) throws Exception{
        userName();
    }
    
    public static void userName() throws Exception{
        Pattern pattern = Pattern.compile("\\([a-zA-Z\\d]+\\)");
        
        BufferedReader in = new BufferedReader(new FileReader("/Users/mmb/Dropbox/datamatiker/SADP/13-10-07 PrimeFaces, DataTable & RegEx/Regex/file.txt"));
        String inLine = null;
        while((inLine = in.readLine()) != null){
            Matcher m = pattern.matcher(inLine);
            while(m.find()){
                System.out.println(m.group());
            }
        }
    }
    
    public static void allInfo() throws Exception{
        
        
        BufferedReader in = new BufferedReader(new FileReader("/Users/mmb/Dropbox/datamatiker/SADP/13-10-07 PrimeFaces, DataTable & RegEx/Regex/file.txt"));
        String inLine = null;
        while((inLine = in.readLine()) != null){
            String[] splitArray = inLine.split(";");
            for(String s : splitArray){
                s = s.trim();
                Pattern pattern = Pattern.compile("<.+>");
                Matcher m = pattern.matcher(s.trim());
                while(m.find()){
                    String email = m.group();
                    System.out.println(s.subSequence(0, s.indexOf("(")));
                    System.out.println(email);
                }
                
            }
            
            
        }
    }
}
