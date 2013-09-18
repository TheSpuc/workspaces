
public class UseCounter {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		Counter c = Counter.getInstance();
		
		System.out.println(c.getValue());
		
		c.count();
		
		System.out.println("Count: " + c.getValue());
		
		c.timesTwo();
		
		System.out.println("TimesTwo: " + c.getValue());
		
		c.zero();
		
		System.out.println("Zero: " + c.getValue());

	}

}
