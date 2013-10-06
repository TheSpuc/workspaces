/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package guessNumber;

import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpSession;
    
/**
 *
 * @author mmb
 */
@Named(value = "numberBean")
@SessionScoped
public class NumberBean implements Serializable {

    private int num;
    private int userNum;
    private String response;
    
    /**
     * Creates a new instance of NumberBean
     */
    public NumberBean() {
        num = (int) Math.round(Math.random()*10);
        userNum = -1;
        response = "";
        System.out.println("Guess num: " + num);
    }
    
    public int getNum(){
        return num;
    }
    
    public void setName(int num){
        this.num = num;
    }
    
    public int getUserNum(){
        return userNum;
    }
    
    public void setUserNum(int userNum){
        this.userNum = userNum;
    }
    
    public String getResponse(){
        if(userNum == num){
            
            //Getting the tree structure of the specific session,
            //making it possible for getting all information of the tree
            FacesContext context = FacesContext.getCurrentInstance();
            //Thereby getting the specific session that is used right now
            HttpSession session = (HttpSession) context.getExternalContext().getSession(false);
            //invalidate, since the number have been guessed
            session.invalidate();
            
            return "Yay, nice work!";
        }else return "Thats not correct!";
    }
    
}
