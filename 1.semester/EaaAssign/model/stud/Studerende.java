package model.stud;

/**
 * Klasse der beskriver en studerende
 * 
 * @author mad
 * 
 */
public class Studerende {
	/*
	 * Attributter der beskriver den studerendes tilstand
	 */
	private String navn;
	private boolean aktiv;
	private int alder;
	
	private int[] karakterer; // indeholder den studerendes karakterer
	private int antalKarkarkterer;
	
	private static final int MAKS_ANTAL_KARAKTERER= 10;// det h�jeste antal karakterer en studerende kan have

	/*
	 * Constructor, n�r den studerende oprettes, skal den have et navn. Ved
	 * oprettelse er den studerende ikke aktiv
	 */
	public Studerende(String navn, int alder) {
		this.navn = navn;
		this.alder = alder;
		this.aktiv = false;
		
		antalKarkarkterer = 0;
		karakterer = new int[MAKS_ANTAL_KARAKTERER];
	}

	/*
	 * Man kan f� oplyst den studerendes alder, ved at kalde metoden getAlder
	 */
	public int getAlder() {
		return alder;
	}

	/*
	 * Den studerendes alder kan �ndres ved kald af setAlder metoden
	 */
	public void setAlder(int alder) {
		this.alder = alder;
	}

	/*
	 * Den studerendes navn kan �ndres ved kald af setNavn metoden
	 */
	public void setNavn(String navn) {
		this.navn = navn;
	}

	/*
	 * Man kan f� oplyst den studerendes navn, ved at kalde metoden getNavn
	 */
	public String getNavn() {
		return navn;
	}

	/*
	 * Den studerendes kan f� �ndret aktivitets status ved at kalde metoden
	 * setStatus
	 */
	public void setAktiv(boolean aktiv) {
		this.aktiv = aktiv;
	}

	/*
	 * Man kan f� oplyst den studerendes aktivitet, ved at kalde metoden isAktiv
	 */
	public boolean isAktiv() {
		return aktiv;
	}

	public void foedselsdag() {
		alder++;
	}

	public String arbejde() {
		String result = "";
		if (alder < 16) {
			result = "Gaa i skole - du er jo kun et barn";
		}
		if (alder >= 16 && alder < 20) {
			result = "Nyd din gymnasietid - og laer at studer";
		}
		if (alder == 20) {
			result = "Tag et sabbataar";
		}
		if (alder > 20 && alder <23) {
			result = "Kom igang med datamatikerudannelsen";
		}
		if (alder >= 23 && alder <30 && !aktiv) {
			result = "Tag nu dit studie serioest";
		}
		if (alder>=30){
			result = "Arbejd haardt";
		}
		return result;
	}

	/*
	 * Tilf�jer en karakter til den studerende. Hvis den studerende allerede har
	 * 10 karakterer registreres intet
	 */
	public void addKarakter(int karakter){
		if (antalKarkarkterer < MAKS_ANTAL_KARAKTERER){
			karakterer[antalKarkarkterer] = karakter;
			antalKarkarkterer++;
		}
	}
	
	/*
	 * Beregner og returnerer gennemsnittet af de karakterer den studerende har op n�et.
	 */
	public double karakterGennemsnit(){
		int sum = 0;
		double gennemsnit = 0.0;
		for (int i = 0; i < antalKarkarkterer; i++){
			sum = sum + karakterer[i];
		}
		if (antalKarkarkterer > 0){
			gennemsnit = (double)sum/ antalKarkarkterer;
		}
		return gennemsnit;
	}
	
	public int highestGrade(){
		int high = karakterer[0];
		for(int i= 1; i<antalKarkarkterer; i++){
			if(karakterer[i] > high){
				high = karakterer[i];
			}
		}
		return high;
	}
	
	public int lowestGrade(){
		int low = karakterer[0];
		for(int i=1; i<antalKarkarkterer; i++){
			if(karakterer[i] < low){
				low = karakterer[i];
			}
		}
		return low;
	}
	
	/*
	 *  Returnerer alle de karakterer den studerende har opn�et
	 */
	public int[] getKarakterer(){
		return karakterer;
	}
	
	
	/*
	 * Returnerer antallet af karakterer den studerende har f�et 
	 */
	public int getAntalKarakterer(){
		return antalKarkarkterer;
	}
}
