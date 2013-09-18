package searchAssign;

import java.util.ArrayList;
import java.util.Collections;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		int[] oddList ={2,4,6,8,1,4,8,6};
		
		System.out.println("odd number in int array: " + Search.oddNumber(oddList));
		
		int[] intervalList ={7,56,34,3,7,14,13,4};
		
		System.out.println("target interval from 10>=N<= 15: " + Search.intervalNumber(intervalList));
		
		int[] twoNumbersWrong ={7,9,13,7,9,13};
		System.out.println("Two numbers wrong: " + Search.twoNumbers(twoNumbersWrong));
		
		int[] twoNumbersRight ={7,9,13,13,9,7};
		System.out.println("Two numbers right: " + Search.twoNumbers(twoNumbersRight));
		
		ArrayList<Player> players = new ArrayList<>();
		Player p1 = new Player("Tommy", 130, 60, 100);
		Player p2 = new Player("Michael", 180, 40, 10);
		Player p3 = new Player("Stefan", 150, 80, 5);
		Player p4 = new Player("Bjoernen Henrik", 120, 130, 300);
		players.add(p1);
		players.add(p2);
		players.add(p3);
		players.add(p4);
		
		System.out.println("Linear goal search: " + Search.findScoreLinear(players, 300));
		System.out.println("Binary goal search: " + Search.findScoreBinary(players, 10));
		
		
		System.out.println("Iterator good player: " + Search.findGoodPlayerIterator(players));
		System.out.println("Best player: " + Search.findGoodPlayer(players));
		
		System.out.println("Squared Linear: " + Search.highestSquareRootLinear(65536));
		System.out.println("Squared Binary: " + Search.highestSquareRootBinary(65536));
		
		ArrayList<Integer> list = new ArrayList<>();
		list.add(2);
		list.add(234);
		list.add(5);
		list.add(15);
		list.add(64);
		list.add(97);
		list.add(300);
		list.add(78);
		
		System.out.println("Find 300: " + Search.find(list, 300));
		Collections.sort(list);
		System.out.println(list);
		System.out.println("Find specific element: " + Search.findSpecificElement(list, 1));
		
		
		System.out.println("Search for number of same letters: " + Search.kSame("iiiiiiiiio", 10));
		
		String[] stringArray = {"Martin", "Stephan", "Anders", "Niels", "Kristian", "Thomas", "Dennis"};
		Integer[] intArray = {1,2,3,4,5,6,10,12,456,243};
		
		System.out.println("Minor generic test: " + Search.<Integer>findBiggestComparable(intArray));
		System.out.println("Minor generic test: " + Search.<String>findBiggestComparable(stringArray));
		
		System.out.println("Target linear finding int: " + Search.<Integer>findComparableTargetLinear(intArray, 2));
		System.out.println("Target linear finding String: " + Search.<String>findComparableTargetLinear(stringArray, "Anders"));
		
		String[] sortedStringArray = {"Aber", "Beta", "Charlie", "Delta", "Stine"};
		Integer[] sortedIntArray = {1,2,3,4,5,6,10};
		System.out.println("Target binary finding int: " + Search.<Integer>findComparableTargetBinary(sortedIntArray, 2));
		System.out.println("Target binary finding String: " + Search.<String>findComparableTargetBinary(sortedStringArray, "Stine"));
		
		Customer[] customers = {new Customer("Mark", "Medum", 22), new Customer("Nikolaj", "Moar", 19), new Customer("Xenia", "Zachariassen", 25)};
		System.out.println("customer after Xenia: " + Search.afterXenia(customers));
	}

}
