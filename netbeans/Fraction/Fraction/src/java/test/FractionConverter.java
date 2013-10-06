package test;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

@FacesConverter(forClass=Fraction.class)
public class FractionConverter implements Converter {
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
}
