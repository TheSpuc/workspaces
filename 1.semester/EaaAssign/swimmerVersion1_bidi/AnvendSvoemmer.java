package swimmerVersion1_bidi;

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
		TraeningsPlan workB = new TraeningsPlan('B', 10, 15);
		
		Swimmer s1 = new Swimmer("Jan", "aarhus", 1990, tider);
		Swimmer s2 = new Swimmer("Bo", "aarhus", 1980, tider);
		Swimmer s3  = new Swimmer("Jesper", "aarhus", 2000, tider);
		
		workA.addSwimmer(s1);
		workA.addSwimmer(s2);
		
		workB.addSwimmer(s3);
		
		System.out.println("s1: " + s1.alleTraeningsTimer());
		System.out.println(s1.getTraeningsPlan());
		System.out.println(s2.getTraeningsPlan());
		System.out.println(s3.getTraeningsPlan());
		
		workB.addSwimmer(s1);
		System.out.println("s1: " + s1.alleTraeningsTimer());
		System.out.println(s1.getTraeningsPlan());
		System.out.println(s2.getTraeningsPlan());
		System.out.println(s3.getTraeningsPlan());
	}

}
