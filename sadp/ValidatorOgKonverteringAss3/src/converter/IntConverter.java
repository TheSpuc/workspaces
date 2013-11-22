/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package converter;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;
import main.IntegerList;

/**
 *
 * @author Mark
 */
@FacesConverter("converter.IntConverter")
public class IntConverter implements Converter{

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        try{
            IntegerList list = new IntegerList();
            String[] splitArray = value.split(",");
            for(String s : splitArray){
                list.add(Integer.parseInt(s.trim()));
            }
            return list;
        }catch(Exception e){ //POKEBALL EXCEPTION CATCHING
            FacesMessage message = new FacesMessage(
                    FacesMessage.SEVERITY_ERROR, 
                    "Must be commaseperated numbers", 
                    "Must be commaseperated numbers. Ex: 1,3,4,5");
            throw new ConverterException(message);
        }
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        return value.toString();
    }
    
}
