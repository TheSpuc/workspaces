package arrayMetoder;

public class ArrayMethods {

	public ArrayMethods(){

	}

	public int sum(int[] t){
		int sum = 0;
		for(int in : t){
			sum += in;
		}
		return sum;
	}

	public double sum(double[] t){
		double sum = 0;
		for(double dou : t){
			sum += dou;
		}
		return sum;
	}

	public int[] createSum(int[] a, int[] b){
		int[] result = new int[a.length];
		for(int i=0; i<a.length; i++){
			result[i] = a[i] + b[i];
		}
		return result;
	}

	public boolean hasOdd(int[] a){
		boolean foundOdd = false;
		int i = 0;
		while(i<a.length && !foundOdd){
			if(a[i]%2 != 0){
				foundOdd = true;
			}
			i++;
		}
		return foundOdd;
	}

	public void printArray(int[] a){
		for(int in : a){
			System.out.print(in + ", ");
		}
		System.out.println();
	}
}
