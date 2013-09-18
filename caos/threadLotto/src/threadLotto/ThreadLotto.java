package threadLotto;

public class ThreadLotto extends Thread{
	
	private int start;
	private int end;
	private lottoraek[] spillede;
	private lottoraek rigtig;
	private int[] genvinst;
	
	public ThreadLotto(lottoraek[] spillede, lottoraek rigtig, int[] gevinst, int start, int end){
		super();
		this.start = start;
		this.end = end;
		this.spillede = spillede;
		this.rigtig = rigtig;
		this.genvinst = gevinst;
	}
	
	public void run(){
		tjek(spillede, rigtig, genvinst, start, end);
	}
	
	public static synchronized void tjek(lottoraek[] spillede, lottoraek rigtig, int[] gevinst, int start, int end){
		for (int j=start; j <= end; j++) {
			gevinst[rigtig.antalrigtige(spillede[j])] ++; 
		}
	}
	
}
