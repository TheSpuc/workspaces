package composite24OctoberAss2;

public class MainTest {
	
	public static void main(String[] args){
		FigureComponent f1 = new FigureComposite("Triangle", 190);
		FigureComponent f2 = new FigureComposite("Elipse", 80);
		FigureComponent f3 = new FigureComposite("Square", 70);
		
		FigureComponent f2a = new FigureComposite("Triangle", 50);
		FigureComponent f2b = new FigureComposite("Triangle", 50);
		FigureComponent f2c = new FigureComposite("Triangle", 50);
		FigureComponent f2d = new FigureComposite("Triangle", 50);
		
		f2.addFigureComponent(f2a);
		f2.addFigureComponent(f2b);
		f2.addFigureComponent(f2c);
		f2.addFigureComponent(f2d);
		
		f1.addFigureComponent(f2);
		f1.addFigureComponent(f3);
		
		f1.draw();
		System.out.println(f1.getAround());
	}
}
