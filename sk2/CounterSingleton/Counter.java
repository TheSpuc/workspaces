
public class Counter {

	private static Counter counter;
	private int value;
	
	private Counter(){
		value = 0;
	}
	
	public static Counter getInstance(){
		if(counter == null){
			counter = new Counter();
		}
		return counter;
	}
	
	public void count(){
		value++;
	}
	
	public void timesTwo(){
		value *= 2;
	}
	
	public void zero(){
		value = 0;
	}
	
	public int getValue(){
		return value;
	}

}
