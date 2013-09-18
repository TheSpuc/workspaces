package threadConsumerProducerSynchro;

public class Producer extends Thread {
	
	private Buffer buffer;
	
	public Producer(Buffer buffer){
		this.buffer = buffer;
	}
	
	public void run(){
		for(int i=0; i<50; i++){
			try {
				System.out.println("append " + i);
				buffer.append((int)Math.random());
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			buffer.TagerRandomTid(100);
		}
	}
}
