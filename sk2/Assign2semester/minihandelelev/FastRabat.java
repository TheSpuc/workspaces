package minihandelelev;

public class FastRabat extends Rabat {

	
	private double rabatBeloeb;
	private double nedreGraense;
	
	public FastRabat(double rabatBeloeb, double nedreGraense){
		this.rabatBeloeb = rabatBeloeb;
		this.nedreGraense = nedreGraense;
	}
	
	public double rabatBeloeb(double beloebUdenRabat){
		double result = 0;
		if(beloebUdenRabat > nedreGraense){
			result = rabatBeloeb;
		}
		return result;
	}
}
