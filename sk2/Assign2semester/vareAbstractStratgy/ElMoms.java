package vareAbstractStratgy;

public class ElMoms implements Moms {

	private double pris;
	
	public ElMoms(double pris){
		this.pris = pris;
	}
	
	public double beregnMoms() {
		double nettopris = pris;
		double moms = -1;
		if((nettopris*1.30)-nettopris <= 3){
			moms = 3;
		}else moms = 1.30;
		return moms;
	}

}
