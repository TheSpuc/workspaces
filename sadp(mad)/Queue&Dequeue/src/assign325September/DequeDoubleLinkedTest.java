package assign325September;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class DequeDoubleLinkedTest {

//	private DequeDoubleLinkedNode<Integer> deque;
	private DequeArray<Integer> deque;
	
	@Before
	public void setUp(){
//		deque = new DequeDoubleLinkedNode<>();
		deque = new DequeArray<>();
		deque.addFirst(1);
		deque.addLast(2);
	}
	
	@Test
	public void dequeGetFirst() {
		assertTrue(deque.getFirst()==1);
	}
	
	@Test
	public void dequeGetLast() {
		assertTrue(deque.getLast()==2);
	}
	
	@Test
	public void dequeRemoveFist(){
		assertTrue(deque.removeFirst() == 1);
		assertTrue(deque.removeFirst() == 2);
		assertTrue(deque.removeFirst() == null);
	}
	
	@Test
	public void dequeRemoveLast(){
		assertTrue(deque.removeLast() == 2);
		assertTrue(deque.removeLast() == 1);
		assertTrue(deque.removeLast() == null);
	}
	
	
	@Test
	public void dequeSize(){
		assertTrue(deque.size() == 2);
	}
	
	@Test
	public void dequeIsEmpty(){
		assertFalse(deque.isEmpty());
		deque.removeFirst();
		deque.removeFirst();
		assertTrue(deque.isEmpty());
	}
	
	@Test
	public void dequeClear(){
		assertFalse(deque.isEmpty());
		deque.clear();
		assertTrue(deque.isEmpty());
	}
	
	@Test
	public void testExpands(){
		deque.addFirst(1);
		deque.addFirst(1);
		deque.addFirst(1);
		deque.addFirst(1);
		deque.addFirst(1);
		deque.addFirst(1);
		deque.addFirst(1);
		deque.addFirst(1);
		deque.addFirst(1);
		assertTrue(deque.removeFirst()==1);
	}
}
