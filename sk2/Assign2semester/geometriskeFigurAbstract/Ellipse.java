package geometriskeFigurAbstract;

public class Ellipse extends GeometriskeFigur{
	
	private int vertikalRadius;
	private int horizontalRadius;
	
	public Ellipse(int xCoord, int yCoord, int vertikalRadius, int horizontalRadius){
		super(xCoord, yCoord);
		this.vertikalRadius = vertikalRadius;
		this.horizontalRadius = horizontalRadius;
	}

	public int getVertikalRadius() {
		return vertikalRadius;
	}

	public void setVertikalRadius(int vertikalRadius) {
		this.vertikalRadius = vertikalRadius;
	}

	public int getHorizontalRadius() {
		return horizontalRadius;
	}

	public void setHorizontalRadius(int horizontalRadius) {
		this.horizontalRadius = horizontalRadius;
	}
	
	public double beregnAreal(){
		return Math.PI*getVertikalRadius()*getHorizontalRadius();
	}
	
	public void flyt(int x, int y) {
		super.setxCoord((getxCoord()+x));
		super.setyCoord((getyCoord()+y));
	}
}
