package com.corejsf;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean; 
   // or import javax.inject.Named;
import javax.faces.bean.SessionScoped; 
   // or import javax.enterprise.context.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;

@ManagedBean(name="bb") // or @Named("bb")
@SessionScoped
public class BackingBean implements Serializable {
   private int day;
   private int month;
   private int year;

   public int getDay() { return day; }
   public void setDay(int newValue) { day = newValue; }

   public int getMonth() { return month; }
   public void setMonth(int newValue) { month = newValue; }

   public int getYear() { return year; }
   public void setYear(int newValue) { year = newValue; }

   public void validateDate(FacesContext context, UIComponent component, 
      Object value) {

      UIInput dayInput = (UIInput) component.findComponent("day");
      UIInput monthInput = (UIInput) component.findComponent("month");
   
      int d = (Integer) dayInput.getLocalValue();
      int m = (Integer) monthInput.getLocalValue();
      int y = (Integer) value;

      if (!isValidDate(d, m, y)) {
         FacesMessage message 
            = com.corejsf.util.Messages.getMessage(
               "com.corejsf.messages", "invalidDate", null);
         message.setSeverity(FacesMessage.SEVERITY_ERROR);
         throw new ValidatorException(message);
      }
   }

   private static boolean isValidDate(int d, int m, int y) {
      if (d < 1 || m < 1 || m > 12) return false;
      if (m == 2) {
         if (isLeapYear(y)) return d <= 29;
         else return d <= 28;
      }
      else if (m == 4 || m == 6 || m == 9 || m == 11)
         return d <= 30;
      else 
         return d <= 31;
   }
    
   private static boolean isLeapYear(int y) {
      return y % 4 == 0 && (y % 400 == 0 || y % 100 != 0); 
   }
}