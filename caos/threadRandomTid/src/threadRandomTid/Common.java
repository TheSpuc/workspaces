package threadRandomTid;

public class Common {

	private int tal;
	
	public Common(int tal){
		this.tal = tal;
	}
	
	public void TagerRandomTid(int max){
		for(int i=0; i<max; i++){
			for(int j=i; j<max; j++){
				double a = Math.random();
				double b = Math.random();
				a+=b;
			}
		}
	}
	
	public int getTal(){
		return tal;
	}
	
	public void opdaterTal(){
		int temp;
		temp = tal;
		TagerRandomTid(100);
		tal = temp+1;
	}

}
