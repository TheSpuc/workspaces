package arrayMetoder;

public class TestArray {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		int[] foo = {1,2,3,4,5,10};
		double[] bar = {1.2, 2.5, 3.6, 2.3, 6.43};
		ArrayMethods argmoo = new ArrayMethods();
		
		System.out.println("int: " + argmoo.sum(foo));
		System.out.println("double: " + argmoo.sum(bar));
		
		int[] moo = {1,2,3,4,5};
		int[] pony = {1,2,3,4,5};
		
		
		argmoo.printArray(argmoo.createSum(moo, pony));
		
		System.out.println(argmoo.hasOdd(foo));

	}

}
