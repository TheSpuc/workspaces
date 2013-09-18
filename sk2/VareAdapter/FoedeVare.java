
public class FoedeVare extends Vare {

	public FoedeVare(int pris, String navn) {
		super(pris, navn);
	}

	@Override
	public double beregnMoms() {
		return getPris() * 0.05;
	}

}
