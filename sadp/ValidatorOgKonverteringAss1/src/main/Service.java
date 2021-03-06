package main;


import java.util.HashMap;
import java.util.Map;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;

/**
 *
 * @author mmb
 */
@ApplicationScoped
@Named(value="service")
public class Service {
    
    private Map<String, User> users;
    
    public Service(){
        users = new HashMap<>();
        User u1 = new User("Liv", "liv123",0, "0101901300", "admin");
        User u2 = new User("Julie", "julie123",1, "0202901400", "user");
        User u3 = new User("Nick", "nick123",2, "0303901500", "admin");
        User u4 = new User("Mark", "mark123",3, "0404901500", "user");
        
        users.put("Liv", u1);
        users.put("Julie", u2);
        users.put("Nick", u3);
        users.put("Mark", u4);
        
    }
    
    public boolean containsUser(String name, String pass){
        boolean result = false;
        User temp = users.get(name);
        if(temp != null){
            if(temp.getPassword().equals(pass)){
                result = true;
            }
        }
        return result;
    }
    
    public void addUser(User u){
        users.put(u.getName(), u);
    }
    
    public Map<String,User> getUsers(){
        return new HashMap<>(users);
    }
    
    public void changePassword(String name, String newPassword){
        System.out.println("her");
        users.get(name).setPassword(newPassword);
    }
    
    public boolean isAdmin(String name){
        boolean result = false;
        if(users.get(name).getUserPri().equals("admin")){
            result = true;
        }
        return result;
    }
}
