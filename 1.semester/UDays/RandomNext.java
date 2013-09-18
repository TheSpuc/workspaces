
public class RandomNext {


	private long seed;

	public RandomNext(long seed){
		this.seed = seed;
	}

	public long next(){
		seed *= 36829367;
		seed %= Integer.MAX_VALUE;
		seed = seed | 734482528;
		seed = seed << 25;
		return seed;
	}

	public static void main(String[] args){
		RandomNext r = new RandomNext(1);
		for(int i=0; i<10; i++){
			System.out.println(r.next());
		}
	}
}
