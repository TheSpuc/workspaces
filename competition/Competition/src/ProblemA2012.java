import java.util.Scanner;


public class ProblemA2012 {
	
	public static void main(String[] args){
		Scanner sc = new Scanner(System.in);
		
		while(sc.hasNext()){
			String justin = sc.nextLine();
			String dentist = sc.nextLine();
			
			if(justin.length() >= dentist.length()){
				System.out.println("go");
			}else System.out.println("no");
		}
	}
}
