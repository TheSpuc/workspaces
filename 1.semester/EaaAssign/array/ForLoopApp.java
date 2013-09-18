package array;

public class ForLoopApp {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		int[] foo = {1,2,3,4,5,6,7,8,9,10,11,13,15,547,3423,567,768,23,12,357,899};
		ForLoop bar = new ForLoop();
		System.out.println("with index: " + bar.antalLigeTalILoop(foo));
		System.out.println("without index: " + bar.antalLigeTal(foo));
		
		System.out.println("from static with index: " + antalLigeTalILoop(foo));
		System.out.println("fron static without index: " + antalLigeTal(foo));
	}
	
	public static int antalLigeTalILoop(int[] array){
		int total = 0;
		for(int i= 0; i<array.length; i++){
			if(array[i]%2 == 0){
				total++;
			}
		}
		return total;
	}
	
	public static int antalLigeTal(int[] array){
		int total = 0;
		for(int foo : array){
			if(foo%2==0){
				total++;
			}
		}
		return total;
	}
}
