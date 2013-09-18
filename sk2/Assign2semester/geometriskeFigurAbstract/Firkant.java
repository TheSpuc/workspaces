package geometriskeFigurAbstract;

public class Firkant extends GeometriskeFigur{

	private int laengde;
	private int bredde;
	
	public Firkant(int xCoord, int yCoord, int laengde, int bredde){
		super(xCoord, yCoord);
		this.laengde = laengde;
		this.bredde = bredde;
	}
	
	public double beregnAreal() {
		return laengde*bredde;
	}

	public void flyt(int x, int y) {
		super.setxCoord((getxCoord()+x));
		super.setyCoord((getyCoord()+y));
	}

}
