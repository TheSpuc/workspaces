package threadConsumerProducer;

public class Consumer extends Thread {

	private Buffer buffer;
	
	public Consumer(Buffer buffer){
		this.buffer = buffer;
	}
	
	public void run(){
		for(int i=0; i<100; i++){
			try {
				buffer.take();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			buffer.TagerRandomTid(100);
		}
	}
}
