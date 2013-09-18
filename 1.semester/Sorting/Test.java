import java.util.ArrayList;
import java.util.Collections;


public class Test {


	public static void main(String[] args) {
		System.out.println("Start of bubble sort:");
		String[] stringArrayBubble = {"Erna", "Elly", "Laurits", "Bertha", "Christian", 
				"August", "Marius", "John", "Tove", "Poul", };
		Integer[] intArray = {45,23,66,30,88,27,2,34,48};

		System.out.println("Without sorting:");
		for(String s : stringArrayBubble){
			System.out.print(s +", ");
		}
		System.out.println();
		System.out.println("Without sorting:");
		for(int i : intArray){
			System.out.print(i + ", ");
		}

		Sorting.<String>bubbleSort(stringArrayBubble); //String sorting
		Sorting.<Integer>bubbleSort(intArray); //Integer sorting
		System.out.println();
		System.out.println("With sorting bubble sort:"); 
		for(String s : stringArrayBubble){ //sorted printout String
			System.out.print(s +", "); 
		}
		System.out.println();
		System.out.println("With sorting bubble sort:");
		for(int i : intArray){ //sorted printout Integer
			System.out.print(i + ", ");
		}
		System.out.println();
		System.out.println("End of bubble sort");

		
		System.out.println("\n\n");

		
		System.out.println("Start of selection sort:");
		ArrayList<String> stringArrayListSelection = new ArrayList<>();
		stringArrayListSelection.add("Erna");
		stringArrayListSelection.add("Elly");
		stringArrayListSelection.add("Laurits");
		stringArrayListSelection.add("Bertha");
		stringArrayListSelection.add("Christian");
		stringArrayListSelection.add("August");
		stringArrayListSelection.add("Marius");
		stringArrayListSelection.add("John");
		stringArrayListSelection.add("Tove");
		stringArrayListSelection.add("Poul");
		String[] stringArraySelection = {"Erna", "Elly", "Laurits", "Bertha", "Christian", 
				"August", "Marius", "John", "Tove", "Poul", };

		System.out.println("Without sorting:");
		for(String s : stringArraySelection){
			System.out.print(s +", ");
		}
		Sorting.<String>selectionSort(stringArraySelection); //selection sort array
		System.out.println();
		System.out.println("With sorting array selection sort:");
		for(String s : stringArraySelection){ //sorted printout String array
			System.out.print(s +", ");
		}
		System.out.println();
		Sorting.<String>selectionSort(stringArrayListSelection); //selection sort ArrayList
		System.out.println("With sorting ArrayList selection sort:");
		System.out.println(stringArrayListSelection); //sorted printout String ArrayList
		System.out.println("End of selection sort");

		System.out.println("\n\n");

		System.out.println("Start of insertion sort:");
		ArrayList<String> stringArrayListInsertion = new ArrayList<>();
		stringArrayListInsertion.add("Erna");
		stringArrayListInsertion.add("Elly");
		stringArrayListInsertion.add("Laurits");
		stringArrayListInsertion.add("Bertha");
		stringArrayListInsertion.add("Christian");
		stringArrayListInsertion.add("August");
		stringArrayListInsertion.add("Marius");
		stringArrayListInsertion.add("John");
		stringArrayListInsertion.add("Tove");
		stringArrayListInsertion.add("Poul");
		String[] stringArrayInsertion = {"Erna", "Elly", "Laurits", "Bertha", "Christian", 
				"August", "Marius", "John", "Tove", "Poul", };

		System.out.println("Without sorting:");
		for(String s : stringArrayInsertion){
			System.out.print(s +", ");
		}
		Sorting.<String>insertionSort(stringArrayInsertion); //selection sort array
		System.out.println();
		System.out.println("With sorting array selection sort:");
		for(String s : stringArrayInsertion){ //sorted printout String array
			System.out.print(s +", ");
		}
		System.out.println();
		Sorting.<String>insertionSort(stringArrayListInsertion); //selection sort ArrayList
		System.out.println("With sorting ArrayList selection sort:");
		System.out.println(stringArrayListInsertion); //sorted printout String ArrayList
		System.out.println("End of insertion sort");


		System.out.println("\n\n");

		
		System.out.println("Start of kunde comparison:");
		ArrayList<Kunde> list = new ArrayList<>();
		Kunde k1 = new Kunde("Medum", "Mark", 22);
		Kunde k2 = new Kunde("Medum", "Liv", 20);
		Kunde k3 = new Kunde("Mikkelsen", "Nikolaj", 19);
		Kunde k4 = new Kunde("McQuid", "Henrik", 105);
		Kunde k5 = new Kunde("Abemand", "Moo", 5);
		Kunde k6 = new Kunde("AA", "BB", 5);
		Kunde k7 = new Kunde("AAA", "BBB", 22);
		list.add(k1);
		list.add(k2);
		list.add(k3);
		list.add(k4);
		list.add(k5);
		list.add(k6);
		list.add(k7);
		
		System.out.println("Without sorting:");
		System.out.println(list);
		Collections.sort(list);
		System.out.println("With Collection.sort:");
		System.out.println(list);
		System.out.println("End of kunde comparison");
		
		System.out.println("\n\n");
		
		
	}

}
