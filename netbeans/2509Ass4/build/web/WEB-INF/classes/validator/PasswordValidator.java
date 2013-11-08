/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/

package validator;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

/**
 *
 * @author Mark
 */
@FacesValidator("validator.PasswordValidator")
public class PasswordValidator implements Validator {
    
    private final String pattern = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{8,10}$";
    
    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        
        UIInput firstComp = (UIInput) component.findComponent("firstPassword");
        String firstPassword = (String) firstComp.getLocalValue();
        String secondPassword = (String) value;
        
        if(!firstPassword.equals(secondPassword)){
            FacesMessage message = new FacesMessage(
                    FacesMessage.SEVERITY_ERROR,
                    "Password dont match",
                    "Password should match");
            throw new ValidatorException(message);
        }
        
        boolean match = false;
        match = firstPassword.matches(pattern);
        match = secondPassword.matches(pattern);
        
        if(!match){
            FacesMessage message = new FacesMessage(
                    FacesMessage.SEVERITY_ERROR,
                    "No good",
                    "Give me a break!");
            throw new ValidatorException(message);
        }
    }
}


