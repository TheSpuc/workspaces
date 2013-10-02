package list2OctoberAss3;

import java.util.NoSuchElementException;

public class MainTest {
	
	public static void main(String[] args){
		PriorityQueue<String> pque = new PriorityQueue<>();
		
		System.out.println("Empty? "  + pque.isEmpty());
		pque.insert(0, "Mark");
		System.out.println("Empty? " + pque.isEmpty());
		System.out.println("Get min: " + pque.min());
		System.out.println("Remove min: " + pque.removeMin());
		
		
		pque.insert(6, "Hans");
		pque.insert(3, "Ib");
		pque.insert(6, "Jens");
		pque.insert(4, "Mads");
		pque.insert(6, "Lene");
		pque.insert(2, "Finn");
		System.out.println(pque);
		System.out.println(pque.size());
		System.out.println(pque.removeMin());
		System.out.println(pque);
		System.out.println(pque.removeMin());
		System.out.println(pque.removeMin());
		System.out.println(pque.removeMin());
		System.out.println(pque.removeMin());
		System.out.println(pque.isEmpty());
		System.out.println(pque.removeMin());
		System.out.println(pque.isEmpty());
		try {
			System.out.println(pque.removeMin());
		} catch (NoSuchElementException e) {
			System.out.print("Got it.");
		}
		try {
			System.out.println(pque.min());
		} catch (NoSuchElementException e) {
			System.out.print("... Got it again.");
		}
		System.out.println(", size: " + pque.size());
		pque.insert(-1, "neg kasper");
		pque.insert(500, "stor kasper");
		System.out.println(pque);

	}
}
