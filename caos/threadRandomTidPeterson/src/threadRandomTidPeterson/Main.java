package threadRandomTidPeterson;

public class Main {
	
	public static void main(String[] args) throws InterruptedException{
		
		Common common = new Common(0);
		
		ThreadClass t1 = new ThreadClass(common, "Moo", 0, 1);
		ThreadClass t2 = new ThreadClass(common, "moo", 1, 0);
		t1.start();
		t2.start();
		
		t1.join();
		t2.join();
		
		System.out.println(common.getTal());
		
	}
}
