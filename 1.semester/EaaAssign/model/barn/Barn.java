package model.barn;

public class Barn {
	private String navn;
	private int alder;
	private boolean koen; // k�n = true angiver dreng, k�n = false angiver pige
	private double[] vaegt;

	public Barn(String navn, int alder, boolean koen) {
		this.navn = navn;
		this.alder = alder;
		this.koen = koen;
		vaegt = new double[] {4.2, 9.3, 12.4, 17.5, 23.2, 25.3, 28.6, 30.4, 33.9, 35.1,37.3};
	}

	public int getAlder() {
		return alder;
	}

	public void setAlder(int alder) {
		this.alder = alder;
	}

	public boolean isKoen() {
		return koen;
	}

	public void setKoen(boolean koen) {
		this.koen = koen;
	}

	public String getNavn() {
		return navn;
	}

	public void setNavn(String navn) {
		this.navn = navn;
	}

	public double getVaegt(int alder){
		double vaegtAlder = 0;
		if(alder >=0 && alder <=10){
			vaegtAlder = vaegt[alder];
		}
		return vaegtAlder;
	}

	public double getStigning(){
		double forskel = Double.MIN_VALUE;
		for (int i = 1; i < vaegt.length; i++) {
			if ((vaegt[i] - vaegt[i-1]) > forskel) {
				forskel = vaegt[i] - vaegt[i-1];
			}
		}
		return forskel;
	}

}
