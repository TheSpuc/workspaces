package java;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
    
    private List<User> users;
    
    public Service(){
        users = new ArrayList<>();
        User u1 = new User("Liv", "liv123",0, "admin",false);
        User u2 = new User("Julie", "julie123",1, "user", false);
        User u3 = new User("Nick", "nick123",2, "admin",false);
        User u4 = new User("Mark", "mark123",3, "user", false);
        
        users.add(u1);
        users.add(u2);
        users.add(u3);
        users.add(u4);
        
    }
    
    public boolean containsUser(String name, String pass){
        boolean found = false;
        int i = 0;
        while(i<users.size() && !found){
            if(users.get(i).getPassword().equals(pass)){
                found = true;
            }
            i++;
        }
        return found;
    }
    
    public void addUser(User u){
        users.add(u);
    }
    
    public List<User> getUsers(){
        System.out.println("DAAAA FUKKKKKKKKKKK");
//        return new ArrayList<>(users);
        return users;
    }
    
    public List<User> listUsers(){
        List<User> result = new ArrayList<>();
        for(User entry : users){
            result.add(entry);
        }
        return result;
    }
    
    public boolean isAdmin(String name){
        boolean result = false;
        boolean found = false;
        int i = 0;
        while(i < users.size() && !found){
            if(users.get(i).getName().equals(name)){
                found = true;
                if(users.get(i).getUserPri().equals("admin")){
                    result = true;
                }
            }
            i++;
        }
        return result;
    }
    
    public void saveUsers(){
        for(User u: listUsers()){
            u.setEditable(false);
        }
    }
    
    public void deleteUser(User u, User loggedInUser){
        if(!u.getName().equals(loggedInUser.getName())){
            users.remove(u);
        }
    }
}
