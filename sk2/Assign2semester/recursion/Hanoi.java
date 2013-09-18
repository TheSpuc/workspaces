package recursion;

public class Hanoi {

	public static void flyt(int n, int fra, int til){
		
		if (n==1) {
			System.out.println("Flyt fra " +fra +" til " + til);	
		}
		else {
			flyt(n-1,fra,6-fra-til);
			System.out.println("Flyt fra " + fra +" til " + til);
			flyt(n-1,6-fra-til,til);
		}
	}
	public static void main(String[] args) {
		flyt(6,1,3);
	}
}
