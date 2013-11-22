package java;


import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

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
    private boolean showInfo;
    private boolean showAdminInfo;
    
    public UserBean(){
        user = new User(null, null, 0, "default", false);
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
    
    public boolean getShowInfo(){
        return showInfo;
    }
    
    public void setShowInfo(boolean showInfo){
        this.showInfo = showInfo;
    }
    
    public void changeInfo(){
        if(showAdminInfo){
            if(showInfo){
                showInfo = false;
            }else {
                showInfo = true;
            }
        }
    }
    
    public boolean getShowAdminInfo(){
        showAdminInfo = service.isAdmin(user.getName());
        return showAdminInfo;
    }
    
    public String logout(){
        invalidateLogout();
        return "index";
    }
    
    public String saveAction(){
        service.saveUsers();
        return null;
    }
    
    public String setEditable(User u){
        u.setEditable(true);
        return null;
    }
    
    public String deleteUser(User u){
        service.deleteUser(u, user);
        return null;
    }
    
    public String createUser(){
        service.createUser(user);
        return "welcome";
    }
    
    private void invalidateLogout(){
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        session.invalidate();
    }
}
