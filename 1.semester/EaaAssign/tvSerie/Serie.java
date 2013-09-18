package tvSerie;

import java.util.ArrayList;

public class Serie {

	private String titel;
	private ArrayList<String> fasteSkuespillere;
	private ArrayList<Afsnit> afsnits;
	
	public Serie(String titel, ArrayList<String> fasteSkuespillere){
		this.titel = titel;
		this.fasteSkuespillere = fasteSkuespillere;
		afsnits = new ArrayList<>();
	}
	
	public String getTitle(){
		return titel;
	}
	
	public ArrayList<String> getFasteSkuespillere(){
		return new ArrayList<String>(fasteSkuespillere);
	}
	
	public void createAfsnit(int nr, int laengde, String gaesteSkuespiller){
		Afsnit as = new Afsnit(nr, laengde, gaesteSkuespiller);
		afsnits.add(as);
	}
	
	public void deleteAfsnit(Afsnit as){
		if(afsnits.contains(as)){
			afsnits.remove(as);
		}
	}
	
	public ArrayList<Afsnit> getAfsnits(){
		return new ArrayList<Afsnit>(afsnits);
	}
	
	public int samletLaengde(){
		int total = 0;
		for(Afsnit as : afsnits){
			total += as.getLaengde();
		}
		return total;
	}
}
