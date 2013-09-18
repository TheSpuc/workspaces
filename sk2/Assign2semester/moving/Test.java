package moving;

public class Test {

	public static void printPos(Moveable m) {
		System.out.println("Position: ("+m.getX()+","+m.getY()+")");
	}
	
	public static void printHeightWidth(Sizeable s){
		System.out.println("Height: (" +s.getHeight() +", " + s.getWidth()+")");
	}
		
	public static void main(String[] args) {
		Circle c=new Circle(10,20,50);
		printPos(c);
		c.move(5,7);
		printPos(c);
		printHeightWidth(c);
		c.grow(0.5);
		printHeightWidth(c);

		System.out.println("------------------");
		
		Rectangle r=new Rectangle(25,25,40,20);
		printPos(r);
		r.move(10,20);
		printPos(r);
		printHeightWidth(r);
		r.grow(2.5);
		printHeightWidth(r);
	}

}
