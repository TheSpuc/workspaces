package chap05.techsupport1;

import java.util.Random;

public class RandomTester {

	private static Random generater;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		generater = new Random();
		System.out.println(randomNumber());
		System.out.println(throwDice());
		System.out.println(getResponse());
		System.out.println(genNumber(10, 12));
	}

	public static int randomNumber(){
		return generater.nextInt();	
	}

	public static int throwDice(){
		return generater.nextInt(6)+1;
	}
	
	public static String getResponse(){
		String[] foo = {"yes", "no", "maybe"};
		return foo[generater.nextInt(foo.length)];
	}
	
	public static int genNumber(int min, int max){
		return generater.nextInt(max - min + 1) + min;
	}
}