package thread;


public class Traedaftestning {

	/**
	 * @param args
	 * @throws InterruptedException 
	 */
	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub
		System.out.println("Vi er igang");
		//for(int i=0; i<5; i++){
			threadClass t1 = new threadClass("MUUH"); 
			threadClass t2 = new threadClass("OEF");
			threadClass t3 = new threadClass("Maeaeh");
			t3.setPriority(Thread.MAX_PRIORITY); //t3 gets started to late, which makes the thread come second or last
			t1.start();
			t2.start();
			t3.start();
			t1.join();
			t2.join();
			t3.join();
			System.out.println("Nu det slut");
		//}
	}
}
