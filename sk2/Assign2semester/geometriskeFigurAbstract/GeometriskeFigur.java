package geometriskeFigurAbstract;

public abstract class GeometriskeFigur {
	
	private int xCoord;
	private int yCoord;
	
	GeometriskeFigur(int xCoord, int yCoord) {
		this.xCoord = xCoord;
		this.yCoord = yCoord;
	}

	public abstract double beregnAreal();
	
	public abstract void flyt(int x, int y);
	
	public int getxCoord() {
		return xCoord;
	}

	public void setxCoord(int xCoord) {
		this.xCoord = xCoord;
	}

	public int getyCoord() {
		return yCoord;
	}

	public void setyCoord(int yCoord) {
		this.yCoord = yCoord;
	}
	
	public String toString(){
		return "X: " + getxCoord() + ", Y: " + getyCoord(); 
	}
}
