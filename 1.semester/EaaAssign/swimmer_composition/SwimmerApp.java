package swimmer_composition;

import java.util.ArrayList;

public class SwimmerApp {

	public static void main(String[] args){
		ArrayList<Double> swimTimes = new ArrayList<Double>();
		swimTimes.add(10.0);
		swimTimes.add(40.0);
		swimTimes.add(20.0);
		
		Swimmer swim = new Swimmer("Mark", "Aarhus", 1990, swimTimes);
		
		
		System.out.println("Bedst: " + swim.bedsteTid());
		System.out.println("Club: " +swim.getClub());
		System.out.println("Gennemsnit: " + swim.gennemsnitAfTid());
		System.out.println("navn: " + swim.getName());
		System.out.println("aar: " +swim.getYear());
		//System.out.println("Collections måden: " + swim.snitUdenDaarligste());
		System.out.println("Slave MÅDEN: " +swim.snitUdenDaarligsteSlaveMaaden());
	}
}
