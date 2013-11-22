package java;

import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author mmb
 */
@Named(value="bean")
@SessionScoped
public class Bean implements Serializable {
    
    
    private Integer inputNumber;
    
    public Bean(){
        
    }
    
    public void setInputNumber(Integer inputNumber){
        this.inputNumber = inputNumber;
    }
    
    public Integer getInputNumber(){
        return inputNumber;
    }
    
}
