package moving;

public class Circle implements Moveable, Sizeable {

	private int xCoord;
	private int yCoord;
	private int radius;

	public Circle(int xCoord, int yCoord, int radius){
		this.xCoord = xCoord;
		this.yCoord = yCoord;
		this.radius = radius;
	}
	
	public int getRadius(){
		return radius;
	}
	
	public void move(int dx, int dy) {
		xCoord += dx;
		yCoord += dy;
	}
	
	public int getX() {
		return xCoord;
	}

	public int getY() {
		return yCoord;
	}

	public void setPosition(int x, int y) {
		this.xCoord = x;
		this.yCoord = y;
	}

	public void grow(double factor) {
		radius *= factor;
	}

	public int getWidth() {
		return radius*2;
	}

	public int getHeight() {
		return radius*2;
	}

}
