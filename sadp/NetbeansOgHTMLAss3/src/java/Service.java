package java;

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
        User u1 = new User("Liv", "liv123");
        User u2 = new User("Julie", "julie123");
        User u3 = new User("Nick", "nick123");
        User u4 = new User("Mark", "mark123");
        
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
}
