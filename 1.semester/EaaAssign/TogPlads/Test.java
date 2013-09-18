package TogPlads;

import java.util.ArrayList;
import java.util.Collections;

public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		ArrayList<Tog> list = new ArrayList<>();
		
		Tog t1 = new Tog("Aarhus-Viby", 100, 10);
		
		System.out.println(t1.ikkeReserveret());
		
		t1.setReserveret(1, true);
		
		System.out.println(t1.ikkeReserveret());
		
		System.out.println("FCK fans har smadret en del stole");
		
		t1.deletePlads(10);
		
		//System.out.println(t1.isReserveret(10));
		
		Tog t2 = new Tog("aarhus-kbh", 1, 100);
		list.add(t2);
		Tog t3 = new Tog("aarhus-kbh", 5, 100);
		list.add(t3);
		Tog t4 = new Tog("aalborg-ringkoebing", 26, 100);
		list.add(t4);
		Tog t5 = new Tog("lem-tarm", 78, 100);
		list.add(t5);
		Tog t6 = new Tog("skjern-aarhus", 10, 100);
		list.add(t6);
		
		System.out.println("Unsorted " + list);
		
		Collections.sort(list);
		System.out.println("Sorted: " + list);
	}
}
