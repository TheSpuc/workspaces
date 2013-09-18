package swimmer_composition;

import java.util.ArrayList;

public class AnvendSvoemmer {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		ArrayList<Double> tider = new ArrayList<>();
		tider.add(1.2);
		tider.add(2.3);
		tider.add(3.4);
		tider.add(33.4);
		
		TraeningsPlan workA = new TraeningsPlan('A', 20, 15);
		TraeningsPlan workB = new TraeningsPlan('B', 10, 10);
		
		workA.createSwimmer("Mark", "Aarhus", 1990, tider);
		workA.createSwimmer("Henrik", "Tranbjerg", 1950, tider);
		
		for(Swimmer sw : workA.getSvoemmer()){
			System.out.println(sw.getName());
		}
		
		workA.deleteSwimmer(workA.getSvoemmer().get(0));
		
		for(Swimmer sw : workA.getSvoemmer()){
			System.out.println(sw.getName());
		}
	}
}
