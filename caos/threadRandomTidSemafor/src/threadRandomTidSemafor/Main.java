package threadRandomTidSemafor;

public class Main {
	
	public static void main(String[] args) throws InterruptedException{
		
		Common common = new Common(0);
		
		ThreadClass t1 = new ThreadClass(common, "Moo");
		ThreadClass t2 = new ThreadClass(common, "moo");
		t1.start();
		t2.start();
		
		t1.join();
		t2.join();
		
		System.out.println(common.getTal());
		
	}
}
