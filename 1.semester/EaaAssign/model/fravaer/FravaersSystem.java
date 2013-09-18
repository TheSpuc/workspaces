package model.fravaer;

/**
 * Models a system for calculating absents for students
 * @author Mark Medum Bundgaard
 *
 */
public class FravaersSystem {
	
	/**
	 * Method for printing out a absent table
	 * @param fravaer
	 */
	public void udskrivFravaer(int[][] fravaer) {
		System.out.println("Elev/Maaned:");
		for(int r=0, rowCount=fravaer.length; r<rowCount; r++){
			System.out.print("Elev " +r +": " );
			for(int c=0, coloumCount=fravaer[r].length; c<coloumCount; c++){
				System.out.print("| " + fravaer[r][c] + " | \t");
			}
			System.out.println();
		}
	}
	
	/**
	 * Method for finding the total absent for one specific student
	 * @param fravaer
	 * @param 0 =< elevNr <= fravaer.length 
	 * @return
	 */
	public int samletFravaer(int[][] fravaer,int elevNr){
		int samletFravaer = 0;
		for(int c = 0, coloumCount=fravaer[elevNr].length; c < coloumCount; c++){
			samletFravaer += fravaer[elevNr][c];
		}
		return samletFravaer;
	}
	
	/**
	 * Method for finding all students with 0 absents
	 * @param fravaer
	 * @return number of students with 0 absents
	 */
	public int mindstFravaer(int[][] fravaer) {
		int antal = 0;
		for(int r=0, rowCount=fravaer.length; r<rowCount; r++){
			int fravaerTemp = samletFravaer(fravaer, r);
			if(fravaerTemp == 0){
				antal++;
			}
		}
		return antal;
	}
	
	/**
	 * Method for finding the student with the highest absent
	 * @param fravaer
	 * @return
	 */
	public int mestFravaer(int[][] fravaer) {
		int elevNummer = 0;
		int fravaerElev = 0;
		for(int r=0, rowCount=fravaer.length; r<rowCount;  r++){
			int fravaerTemp = samletFravaer(fravaer, r);
			if(fravaerTemp > fravaerElev){
				fravaerElev = fravaerTemp;
				elevNummer = r;
			}
		}
		return elevNummer;
	}
	
	/**
	 * Method for finding the average absent for each student
	 * @param fravaer
	 * @param elevNr
	 * @return
	 */
	public double gennemsnit(int[][] fravaer, int elevNr) {
		double gennemsnit = 0;
		for(int c=0, coloumCount=fravaer[elevNr].length; c < coloumCount; c++){
			gennemsnit += fravaer[elevNr][c];
		}
		return gennemsnit/fravaer[elevNr].length;
	}
	
	/**
	 * Method for resetting one students absent
	 * @param fravaer
	 * @param elevNr
	 */
	public void nulstil(int[][] fravaer, int elevNr) {
		for(int c=0, coloumCount=fravaer[elevNr].length; c < coloumCount; c++){
			fravaer[elevNr][c] = 0;
		}
	}
}
