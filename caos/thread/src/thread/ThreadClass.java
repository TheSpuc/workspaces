package thread;


public class threadClass extends Thread {

	String dyrelyd;

	public threadClass (String dyrelyd) {
		super();
		this.dyrelyd = dyrelyd;
	}

	public void run() {
		for(int i=0; i<5; i++){
			System.out.println(dyrelyd);
			try {
				sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
