package stackCode;

public class Opg19Eks4 {
	
	public static void main(String[] args){
		int[] a = new int[5];
		
		a[0] = 7;
		a[1] = 6;
		a[2] = 11;
		a[3] = 2;
		a[4] = 3;
		
		int b = 0;
		int c = 1;
		
		while(c < 5){
			b = b + a[c];
			c += 1;
		}
	}
}
