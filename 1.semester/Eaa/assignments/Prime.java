package assignments;

import java.util.Scanner;

public class Prime {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		System.out.println(isPrime(sc.nextInt()));
		sc.close();
	}

	public static boolean isPrime(int n){
		boolean foo = true;
		if(n <= 1){
			foo = false;
		}
		if (n%2==0){
			foo = false;
		}
		int count = 3;
		while(count < n){
			if(n%count == 0){
				foo = false;
			}
			count += 2;
		}
		return foo;
	}
}
