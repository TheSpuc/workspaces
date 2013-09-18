package forsikringTest;


public class BilForsikring {
	private double grundPraemie;
	
	public double getGrundPraemie(){
		return grundPraemie;
	}
	
	public  void setGrundpaemie(double grundPr){
		if (grundPr <= 0) throw new RuntimeException("grundPr skal vaere positiv");
		grundPraemie = grundPr;
	}
	
	public double beregnPraemie(int alder, boolean isKvinde, int skadeFrieaar){
		double praemie = grundPraemie;
		if (praemie == 0)
			throw new RuntimeException("GrundPraemie har ikke foeet en vaerdi");
		if (alder < 18)
			throw new RuntimeException("Du er for ung til at tegne en forsikring");
		if (alder-skadeFrieaar < 18)
			throw new RuntimeException("Du kan ikke have koert skadefri saa laenge");
		if (skadeFrieaar < 0)
			throw new RuntimeException("Antal skade frie aar skal vaere positiv");
		
		if(alder <25) praemie = 1.25 * grundPraemie;
		
		if (isKvinde) praemie = praemie - grundPraemie*0.05;
		
		if (skadeFrieaar < 3);
		else if (skadeFrieaar < 6)
			praemie = praemie - grundPraemie*0.15;
		else if (skadeFrieaar < 9)
			praemie = praemie - grundPraemie*0.25;
		else praemie = praemie - grundPraemie*0.35;
		return praemie;
	}

}
