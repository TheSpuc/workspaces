    package test;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

@FacesValidator("test.Fraction")
public class FractionValidator implements Validator {
	public void validate(FacesContext context, UIComponent component,
			Object object) {
		Fraction fraction = (Fraction) object;
		if (fraction.getNumerator() >= fraction.getDenominator()) {
			FacesMessage message = new FacesMessage(
					FacesMessage.SEVERITY_ERROR,
					"Must be less than 1. ",
					"Must be less than 1. Example: 3/5");
			throw new ValidatorException(message);
		}
	}
}
