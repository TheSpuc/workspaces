/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/

package validator;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

/**
 *
 * @author Mark
 */
@FacesValidator("validator.DateValidator")
public class DateValidator implements Validator {
    
    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        String toTest = (String) value;
        if(toTest.length() < 10){
            FacesMessage message = new FacesMessage(
                    FacesMessage.SEVERITY_ERROR,
                    "Must be 10 numbers. ",
                    "Must be 10 numbers. Example: 0101011000");
            throw new ValidatorException(message);
        }
        toTest = toTest.substring(0, 6);
        SimpleDateFormat formatter = new SimpleDateFormat("ddMMyyyy");
        formatter.setLenient(false);
        try{
           formatter.parse(toTest);
        }catch(ParseException e){
            FacesMessage message = new FacesMessage(
                    FacesMessage.SEVERITY_ERROR,
                    "Not a valid date. ",
                    "Not a valid date. Example: 010113");
            throw new ValidatorException(message);
        }
    }
}
