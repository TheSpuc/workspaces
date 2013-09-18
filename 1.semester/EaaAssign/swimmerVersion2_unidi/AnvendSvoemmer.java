package swimmerVersion2_unidi;

import java.util.ArrayList;

public class AnvendSvoemmer {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ArrayList<Swimmer> swimList = new ArrayList<>();
		
		ArrayList<Double> tider = new ArrayList<>();
		tider.add(1.2);
		tider.add(2.3);
		tider.add(3.4);
		tider.add(33.4);
		
		Swimmer s1 = new Swimmer("Jan", "aarhus", 1990, tider);
		Swimmer s2 = new Swimmer("Bo", "aarhus", 1980, tider);
		
		swimList.add(s1);
		swimList.add(s2);
	
		TraeningsPlan workA = new TraeningsPlan('A', 20, 15);
		
		workA.addSvoemmer(s1);
		workA.addSvoemmer(s2);
		
		for(Swimmer sw : workA.getSvoemmer()){
			System.out.println(sw.getName());
		}
	}

}
