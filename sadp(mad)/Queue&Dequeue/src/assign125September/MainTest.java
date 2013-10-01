package assign125September;

import java.util.NoSuchElementException;

public class MainTest {
	
	public static void main(String[] args){
		FifoQueue<Integer> queue = new FifoQueue<Integer>();
		System.out.println(queue.isEmpty());
		queue.enqueue(5);
		System.out.println("Test getFront(): " + queue.getFront());
		System.out.println("Test dequeue(): " + queue.dequeue());
		System.out.println("Is list empty: " + queue.isEmpty());
		queue.enqueue(1);
		System.out.println("Added new entry, list is now: " + queue.isEmpty());
		queue.enqueue(2);
		queue.enqueue(3);
		queue.enqueue(4);
		queue.enqueue(6);
		
		System.out.println("list of all elements: ");
		for (int i = 0; i < 9; i++) {
			try {
				System.out.println(queue.dequeue() + ", ");
			} catch (NoSuchElementException e) {
				System.out.println("\nCAUGHT NO SUCH ELEMENTEXCEPTION!");
			}
		}
		try {
			System.out.println("\nPeeking in empty queue...");
			queue.getFront();
		} catch (NoSuchElementException e) {
			System.out.println(" --- >CAUGHT NO SUCH ELEMENTEXCEPTION!");
		}
	}
}
