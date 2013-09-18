package vareAbstractStratgy;

public class SpiritusMoms implements Moms {
	
	private int alkoholprocent;
	
	public SpiritusMoms(int alkoholprocent){
		this.alkoholprocent = alkoholprocent;
	}

	@Override
	public double beregnMoms() {
		double moms = -1;
		if(alkoholprocent > 40){
			moms = 2.20;
		}else moms = 1.80;
		return moms;
	}
	
	

}
