package hashsetAn;

import java.util.HashSet;
import java.util.Set;

public class Hashset {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Set<Integer> set = new HashSet<>();
		set.add(34);
		set.add(12);
		set.add(23);
		set.add(45);
		set.add(67);
		set.add(34);
		set.add(98);
		
		System.out.println("First print: " + set);
		set.add(23);
		System.out.println("23 inserted, but it already exist: " + set);
		set.remove(67);
		System.out.println("67 removed: " + set);
		System.out.println("does the set contain 23: " + set.contains(23));
		System.out.println("Size of set: " + set.size());
	}

}
