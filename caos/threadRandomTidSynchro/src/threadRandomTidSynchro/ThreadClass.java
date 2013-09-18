package threadRandomTidSynchro;


public class ThreadClass extends Thread {

	private Common common;
	
	public ThreadClass(Common common, String name){
		super();
		this.common = common;
	}
	
	public void run(){
		for(int j=0; j<100; j++){
			common.opdaterTal();
			common.TagerRandomTid(100);
		}
	}
}
