package generalization;

public class Arbejdsdreng extends Ansat {
	
	
	public Arbejdsdreng(String name, String adresse, int loen){
		super(name, adresse, loen);
	}

	public int beregnLoen() {
		return super.getLoen()*25;
	}
}
