package model.fravaer;

/**
 * Models a testing of the class FravaersSystemet
 * @author Mark Medum Bundgaard
 *
 */
public class AfproevFravaersSystem {

	public static void main(String[] args) {
		int[][] fravaer10S = 
				{{2,0,0,0,3,1,0,2,0,0,0,0},
				{0,0,0,0,0,0,0,0,0,0,0,0},
				{2,0,0,0,3,1,0,2,0,0,0,0},
				{1,22,1,2,1,2,0,2,0,0,4,0},
				{5,0,0,0,0,0,0,10,0,0,0,0}};

		FravaersSystem karakterSystem = new FravaersSystem();
		karakterSystem.udskrivFravaer(fravaer10S);
		
		//iteration for all students, writing out all total absents for each
		for(int i=0; i<fravaer10S.length; i++){
			System.out.println("Samlet fravaer for elev " +i + ": " 
					+ karakterSystem.samletFravaer(fravaer10S, i));
		}

		System.out.println("Elev med mindst fravaer: " + karakterSystem.mindstFravaer(fravaer10S));
		System.out.println("Elev med mest fravaer: " + karakterSystem.mestFravaer(fravaer10S));
		
		//iteration for all students, writing out all average absents for each
		for(int i=0; i<fravaer10S.length; i++){
			System.out.println("Gennemsnit fravaer for elev " +i + ": " 
					+ karakterSystem.gennemsnit(fravaer10S, i));
		}
		
		//set absent of student i = 3 to 0
		karakterSystem.nulstil(fravaer10S, 3);
		
		//prints out new absents after reset
		karakterSystem.udskrivFravaer(fravaer10S);

	}
}
