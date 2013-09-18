package adt11September;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class RingITest {

	private RingInterface<String> ring;
	@Before
	public void setUp() throws Exception {
		ring = new ConcreteRing<String>();
		ring.add("Jan");
		ring.add("Per");
		ring.add("Hans");
	}

	@Test
	public void testGetCurrentSize() {
		assertEquals(3, ring.getCurrentSize());
		ring.add("Lis");
		assertEquals(4, ring.getCurrentSize());

	}

//	@Test
//	public void testIsFull() {
//		assertFalse(ring.isFull());
//		for (int i = 1; i<=22;i++){
//			ring.add("ny "+ 1);
//		}
//		assertTrue(ring.isFull());
//	}

	@Test
	public void testIsEmpty() {
		assertFalse(ring.isEmpty());
		ring.clear();
		assertTrue(ring.isEmpty());
	}

	@Test
	public void testAdd() {
		assertFalse(ring.contains("Lis"));
		ring.add("Lis");
		assertTrue(ring.contains("Lis"));
	}

//	@Test
//	public void testRemove() {
//		String s = ring.remove();
//		assertFalse(ring.contains(s));
//		assertEquals(2, ring.getCurrentSize());
//	}

	@Test
	public void testRemoveT() {
		assertTrue(ring.contains("Per"));
		ring.remove("Per");
		assertFalse(ring.contains("Per"));
		assertEquals(2, ring.getCurrentSize());
	}

	@Test
	public void testClear() {
		ring.clear();
		assertEquals(0, ring.getCurrentSize());
		assertTrue(ring.isEmpty());
	}

//	@Test
//	public void testGetFrequencyOf() {
//		assertEquals(1, ring.getFrequencyOf("Per"));
//		ring.add("Per");
//		assertEquals(2,  ring.getFrequencyOf("Per"));
//	}

	@Test
	public void testContains() {
		assertTrue(ring.contains("Per"));
		System.out.println(ring.contains("Per"));
		assertFalse(ring.contains("Pia"));
		System.out.println(ring.contains("Pia"));
	}
	
	@Test
	public void testAdvance(){
		assertEquals("Hans", ring.getCurrentItem());
		ring.advance();
		assertEquals("Jan", ring.getCurrentItem());
	}

//	@Test
//	public void testToArray() {
//		Object[] arr = ring.toArray();
//		assertEquals(3,arr.length);
//	}

}
