package generalization;

public abstract class Person {

	private String navn;
	private String adresse;
	
	Person(String navn, String adresse){
		this.navn = navn;
		this.adresse = adresse;
	}

	public String getNavn() {
		return navn;
	}

	public void setNavn(String navn) {
		this.navn = navn;
	}

	public String getAdresse() {
		return adresse;
	}

	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}
	
}
