package enumeration;

public class EnumDemo {

	enum Day {MANDAG, TIRSDAG, ONSDAG, TORSDAG, FREDAG, LOERDAG, SOENDAG}
	
	public static void main(String[] args) {
		Day workday = Day.MANDAG;
		Day weekend = Day.LOERDAG;
		System.out.println(workday);
		
		System.out.println("Ordinal vaerdien for " + Day.MANDAG +"  "+ Day.MANDAG.ordinal());
	    System.out.println("Ordinal vaerdien for weekend " + weekend + " "+ weekend.ordinal());
//		
//		if (Day.SOENDAG > Day.ONSDAG)
//			System.out.println("Soendag kommer senere end onsdag");
//		
		if (Day.SOENDAG.compareTo(Day.ONSDAG) > 0)
			System.out.println("Soendag kommer senere end onsdag");

	}

}
