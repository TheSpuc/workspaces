package personGaver;

public class Test {

	public static void main(String[] args){
		
		Person p1 = new Person("Emil", 10);
		Person p2 = new Person("Henrik", 8);
		Person p3 = new Person("Nikolaj", 7);
		
		Present gift1 = new Present("Car", p1);
		Present gift2 = new Present("Firetruck", p2);
		Present gift3 = new Present("Moon", p1);
		Present gift4 = new Present("Pony", p3);
		Present gift5 = new Present("Plain", p2);
		
		gift1.setPrice(100);
		gift2.setPrice(200);
		gift3.setPrice(100);
		gift4.setPrice(10);
		gift5.setPrice(300);
		
		
		gift1.givePresent(p3);
		gift2.givePresent(p3);
		gift3.givePresent(p3);
		gift5.givePresent(p3);
		
		
		System.out.println(p3.totalPresentMoney());
		
		System.out.println(p3.getPresentPeople());
	}
}
