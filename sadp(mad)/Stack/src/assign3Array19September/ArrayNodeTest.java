package assign3Array19September;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;


public class ArrayNodeTest {

	private DropOutStackArray<String> stack;
	
	@Before
	public void setUp(){
		stack = new DropOutStackArray<>();
		stack.push("Mark");
		stack.push("Nick");
		stack.push("Hest");
	}
	
	@Test
	public void testTop() {
		assertTrue((stack.peek().equals("Hest")));
	}
	
	@Test
	public void testPop(){
		assertTrue((stack.peek().equals("Hest")));
		stack.pop();
		assertTrue((stack.peek().equals("Nick")));
	}
	
	@Test
	public void testIsEmptry(){
		assertFalse(stack.isEmpty());
		stack.pop();
		stack.pop();
		stack.pop();
		assertTrue(stack.isEmpty());
	}

	@Test
	public void testIsClear(){
		assertTrue((stack.peek().equals("Hest")));
		stack.clear();
		assertTrue(stack.isEmpty());
		assertTrue(stack.peek()== null);
	}
	
	@Test
	public void testFull(){
		stack.push("Papir");
		stack.push("Prut");
		stack.push("Hej");
		assertTrue(stack.peek().equals("Hej"));
	}
}
