package state10OctoberAss2;

public class MainTest {
	
	public static void main(String[] args) throws Exception{
		Door d = new Door();
		d.setState(new ClosingImpl(d));
		Thread.sleep(10000);
	}
}
