/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author mmb
 */
public class User {
    private String name;
    private String password;
    private int number;
    private String userPri;
    private String cpr;
    
    public User(){
        this("", "", 0, "moo", "0000000000");
    }
    
    public User(String name, String password, int number, String userPri, String cpr){
        this.name = name;
        this.password = password;
        this.number = number;
        this.userPri = userPri;
        this.cpr = cpr;
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
    
    public void setCpr(String cpr){
        this.cpr = cpr;
    }
    
    public String getCpr(){
        return cpr;
    }
}
