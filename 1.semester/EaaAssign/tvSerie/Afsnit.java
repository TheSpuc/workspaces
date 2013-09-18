package tvSerie;

public class Afsnit {

	private int nr;
	private int laengde;
	private String gaesteSkuespiller;
	
	Afsnit(int nr, int laengde, String gaesteSkuespiller){
		this.nr = nr;
		this.laengde = laengde;
		this.gaesteSkuespiller = gaesteSkuespiller;
	}
	
	public int getNr(){
		return nr;
	}
	
	public int getLaengde(){
		return laengde;
	}
	
	public String getGaesteSkuespiller(){
		return gaesteSkuespiller;
	}
	
	public void setGaesteSkuespiller(String skuespiller){
		gaesteSkuespiller = skuespiller;
	}

}
