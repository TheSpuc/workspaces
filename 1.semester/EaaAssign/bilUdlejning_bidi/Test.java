package bilUdlejning_bidi;

public class Test {

	public static void main(String[] args){
		
		Bil b1 = new Bil("1234566", 2);
		Bil b2 = new Bil("8765531", 4);
		
		b1.setPrisPrDay(100);
		b2.setPrisPrDay(200);
		
		Udlejning ud1 = new Udlejning(1, "08/06", 10);
		Udlejning ud2 = new Udlejning(2, "12/12", 20);
		
		
		ud1.addBil(b1);
		ud2.addBil(b2);
		
		System.out.println("ud1.addBil(b1) :" +ud1.getBiler());
		System.out.println("b1.getUdl: " + b1.getUdlejninger());
		System.out.println("ud2.addBil(b2): " +ud2.getBiler());
		System.out.println("b2.getUdl: " +b2.getUdlejninger());
		System.out.println();
		ud1.addBil(b2);
		System.out.println("b2 dage udlejet: " + b2.antalDageUdlejet());
		ud2.addBil(b1);
		
		System.out.println("ud1.addBil(b2) :" +ud1.getBiler());
		System.out.println("b1.getUdl: " + b1.getUdlejninger());
		System.out.println("ud2.addBil(b1): " +ud2.getBiler());
		System.out.println("b2.getUdl: " +b2.getUdlejninger());
		System.out.println();
		
		ud1.removeBil(b1);
		
		System.out.println("ud1.removeBil(b1): " + b1.getUdlejninger());
		System.out.println("b1.getUdl: " + b1.getUdlejninger());
		System.out.println();
		ud2.removeBil(b1);
		
		System.out.println("ud2.removeBil(b1): " + b1.getUdlejninger());
	}
}
