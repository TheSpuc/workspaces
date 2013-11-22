package java;


/**
 *
 * @author mmb
 */
public class User {
    private String name;
    private String password;
    private int number;
    private String userPri;
    
    public User(){
        this("", "", 0, "moo");
    }
    
    public User(String name, String password, int number, String userPri){
        this.name = name;
        this.password = password;
        this.number = number;
        this.userPri = userPri;
    }
    
    public String getName(){
        return name;
    }
    
    public void setName(String name){
        this.name = name;
    }
    
    public String getPassword(){
        return password;
    }
    
    public void setPassword(String password){
        this.password = password;
    }
    
    public void setNumber(int number){
        this.number = number;
    }
    
    public int getNumber(){
        return number;
    }
    
    public String getUserPri(){
        return userPri;
    }
    
    public void setUserPri(String userPri){
        this.userPri = userPri;
    }
}
