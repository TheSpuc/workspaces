package javaBean10October; 

import java.beans.PropertyChangeSupport;

public class MainTest {
	
	public static void main(String[] args){
		
		Staevne s = new Staevne("Aarhus labaner");
		
		Spiller s1 = new Spiller("Stefan");
		Spiller s2 = new Spiller("Mike");
		Spiller s3 = new Spiller("Emil");
		Spiller s4 = new Spiller("Christian");
		
		
		Kamp k1 = new Kamp('E', 1, s1, s2);
		Kamp k2 = new Kamp('D', 2, s3, s4);
		
		s.addKamp(k1);
		s.addKamp(k2);
		
	}
}
