package java;


import java.io.Serializable;
import java.util.List;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author mmb
 */
@Named(value="bean")
@SessionScoped
public class Bean implements Serializable {
    
    @Inject
    private Service service;
    private Car car;
    private String selectedCarRegnr;
    
    public Bean(){
        selectedCarRegnr = "";
        service = new Service();
    }
    
    public String getSelectedCarRegnr(){
        return selectedCarRegnr;
    }
    
    public void setSelectedCarRegnr(String selectedCarRegnr){
        this.selectedCarRegnr = selectedCarRegnr;
    }
    
    public List<Car> getCars(){
        return service.getCars();
    }
    
    public Car getCar(){
        return car;
    }
    
    public String update(){
        car = service.findCar(selectedCarRegnr);
        service.removeCar(car);
        return null;
    }
    
    //SelectManyCheckBox related
    
    private String[] selectedRegnr = new String[10];
    
    public String[] getSelectedCars(){
        String[] result = new String[selectedRegnr.length];
        System.arraycopy(selectedRegnr, 0, result, 0, selectedRegnr.length);
        return result;
    }
    
    public String updateMany(){
        for(String s : selectedRegnr){
            car = service.findCar(s);
            service.removeCar(car);
        }
        return null;
    }
    
    public void setSelectedCars(String[] selectedRegnr){
        this.selectedRegnr = selectedRegnr;
    }
}
