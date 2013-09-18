package minihandelelev;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Vare {
	
	@Id
	@GeneratedValue
	private int nummer;
	private String navn;
	private double stkPris;
	private int antalPaaLager;

	public Vare(){
		
	}
	
	public Vare(String navn, double stkPris) {
		this.navn = navn;
		this.stkPris = stkPris;
		antalPaaLager = 0;
	}

	public int getNummer() {
		return nummer;
	}

	public String getNavn() {
		return navn;
	}

	public void setNavn(String navn) {
		this.navn = navn;
	}

	public double getStkPris() {
		return stkPris;
	}

	public void setStkPris(double stkPris) {
		this.stkPris = stkPris;
	}

	public int getAntalPaaLager() {
		return antalPaaLager;
	}

	public void setAntalPaaLager(int antalPaaLager) {
		this.antalPaaLager = antalPaaLager;
	}

}
