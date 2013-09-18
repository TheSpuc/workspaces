package model.stud;

public class AfproevStuderende {

	/**
	 * Et program der opretter et par studerende og kalder metoder p� disse.
	 */
	public static void main(String[] args) {
		Studerende s1 = new Studerende("Hanne",20);
		Studerende s2 = new Studerende("Margrethe",12);
		s2.setAktiv(true);
		
		System.out.println(s1.getNavn() + " "+ s1.getAlder() +" " + s1.arbejde());
		System.out.println(s2.getNavn() + " "+ s2.getAlder() +" " + s2.arbejde());
		
		s2.addKarakter(7);
		s2.addKarakter(12);
		s2.addKarakter(10);
		
		System.out.println("highest grade: " + s2.highestGrade());
		System.out.println("lowest grade: " + s2.lowestGrade());
		
		System.out.println(s2.getNavn() + " har et gennemsnit paa: " + s2.karakterGennemsnit());
		System.out.println(s1.getNavn() + " har et gennemsnit paa: " + s1.karakterGennemsnit());
		
		System.out.println("Karaktererne for " + s2.getNavn());
		for(int i=0; i<s2.getAntalKarakterer(); i++){
			System.out.println(s2.getKarakterer()[i]);
		}

	}

}
