package soegningelev;

public class Kunde {
	private String navn;
	private int nummer;
	private int koeb;
	public Kunde(String navn, int nummer, int koeb) {
		super();
		this.navn = navn;
		this.nummer = nummer;
		this.koeb = koeb;
	}
	public int getKoeb() {
		return koeb;
	}
	public void setKoeb(int koeb) {
		this.koeb = koeb;
	}
	public String getNavn() {
		return navn;
	}
	public void setNavn(String navn) {
		this.navn = navn;
	}
	public int getNummer() {
		return nummer;
	}
	public void setNummer(int nummer) {
		this.nummer = nummer;
	}
	public String toString(){
		return navn + " " + nummer + " " +  koeb;
	}

}
