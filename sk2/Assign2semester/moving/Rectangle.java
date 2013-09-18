package moving;


public class Rectangle implements Moveable, Sizeable {

	private int x; // x,y angiver hvor i kkordinatsystemet rektanglen er placeret
	private int y;
	private int b; // bredden af rektanglet
	private int h; // h�jden af rektanglet
	
	public Rectangle(int x, int y, int b, int h) {
		this.x=x;
		this.y=y;
		this.b=b;
		this.h=h;
	}
	
	/**
	 * Flytter rektanglet relativt til dets position
	 */
	public void move(int dx, int dy) {
		this.x+=dx;
		this.y+=dy;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public void setPosition(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public void grow(double factor) {
		b *= factor;
		h *= factor;
	}

	public int getWidth() {
		return b;
	}

	public int getHeight() {
		return h;
	}

}
