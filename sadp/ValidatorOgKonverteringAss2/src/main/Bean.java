/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package main;

import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

/**
 *
 * @author Mark
 */
@Named(value="bean")
@SessionScoped
public class Bean implements Serializable {
    
    private String inputText;
    
    public Bean(){
        inputText = "";
    }
    
    public void setInputText(String inputText){
        this.inputText = inputText;
    }
    
    public String getInputText(){
        return inputText;
    }
    
    public String colorText(){
        
        return null;
    }
}
