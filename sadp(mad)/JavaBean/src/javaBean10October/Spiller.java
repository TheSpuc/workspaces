package javaBean10October;

public class Spiller {
	
	private String navn;
	
	public Spiller(String navn){
		this.navn = navn;
	}
	
	public String getNavn(){
		return navn;
	}
	
	public void setNavn(String navn){
		this.navn = navn;
	}
	
	@Override
	public String toString(){
		return navn;
	}
}
