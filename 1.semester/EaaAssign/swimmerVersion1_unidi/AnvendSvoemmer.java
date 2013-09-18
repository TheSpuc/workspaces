package swimmerVersion1_unidi;

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
		
		
		
		TraeningsPlan workA = new TraeningsPlan('A', 20, 15);
		TraeningsPlan workB = new TraeningsPlan('B', 10, 5);
		
		Swimmer s1 = new Swimmer("Jan", "aarhus", 1990, tider, workA);
		Swimmer s2 = new Swimmer("Bo", "aarhus", 1980, tider, workA);
		Swimmer s3  = new Swimmer("Jesper", "aarhus", 2000, tider, workB);
		
		swimList.add(s1);
		swimList.add(s2);
		swimList.add(s3);
		
		System.out.println("s1: " + s1.alleTraeningsTimer());
		System.out.println("s2: " + s2.alleTraeningsTimer());
		System.out.println("s3: " + s3.alleTraeningsTimer());
		
	}

}
