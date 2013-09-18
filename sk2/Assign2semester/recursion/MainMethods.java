package recursion;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class MainMethods {


	public static void main(String[] args) {
		System.out.println("Factor: " + factor(5));
		System.out.println("Power1: " + power(2, 2));
		System.out.println("Power2: " + power2(2, 2));
		System.out.println("Product: " + product(19, 3));
		System.out.println("ProductRus: " + productRus(19, 3));
		System.out.println("Reverse String: " + reverse("RANSLIRPA"));
		System.out.println("Greatest common divisor: " + gcd(10, 8));
		System.out.println("Palindrom: " + palindrom("RACECAR"));
		System.out.println("Binomial: "+ binomial(3, 2));
		System.out.println("Number: " + number(6));
		System.out.println();


		directorylookup(FileSystems.getDefault().getPath("/Users/MMB/Dropbox/12s"));
		System.out.println();
		System.out.println("Recursive number of folders: " +directorylookupInt(FileSystems.getDefault().getPath("/Users/MMB/Dropbox/12s")));
		System.out.println();
		directorylookupLevel(FileSystems.getDefault().getPath("/Users/MMB/Dropbox/12s"), 1);
		System.out.println();


		anagram("KAT", "");
	}

	/**
	 * Factor for n
	 * @param n
	 * @return
	 */
	public static int factor(int n){
		int result = 0;
		if(n == 0){
			result = 1;
		}else result = n*factor(n-1);
		return result;
	}

	/**
	 * Power off
	 */
	public static int power(int n, int p){
		int result = 0;
		if(p == 0){
			result = 1;
		}else result = power(n, p-1)*n;
		return result;
	}

	/**
	 * Power off 2
	 */
	public static int power2(int n, int p){
		int result = 0;
		if(p == 0){
			result = 1;
		}else if (p%2 != 0){
			result = power2(n, p-1)*n;
		}else result = power2((n*n), p/2);
		return result;
	}

	/**
	 * Product
	 */
	public static int product(int a, int b){
		int result = 0;
		if(a == 0){
			result = 0;
		}else if(a == 1){
			result = b;
		}else result = product(a-1, b) + b;
		return result;
	}

	/**
	 * ProductRus
	 */
	public static int productRus(int a, int b){
		int result = 0;
		if(a == 0){
			result = 0;
		}else if(a%2 != 0){
			result = productRus(a-1, b)+b;
		}else result = productRus(a/2, 2*b);
		return result;
	}

	/**
	 * String Reverse, start from the beginning and move that to the end,
	 * thereby recursive call with rest of string, until there is only 1 or 0 left, 
	 * thereby returning s for addition through the rest of the nested calls
	 */
	public static String reverse(String s){
		String result = "";
		if(s.length() <= 1){
			result = s;
		}else result = reverse(s.substring(1)) + s.charAt(0);
		return result;
	}

	/**
	 * Greatest common divisor
	 */
	public static int gcd(int a, int b){
		int result = 0;
		if(b == 0){
			result = a;
		}else result = gcd(b, a%b);
		return result;
	}

	/**
	 * Palindrom
	 * RACECAR - its the same when you read it from right to left.
	 */
	public static boolean palindrom(String s){
		boolean result = false;
		if(s.length() <= 1){
			result = true;
		}else if(s.charAt(0) == s.charAt(s.length()-1)){ //Check whether the first and last char is the same
			result = palindrom(s.substring(1, s.length()-1)); // If it is the same, recursive call with the index 1 and second last index
		}
		return result;
	}

	/**
	 * Binomial, describe all possible ways of combining all numbers from 1 to m,
	 * where n describes how many numbers to combine, eks:
	 * [3, 2] - combine numbers [1, 2, 3] in sets of [2]
	 * [1,2] - [1,3] - [2,3], hereby defining 3 possible combinations.
	 */
	public static int binomial(int n, int m){
		int result = 0;
		if(m == 0){ //if the number of set is [0], binomial coefficient defines the result as 1
			result = 1;
		}else if(n == 0){ //if n is defined as 0, the binomial coefficient defines the result as 0, no matter what combination m is defined 
			result = 0;
		}else result = binomial(n-1, m-1) + binomial(n-1, m); //recursive call, defined as the binomial coefficient calculation
		return result;
	}

	/**
	 * Recusive function for numbers
	 */
	public static int number(int n){
		int result = 0;
		if(n == 0){
			result = 2;
		}else if(n == 1){
			result = 1;
		}else if(n == 2){
			result = 5;
		}else if(n%2 == 0){
			result = 2*number(n-3) - number(n-1);
		}else result = number(n-1) + number(n-2) + 3*number(n-3);
		return result;
	}

	/**
	 * Recursive lookup in folders on hd Java 7
	 */
	public static void directorylookup(Path dir) {
		System.out.println("[DIR]   " + dir); //print out the current Path
		try (DirectoryStream<Path> stream = Files.newDirectoryStream(dir)) { //try with construct based on java 7
			for (Path entry : stream) { //entry represent the next file to look at
				if (Files.isDirectory(entry)) { //static method for checking whether the file is a directory
					directorylookup(entry); //recursive call 
				}
			}
			stream.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Recursive lookup in folders on hd Java 7
	 */
	public static int directorylookupInt(Path dir)  {
		int result = 0;
		try (DirectoryStream<Path> stream = Files.newDirectoryStream(dir)) {
			for (Path entry : stream) {
				if (Files.isDirectory(entry)) {
					result++;
					result += directorylookupInt(entry);
				}
			}
			stream.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * Recursive lookup in folders on hd with level Java 7
	 */
	public static void directorylookupLevel(Path dir, int level) {
		System.out.println(level + ": [DIR]   " + dir);
		try (DirectoryStream<Path> stream = Files.newDirectoryStream(dir)) {
			for (Path entry : stream) {
				if (Files.isDirectory(entry)) {
					directorylookupLevel(entry, (level+1));
				}
			}
			stream.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void anagram(String text, String prefix)  {
		if(text.length() == 0) System.out.println(prefix);
		for(int i = 0; i < text.length(); i++) {
			anagram(text.substring(0, i) + text.substring(i+1, text.length()), prefix + text.charAt(i));
		}
	}
}
