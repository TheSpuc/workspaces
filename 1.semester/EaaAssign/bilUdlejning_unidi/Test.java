package bilUdlejning_unidi;

public class Test {

	public static void main(String[] args){
		
		Bil b1 = new Bil("1234566", 2);
		Bil b2 = new Bil("8765531", 4);
		
		b1.setPrisPrDay(100);
		b2.setPrisPrDay(200);
		
		Udlejning ud1 = new Udlejning(1, "08/06", 10);
		ud1.addBil(b1);
		ud1.addBil(b2);
		ud1.addBil(b1);
		ud1.addBil(b1);
		ud1.addBil(b2);
		
		System.out.println(ud1.getPris());
	}
}
