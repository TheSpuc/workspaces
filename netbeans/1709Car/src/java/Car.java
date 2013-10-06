
import java.io.Serializable;
import javax.enterprise.context.ApplicationScoped;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author mmb
 */
@ApplicationScoped
public class Car implements Serializable{
    
    private String regnr;
    private String company; 
    private String model;
    
    public Car(String regnr, String company, String model){
        this.regnr = regnr;
        this.company = company;
        this.model = model;
    }
    
    public String getRegnr(){
        return regnr;
    }
    
    public void setRegnr(String regnr){
        this.regnr = regnr;
    }
    
    public String getCompany(){
        return company;
    }
    
    public void setCompany(String company){
        this.company = company;
    }
    
    public String getModel(){
        return model;
    }
    
    public void setModel(String model){
        this.model = model;
    }
    
    @Override
    public String toString(){
        return company + " " + model + " " + regnr;
    }
    
    //SelectManyCheckBoxRelated
    
    
}
