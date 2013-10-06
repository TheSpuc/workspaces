
import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
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
    @Inject
    private Service service;
    private String userName;
    private String userPassword;
    private int userNumber;
    
    public UserBean(){
        userName = "";
        userPassword = "";
        userNumber = -1;
    }
    
    public String ableToLogin(){
        if(service.containsUser(userName, userPassword)){
            return "welcome";
        }else return "error";
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
}
