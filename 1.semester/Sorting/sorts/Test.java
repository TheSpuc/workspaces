package sorts;

import java.util.ArrayList;

public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ArrayList<Kunde> list = new ArrayList<>();
		
		Kunde k1 = new Kunde("AA", "moo", 0);
		Kunde k2 = new Kunde("BB", "moo", 0);
		Kunde k3 = new Kunde("CC", "moo", 0);
		Kunde k4 = new Kunde("EE", "moo", 0);
		Kunde k5 = new Kunde("DD", "moo", 0);

		list.add(k1);
		list.add(k2);
		list.add(k3);
		list.add(k4);
		list.add(k5);
		
		System.out.println(list);
		
		Kunde k6 = new Kunde("B", "moo", 0);
		
		Sorting.<Kunde>insertArrayList(list, k6);
		System.out.println(list);
		
		Kunde[] kundeArray = {new Kunde("AA", "moo", 0), new Kunde("BB", "moo", 0), new Kunde("CC", "moo", 0), new Kunde("EE", "moo", 0), new Kunde("DD", "moo", 0), null};
		
		for(Kunde k: kundeArray){
			System.out.print(k + ", ");
		}
		System.out.println();
		
		Sorting.<Kunde>insertArray(kundeArray, k6);
		
		for(Kunde k: kundeArray){
			System.out.print(k + ", ");
		}
		System.out.println("\n");
		
		System.out.println("Assign 3:\n");
		ArrayList<Kunde> list1 = new ArrayList<>();
		Kunde k11 = new Kunde("AA", "moo", 0);
		Kunde k22 = new Kunde("BB", "moo", 0);
		Kunde k33 = new Kunde("CC", "moo", 0);
		Kunde k44 = new Kunde("EE", "moo", 0);
		Kunde k55 = new Kunde("DD", "moo", 0);
		list1.add(k11);
		list1.add(k22);
		list1.add(k33);
		list1.add(k44);
		list1.add(k55);
		
		ArrayList<Kunde> list2 = new ArrayList<>();
		Kunde k111 = new Kunde("AA", "moo", 0);
		Kunde k222 = new Kunde("BB", "moo", 0);
		Kunde k333 = new Kunde("CC", "moo", 0);
		Kunde k444 = new Kunde("EE", "moo", 0);
		Kunde k555 = new Kunde("DD", "moo", 0);
		Kunde k666 = new Kunde("A", "moo", 0);
		Kunde k777 = new Kunde("bb", "moo", 0);
		Kunde k888 = new Kunde("cc", "moo", 0);
		list2.add(k666);
		list2.add(k777);
		list2.add(k888);
		list2.add(k111);
		list2.add(k222);
		list2.add(k333);
		list2.add(k444);
		list2.add(k555);
		
		
		System.out.println(list1);
		System.out.println(list2);
		System.out.println(Sorting.<Kunde>merge(list1, list2));
		
		System.out.println("Assign 4:");
		int[] a = {1, 5, 6, 7, 9, 12};
		int[] b = {1, 8, 12, 50, 100, 110, 300};
		
		int[] res = Sorting.incommen(a, b);
		for(int i: res){
			System.out.print(i +", ");
		}
		System.out.println();
		
		System.out.println("Assign 5:");
		ArrayList<Kunde> kundeList = new ArrayList<>();
		Kunde ku1 = new Kunde("A", "moo", 0);
		kundeList.add(ku1);
		Kunde ku2 = new Kunde("B", "moo", 0);
		kundeList.add(ku2);
		Kunde ku3 = new Kunde("C", "moo", 0);
		kundeList.add(ku3);
		Kunde ku4 = new Kunde("D", "moo", 0);
		kundeList.add(ku4);
		Kunde ku5 = new Kunde("E", "moo", 0);
		kundeList.add(ku5);
		Kunde ku6 = new Kunde("F", "moo", 0);
		kundeList.add(ku6);
		
		Kunde[] kundeArr = {new Kunde("A", "moo", 0), new Kunde("B", "moo", 0), new Kunde("C", "moo", 0)};
		
		System.out.println(Sorting.<Kunde>good(kundeList, kundeArr));
	}

}
