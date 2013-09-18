package whileLoops;

public class BigSum {

	public static void main(String[] args) {
		
		int i = 0;
		int result = 0;
		while(i<10){
			int r = 0;
			while(r<=10){
				result +=r;
				r++;
			}
		i++;
		}
		System.out.println(result);
	}

}
