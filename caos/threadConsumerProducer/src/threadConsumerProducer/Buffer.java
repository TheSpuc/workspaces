package threadConsumerProducer;

import java.util.concurrent.Semaphore;


public class Buffer {
	
	private int[] ints;
	private int nrOfElements;
	private int nextIn;
	private int nextOut;
	private int error;
	private Semaphore semaphore;
	private Semaphore sFull;
	private Semaphore sEmpty;
	private int counter;
	
	public Buffer(){
		ints = new int[25];
		nrOfElements = 0;
		nextIn = 0;
		nextOut = 0;
		error = 0;
		semaphore = new Semaphore(1);
		sFull = new Semaphore(25);
		sEmpty = new Semaphore(0);
		counter = 1;
	}
	
	public int numberOfElements(){
		return nrOfElements;
	}
	
	
	public void append(int element) throws InterruptedException{
		sFull.acquire(); //Count how many free spaces there is, i would like to wait as long as there is free space.
		semaphore.acquire();
		System.out.println(counter);
		if(nrOfElements < ints.length){
			ints[nextIn] = element;
			nextIn = (nextIn+1) % ints.length;
			nrOfElements++;
		}
		counter++;
		semaphore.release();
		sEmpty.release();
	}
	
	public int take() throws InterruptedException{
		int result = 0;
		sEmpty.acquire();
		semaphore.acquire();
		if(nrOfElements != 0){
			result = ints[nextOut];
			ints[nextOut] = -1;
			nextOut = (nextOut+1) % ints.length;
			nrOfElements--;
		}
		if(result == -1){
			error++;
		}
		semaphore.release();
		sFull.release();
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
