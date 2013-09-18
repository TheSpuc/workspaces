
public class Elvare extends Vare {

	public Elvare(int pris, String navn) {
		super(pris, navn);
	}

	@Override
	public double beregnMoms() {
		double moms = getPris() * 0.30;
		if(moms < 3){
			moms = 3;
		}
		return moms;
	}

}
