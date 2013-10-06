
import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Mark
 */
@Named("userBean")
@SessionScoped
public class UserBean implements Serializable {
    
    private String userName;
    private String userPassword;
    private int userNumber;
    private String greeting;
    
    public UserBean(){
        userName = "";
        userPassword = "";
        userNumber = -1;
        greeting = "";
    }
    
    public void setUserName(String userName){
        this.userName = userName;
    }
    
    public String getUserName(){
        return userName;
    }
    
    public void setUserPassword(String userPassword){
        this.userPassword = userPassword;
    }
    
    public String getUserPassword(){
        return userPassword;
    }
    
    public void setUserNumber(int userNumber){
        this.userNumber = userNumber;
    }
    
    public int getUserNumber(){
        return userNumber;
    }
    
    public String getGreeting(){
        return userName + " " + userNumber + " " + userPassword; 
    }
}
