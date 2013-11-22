package java;


import java.util.ArrayList;
import java.util.List;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author mmb
 */
@ApplicationScoped
@Named(value="service")
public class Service {
    
    private List<Car> cars;
    
    public Service(){
        cars =  new ArrayList<>();
        cars.add(new Car("12345", "Ford", "Fiesta"));
        cars.add(new Car("54321", "Volvo", "Smart"));
        cars.add(new Car("13579", "Bugatti", "Veyron"));
    }
    
    public List<Car> getCars(){
        return new ArrayList<>(cars);
    }
    
    public void removeCar(Car car){
        cars.remove(car);
    }
    
    public Car findCar(String regnr){
        Car result = null;
        int i = 0;
        boolean found = false;
        while(!found && i<cars.size()){
            if(cars.get(i).getRegnr().equals(regnr)){
                found = true;
                result = cars.get(i);
            }
            i++;
        }
        return result;
    }
    
}
