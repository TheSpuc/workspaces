package threadConsumerProducer;

public class MainBuffer {

	public static void main(String[] args) throws InterruptedException {
		Buffer buffer = new Buffer();
		
		//SneakyThread s = new SneakyThread(buffer);
		Consumer c = new Consumer(buffer);
		Producer p = new Producer(buffer);
		
		//s.start();
		c.start();
		p.start();
		
		//s.join();
		c.join();
		p.join();
		System.out.println("Error: " + buffer.getError());
	}

}
