package threadRandomTidPeterson;


public class ThreadClass extends Thread {

	private Common common;
	private int me;
	private int other;
	private static volatile boolean[] flag = new boolean[2];
	private static volatile int turn = 0;

	public ThreadClass(Common common, String name, int me, int other){
		super();
		this.common = common;
		this.me = me;
		this.other = other;
	}

	public void run(){
		for(int j=0; j<100; j++){
			System.out.println("me beginning: " + me);
			flag[me] = true;
			turn = other;
			while(flag[other] && turn == other);
			System.out.println("me inside: " + me);
			System.out.println("tal before: " + common.getTal());
			common.opdaterTal();
			System.out.println("Tal after: " + common.getTal());
			flag[me] = false;
			common.TagerRandomTid(100);
		}
	}
}
