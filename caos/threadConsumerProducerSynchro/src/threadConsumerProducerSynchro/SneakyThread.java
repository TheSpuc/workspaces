package threadConsumerProducerSynchro;

public class SneakyThread extends Thread {

	private Buffer buffer;

	public SneakyThread(Buffer buffer){
		this.buffer = buffer;
	}

	public void run(){
		while(true){
			System.out.println("number: " + buffer.numberOfElements());
			try {
				this.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
