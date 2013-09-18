package generic4September2;

import java.util.HashSet;
import java.util.Set;


public class SetOperations {

	public static void main(String[] args){
		Set<Integer> set1 = new HashSet<>();
		
		set1.add(2);
		set1.add(4);
		set1.add(6);
		
		Set<Integer> set2 = new HashSet<>();
		
		set2.add(1);
		set2.add(3);
		set2.add(6);
		
		System.out.println(set1);
		System.out.println(set2 + "\n");
		
		System.out.println(mergeSets(set1, set2) + "\n");
		System.out.println(inCommon(set1, set2) + "\n");
		System.out.println(difference(set1, set2) + "\n");
		
		
	}
	
	private static<E> Set<E> mergeSets(Set<E> set, Set<E> other){
		Set<E> result = new HashSet<>(set);
		result.addAll(other);
		return result;
	}
	
	private static<E> Set<E> inCommon(Set<E> set, Set<E> other){
		Set<E> result = new HashSet<>();
		for(E e : set){
			if(other.contains(e)){
				result.add(e);
			}
		}
		return result;
	}
	
	public static<E> Set<E> difference(Set<E> set, Set<E> other){
		Set<E> result = new HashSet<>(set);
		result.removeAll(other);
		return result;
	}
}
