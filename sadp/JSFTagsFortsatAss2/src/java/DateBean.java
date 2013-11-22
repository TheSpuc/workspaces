package java;


import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author mmb
 */
@Named(value="dateBean")
@SessionScoped
public class DateBean implements Serializable {
    
    
    private int[] days;
    private int selectedDay;
    private int[] months;
    private int selectedMonth;
    private int[] years;
    private int selectedYear;
    private String text;
    
    public DateBean(){
        this.text = "";
        
        //Day
        days = new int[31];
        for(int i=0; i<31; i++){
            days[i] = i+1;
        }
        
        //Month
        months = new int[12];
        for(int i=0; i<12; i++){
            months[i] = i+1;
        }
        
        //Year
        years = new int[31];
        int year = 1990;
        int i = 0;
        while(i<=30){
            years[i] = year;
            year++;
            i++;
        }
    }
    
    public int[] getDays(){
        int[] result = new int[days.length];
        System.arraycopy(days, 0, result, 0, days.length);
        return result;
    }
    
    public int[] getMonths(){
        int [] result = new int[months.length];
        System.arraycopy(months, 0, result, 0, months.length);
        return result;
    }
    
    public int[] getYears(){
        int[] result = new int[years.length];
        System.arraycopy(years, 0, result, 0, years.length);
        return result;
    }
    
    public int getSelectedDay(){
        return selectedDay;
    }
    
    public void setSelectedDay(int selectedDay){
        this.selectedDay = selectedDay;
    }
    
    public int getSelectedMonth(){
        return selectedMonth;
    }
    
    public void setSelectedMonth(int selectedMonth){
        this.selectedMonth = selectedMonth;
    }
    
    public int getSelectedYear(){
        return selectedYear;
    }
    
    public void setSelectedYear(int selectedYear){
        this.selectedYear = selectedYear;
    }
    
    public void setText(String text){
        this.text = text;
    }
    
    public String getText(){
        return text;
    }
    
    public String outputString(){
        text = selectedDay + "/" + selectedMonth + " " + selectedYear; 
        return null;
    }
}
