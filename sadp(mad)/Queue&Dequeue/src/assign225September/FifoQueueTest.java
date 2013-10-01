package assign225September;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class FifoQueueTest {

	private FifoQueueSingleNode<Integer> queue;
	
	@Before
	public void setUp(){
		queue = new FifoQueueSingleNode<>();
		queue.enqueue(1);
		queue.enqueue(2);
		queue.enqueue(3);
	}
	
	@Test
	public void getFrontTest() {
		assertTrue(queue.getFront()==1);
		queue.dequeue();
		assertTrue(queue.getFront()==2);
		queue.dequeue();
		assertTrue(queue.getFront()==3);
		queue.dequeue();
		assertTrue(queue.getFront()==null);
	}
	
	@Test
	public void dequeueTest(){
		assertTrue(queue.dequeue()==1);
		assertTrue(queue.getFront()==2);
	}
	
	@Test
	public void expandTest(){
		queue.enqueue(4);
		queue.enqueue(5);
		queue.enqueue(6);
		queue.enqueue(7);
		queue.enqueue(8);
		queue.enqueue(9);
		queue.enqueue(10);
		queue.enqueue(11);
		queue.enqueue(12);
		assertTrue(queue.dequeue()==1);
		queue.dequeue();
		queue.dequeue();
		assertTrue(queue.dequeue()==4);
	}
	
	

}
