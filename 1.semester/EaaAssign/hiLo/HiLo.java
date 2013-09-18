package hiLo;

import java.util.InputMismatchException;
import java.util.Scanner;

public class HiLo {

	private Scanner sc;
	private int haveToGuess;
	private int userGuess;
	private int count;

	public HiLo(){
		sc = new Scanner(System.in);
		haveToGuess = (int) (Math.random()*100)+1;
	}

	private void printWelcome(){
		System.out.println("Welcome to the HiLo program");
		System.out.println("Please try to guess the psuedoRandom number I have created");
		System.out.println("if you input 0 or find the number I created, then the program will quit");
	}

	private void printBye(){
		if(userGuess != haveToGuess){
			System.out.println("You failed the test!");
			System.out.println("I will now exit!!");
		}else{
			System.out.println("Fancy you guessed it, you used " + count + ". tries");
			System.out.println("I will now give you a new number to guess, lets see if you can do better!");
		}
	}

	public void game(){
		this.printWelcome();
		count = 1;
		while(userGuess != haveToGuess){
			System.out.println("Please input the " + count + ". guess:");
			try{
				userGuess = sc.nextInt();
			}
			catch(InputMismatchException e){
				System.out.println("Please input only numbers! Monkey!");
				sc.next();
				continue;
			}
			count++;
			if(userGuess == 0){
				break;
			}
			if(userGuess > haveToGuess){
				System.out.println("big big big! Monkey!");
			}else if (userGuess < haveToGuess){
				System.out.println("low low low! Monkey!");
			}else if(userGuess == haveToGuess){
				this.printBye();
				haveToGuess = (int) (Math.random()*100)+1;
				this.game();
			}
		}
		this.printBye();
	}
}
