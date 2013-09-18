package chap01.figures;

public class App
{
	public static void main(String[] args)
	{
		Circle circle1 = new Circle();
		circle1.makeVisible();
		
		Circle circle2 = new Circle();
		circle2.slowMoveHorizontal(100);
		circle2.makeVisible();
	}
}
