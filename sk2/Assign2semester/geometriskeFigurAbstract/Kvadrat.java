package geometriskeFigurAbstract;

public class Kvadrat extends Firkant {

	private int stoerrelse;
	
	public Kvadrat(int xCoord, int yCoord, int stoerrelse){
		super(xCoord, yCoord, stoerrelse, stoerrelse);
		this.stoerrelse = stoerrelse;
	}
	
	public int getStoerrelse() {
		return stoerrelse;
	}

	public void setStoerrelse(int stoerrelse) {
		this.stoerrelse = stoerrelse;
	}
}
