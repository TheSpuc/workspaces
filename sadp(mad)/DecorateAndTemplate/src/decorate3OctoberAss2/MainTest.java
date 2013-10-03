package decorate3OctoberAss2;

public class MainTest {
	
	public static void main(String[] args){
		CarType car = new Mondeo();
		car = new Leather(car);
		car = new RearSensor(car);
		car = new MetalCoat(car);
		
		System.out.println(car.getDescription() + " " + car.cost());
	}
}
