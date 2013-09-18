package model.barn;

public class AfproevModel {

	
	public static void main(String[] args) {

		Barn b1 = new Barn("Emma", 1,false); // pige p� 1 �r
		Barn b2 = new Barn("Oliver",3,true);  // dreng p� 3 �r
		Institution ins = new Institution("Aarhus", "Villavej");

		System.out.println(b1.getNavn() + " er " + b1.getAlder() + " aar" );
		System.out.println(b2.getNavn() + " er " + b2.getAlder() + " aar" );
		System.out.println("Stigning: " + b1.getStigning());
		
		ins.addBarn(b1);
		ins.addBarn(b2);
		
		System.out.println(b1.getVaegt(10));
		
		System.out.println("G alder: " +ins.gennemsnitAlder());
		System.out.println("Piger: " + ins.antalPiger());
		System.out.println("Drenge: " + ins.antalDrenge());
	}
}
