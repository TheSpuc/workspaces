package minihandelelev;

public class ProcentRabat extends Rabat {
	
	private int rabatProcent;
	
	public ProcentRabat(int rabatProcent){
		this.rabatProcent = rabatProcent;
	}
	
	public double rabatBeloeb(double beloebUdenRabat){
		return (rabatProcent/100.0)*beloebUdenRabat;
	}
}
