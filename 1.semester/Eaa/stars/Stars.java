package stars;

public class Stars {
	final int MAX_ROWS = 10;

	public void starPicture() {
//		for (int row = 1; row <= MAX_ROWS; row++) {
//			for (int star = 1; star <= row; star++) {
//				System.out.print("*");
//			}
//			System.out.println();
//		}
		
		//10 first
//		for(int i=10; i>=0; i--){
//			for(int k=1; k<=i; k++){
//				System.out.print("*");
//			}
//			System.out.println();
//		}
		
		//1 top
//		for(int i=0; i<10; i++){
//			for(int k=10; k>=i; k--){
//				System.out.print(" ");
//			}
//			for(int j=0; j<=i; j++){
//				System.out.print("*");
//			}
//			System.out.println();
//		}
		
		//10 top
//		for(int i=0; i<10; i++){
//			for(int k=1; k<=i; k++){
//				System.out.print(" ");
//			}
//			for(int j=10; j>i; j--){
//				System.out.print("*");
//			}
//			System.out.println();
//		}
		
		//fancy
		int count = 1;
		for(int i=0; i<5; i++){
			for(int k=4; k>i; k--){
				System.out.print(" ");
			}
			for(int j=1; j<=count; j++){
				System.out.print("*");
			}
			count +=2;
			System.out.println();
		}
		count = 9;
		for(int i=0; i<=5; i++){
			for(int k=0; k<i; k++){
				System.out.print(" ");
			}
			for(int j=0; j<count; j++){
				System.out.print("*");
			}
			count -= 2;
			System.out.println();
		}
	}
}
