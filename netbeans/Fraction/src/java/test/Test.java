package test;
import java.io.Serializable;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.validator.ValidatorException;
import javax.inject.Named;

@Named
@RequestScoped
public class Test implements Serializable {

	private Fraction fraction;

	public Fraction getFraction() {
		return fraction;
	}

	public void setFraction(Fraction fraction) {
		this.fraction = fraction;
	}

	public void fractionValidate(FacesContext context, UIComponent component,
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

	public Converter getFractionConverter() {
		return new Converter() {
			public Object getAsObject(FacesContext context, UIComponent component,
					String string) throws ConverterException {
				try {
					Fraction fraction = new Fraction();
					int index = string.indexOf('/');
					String numerator = string.substring(0, index).trim();
					String denominator = string.substring(index + 1).trim();
					fraction.setNumerator(Integer.parseInt(numerator));
					fraction.setDenominator(Integer.parseInt(denominator));
					return fraction;
				} catch (Exception e) {
					FacesMessage message = new FacesMessage(
							FacesMessage.SEVERITY_ERROR,
							"Must be a fraction. ",
							"Must be a fraction. Example 3/5");
					throw new ConverterException(message);
				}
			}

			public String getAsString(FacesContext context, UIComponent component,
					Object object) throws ConverterException {
				return object.toString();
			}
		};
	}
}
