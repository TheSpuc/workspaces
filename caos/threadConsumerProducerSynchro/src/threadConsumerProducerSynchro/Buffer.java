package threadConsumerProducerSynchro;



public class Buffer {

	private int[] ints;
	private int nrOfElements;
	private int nextIn;
	private int nextOut;
	private int error;

	public Buffer(){
		ints = new int[25];
		nrOfElements = 0;
		nextIn = 0;
		nextOut = 0;
		error = 0;
	}

	public int numberOfElements(){
		return nrOfElements;
	}


	public synchronized void append(int element) throws InterruptedException{
		while(nrOfElements == ints.length){
			wait();
		}
		ints[nextIn] = element;
		nextIn = (nextIn+1) % ints.length;
		nrOfElements++;
		System.out.println("elements append: " + nrOfElements);
		notifyAll();
	}

	public synchronized int take() throws InterruptedException{
		int result = 0;
		while(nrOfElements == 0){
			wait();
		}
		result = ints[nextOut];
		ints[nextOut] = -1;
		nextOut = (nextOut+1) % ints.length;
		nrOfElements--;
		if(result == -1){
			error++;
		}
		notifyAll();
		System.out.println("elements take: " + nrOfElements);
		return result;
	}

	public int getError(){
		return error;
	}

	public void TagerRandomTid(int max){
		for(int i=0; i<max; i++){
			for(int j=i; j<max; j++){
				double a = Math.random();
				double b = Math.random();
				a+=b;
			}
		}
	}
}
