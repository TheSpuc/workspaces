package threadRandomTidSemafor;

import java.util.concurrent.Semaphore;

public class ThreadClass extends Thread {

	private Common common;
	private static Semaphore semaphore = new Semaphore(1);
	
	public ThreadClass(Common common, String name){
		super();
		this.common = common;
	}
	
	public void run(){
		for(int j=0; j<100; j++){
			try {
				semaphore.acquire();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			common.opdaterTal();
			semaphore.release();
			common.TagerRandomTid(100);
		}
	}
}
