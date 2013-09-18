package model.barn;

public class Institution {

	private String navn;
	private String adresse;
	private Barn[] boern;
	private int antalBoern;
	private static final int MAX_ANTAL_BOERN = 25;
	
	public Institution(String navn, String adresse){
		this.navn = navn;
		this.adresse = adresse;
		antalBoern = 0;
		boern = new Barn[MAX_ANTAL_BOERN];
	}
	
	public String getNavn(){
		return navn;
	}
	
	public String getAdresse(){
		return adresse;
	}
	
	public void setNavn(String navn){
		this.navn = navn;
	}
	
	public void setAdresse(String adresse){
		this.adresse = adresse;
	}
	
	public void addBarn(Barn ba){
		if(antalBoern < MAX_ANTAL_BOERN){
			boern[antalBoern] = ba;
			antalBoern++;
		}
	}
	
	public int gennemsnitAlder(){
		int gennemsnit = 0;
		for(int i = 0; i<antalBoern; i++){
			gennemsnit += boern[i].getAlder();
		}
		return gennemsnit/antalBoern;
	}
	
	public int antalPiger(){
		int antalPiger = 0;
		for(int i = 0; i<antalBoern; i++){
			if(!boern[i].isKoen()){
				antalPiger++;
			}
		}
		return antalPiger;
	}
	
	public int antalDrenge(){
		int antalDrenge = 0;
		for(int i = 0; i<antalBoern; i++){
			if(boern[i].isKoen()){
				antalDrenge++;
			}
		}
		return antalDrenge;
	}
	
	
}
