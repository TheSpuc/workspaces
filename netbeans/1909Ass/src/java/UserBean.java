
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
    private User user;
    private String detailButtonName;
    private boolean showInfo;
    private boolean showAdminInfo;
    
    public UserBean(){
        user = new User(null, null, 0, "default");
        detailButtonName = "Details";
        showInfo = false;
        showAdminInfo = false;
    }
    
    public String ableToLogin(){
        if(service.containsUser(user.getName(), user.getPassword())){
            return "welcome";
        }else{
            user = new User();
            return "error";
        }
    }
    
    public User getUser(){
        return user;
    }
    
    public String getDetailButtonName(){
        return detailButtonName;
    }
    
    public boolean getShowInfo(){
        return showInfo;
    }
    
    public void changeInfo(){
        if(showAdminInfo){
            if(showInfo){
                showInfo = false;
                detailButtonName = "Details";
            }else {
                showInfo = true;
                detailButtonName = "Overview";
            }
        }
    }
    
    public boolean getShowAdminInfo(){
        showAdminInfo = service.isAdmin(user.getName());
        showInfo = false;
        return showAdminInfo;
    }
}
