package template3OctoberAss4;

public class Hold implements Comparable<Hold>{
	private Ugedag ugedag;
	private String sportsgren;
	
	public Hold(Ugedag ugedag, String sportsgren) {
		super();
		this.sportsgren = sportsgren;
		this.ugedag = ugedag;
	}
	public Ugedag getUgedag() {
		return ugedag;
	}
	public String getSportsgren() {
		return sportsgren;
	}
	
	public String toString(){
		return ugedag + " - " + sportsgren;
	}
	@Override
	public int compareTo(Hold hold) {
		if (this.ugedag.equals(hold.getUgedag()))
			return this.sportsgren.compareTo(hold.getSportsgren());
		else
			return this.ugedag.compareTo(hold.getUgedag());
	
	}
	public enum Ugedag {MANDAG,TIRSDAG,ONSDAG,TORSDAG,FREDAG,LOERDAG,SOENDAG}
}
