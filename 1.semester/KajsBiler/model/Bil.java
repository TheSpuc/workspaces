package model;

public class Bil {

	private String maerke;
	private int regnr;

	/**
	 * @param regnr
	 * @param maerke
	 */
	public Bil(int regnr, String maerke) {

		this.regnr = regnr;
		this.maerke = maerke;

	}

	/**
	 * @return
	 */
	public String getmaerke() {
		return maerke;
	}

	/**
	 * @param navn
	 */
	public void setmaerke(String maerke) {
		this.maerke = maerke;
	}

	/**
	 * @return
	 */
	public int getRegnr() {
		return regnr;
	}

	/**
	 * @param
	 */
	public void setRegnr(int regnr) {
		this.regnr = regnr;
	}

	public String toString() {
		return regnr + " " + maerke;
	}
}
