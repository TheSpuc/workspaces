/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package beans;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;
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
    
    private Map<Integer, String> dic;
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
        BufferedReader in = new BufferedReader(new FileReader(new File("/Users/mmb/Dropbox/workspace/netbeans/2210Ass2/src/java/beans/Postnummer.txt")));
        
        String line;
        while((line = in.readLine()) != null){
            String[] splitArray = line.split(" ");
            int key = Integer.parseInt(splitArray[0]);
            String value = "";
            for(int i=1; i< splitArray.length; i++){
                value += splitArray[i] + " ";
            }
            dic.put(key, value);
        }
    }
    
    public String getCity(){
        if(search.length() == 4){
            return dic.get(Integer.parseInt(search));
        } else return "";
    }
    
    public String getSearch(){
        return search;
    }
    
    public void setSearch(String search){
        this.search = search;
    }
    
    public Map<Integer, String> getDic(){
            return dic;        
    }
    
    public String getS(){
        return null;
    }
    
}
