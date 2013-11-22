/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package beans;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

/**
 *
 * @author mmb
 */
@Named(value="post")
@SessionScoped
public class PostNum implements Serializable {
    
    private Map<String, String> dic;
    private String search;
    
    
    public PostNum(){
        dic = new TreeMap<>();
        search = "";
        try {
            addToDic();
        } catch (IOException ex) {
            Logger.getLogger(PostNum.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void addToDic() throws FileNotFoundException, IOException{
        BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(new File("C:\\Users\\Mark\\Dropbox\\workspace\\netbeans\\2210Ass3\\src\\java\\beans\\Postnummer.txt")), "utf-8"));
        
        String line;
        while((line = in.readLine()) != null){
            String[] splitArray = line.split(" ");
            String key = splitArray[0];
            String value = "";
            for(int i=1; i< splitArray.length; i++){
                value += splitArray[i] + " ";
            }
            dic.put(key, value);
        }
    }
    
    public String getCity(){
        if(search.length() == 4){
            return dic.get(search);
        } else return "";
    }
    
    public List<String> getTempList(){
        List<String> result = new ArrayList<>();
        for(Iterator it = dic.entrySet().iterator(); it.hasNext();){
            Map.Entry entry = (Map.Entry) it.next();
            if(entry.getKey().toString().startsWith(search)){
                String res = entry.getKey() + " " + entry.getValue();
                result.add(res);
            }
        }
        return result;
    }
    
    public String getSearch(){
        return search;
    }
    
    public void setSearch(String search){
        this.search = search;
    }
    
    public Map<String, String> getDic(){
        return dic;        
    }
    
    public String getS(){
        return null;
    }
    
}
