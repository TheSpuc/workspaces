package main;

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
    private String cpr;
    private String userPri;
    
    public User(){
        this("", "", 0, "", "moo");
    }
    
    public User(String name, String password, int number, String cpr, String userPri){
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
    
    public String getCpr(){
        return cpr;
    }
    
    public void setCpr(String cpr){
        this.cpr = cpr;
    }
    
    public String getUserPri(){
        return userPri;
    }
    
    public void setUserPri(String userPri){
        this.userPri = userPri;
    }
}
