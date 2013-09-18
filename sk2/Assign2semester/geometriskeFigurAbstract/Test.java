package geometriskeFigurAbstract;

public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Firkant f1 = new Firkant(1, 1, 20, 20);
		System.out.println(f1);
		System.out.println(f1.beregnAreal());
		f1.flyt(1, 1);
		System.out.println(f1 + "\n");
		
		
		Cirkel c1 = new Cirkel(1, 1, 5);
		System.out.println(c1);
		System.out.println(c1.beregnAreal());
		c1.flyt(1, 1);
		System.out.println(c1 + "\n");

		Ellipse e1 = new Ellipse(1, 1, 10, 20);
		System.out.println(e1);
		System.out.println(e1.beregnAreal());
		e1.flyt(1, 1);
		System.out.println(e1);
	}

}
