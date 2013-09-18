package generalization;

public abstract class Ansat extends Person {
	
	private int loen;
	
	protected Ansat(String name, String adresse, int loen){
		super(name, adresse);
		this.loen = loen;
	}
	
	protected int getLoen(){
		return loen;
	}
	
	public abstract int beregnLoen();
}
