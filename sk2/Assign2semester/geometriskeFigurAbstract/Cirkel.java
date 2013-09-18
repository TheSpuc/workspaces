package geometriskeFigurAbstract;

public class Cirkel extends GeometriskeFigur{
	
	private int radius;
	
	public Cirkel(int xCoord, int yCoord, int radius){
		super(xCoord, yCoord);
		this.radius = radius;
	}

	public int getRadius() {
		return radius;
	}

	public void setRadius(int radius) {
		this.radius = radius;
	}
	
	public double beregnAreal(){
		return Math.PI*Math.pow(getRadius(), 2);
	}

	public void flyt(int x, int y) {
		super.setxCoord((getxCoord()+x));
		super.setyCoord((getyCoord()+y));
	}
}
