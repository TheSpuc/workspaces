import java.util.List;


public class MergeThread extends Thread {

	private FletteSortering sort;
	private List<Integer> list;
	private int low, high;
	
	
	public MergeThread(FletteSortering sort, List<Integer> list, int low, int high){
		super();
		this.sort = sort;
		this.list = list;
		this.low = low;
		this.high = high;
	}
	
	public void run(){
		sort.mergesort(list, low, high);
	}
}
