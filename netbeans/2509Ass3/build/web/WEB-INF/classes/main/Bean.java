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
    
    private String input;
    
    public Bean(){
        input = "";
    }
    
    public String getInput(){
        return input;
    }
    
    public void setInput(String input){
        this.input = input;
    }
    
    public String hack(){
        return null;
    }
}
