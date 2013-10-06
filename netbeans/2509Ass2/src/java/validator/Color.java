/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/

package validator;

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
@FacesValidator("validator.Color")
public class Color implements Validator {
    
    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        String test = (String) value;
        test = test.toLowerCase().trim();
        if(!test.equals("red") && !test.equals("green") && !test.equals("blue")){
            FacesMessage message = new FacesMessage(
                    FacesMessage.SEVERITY_ERROR, 
                    "Not a color", 
                    "Must be either green, blue or red");
            throw new ValidatorException(message);
        }
    }
    
}
