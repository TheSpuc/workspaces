package TogPlads;

public class Plads {

	private int nr;
	private boolean reserveret;
	private Tog tog;

	Plads(int nr, Tog tog){
		this.nr = nr;
		this.tog = tog;
	}

	public int getNr(){
		return nr;
	}

	public void setReserveret(boolean reserveret){
		this.reserveret = reserveret;
	}

	public boolean isReserveret(){
		return reserveret;
	}

	public Tog getTog(){
		return tog;
	}

	void unsetTog(){
		if(tog != null){
			tog = null;
		}
	}
}
