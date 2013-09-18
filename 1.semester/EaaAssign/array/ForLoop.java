package array;

public class ForLoop {

	public ForLoop(){
		
	}
	
	public int antalLigeTalILoop(int[] array){
		int total = 0;
		for(int i= 0; i<array.length; i++){
			if(array[i]%2 == 0){
				total++;
			}
		}
		return total;
	}
	
	public int antalLigeTal(int[] array){
		int total = 0;
		for(int foo : array){
			if(foo%2==0){
				total++;
			}
		}
		return total;
	}
}
