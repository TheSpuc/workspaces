
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TestFlettesortering {

	/**
	 * @param args
	 * @throws InterruptedException 
	 */
	public static void main(String[] args) throws InterruptedException {
		List<Integer> list = new ArrayList<Integer>();
		Random r=new Random();
		for (int i=0;i<1000000;i++) {
			list.add(Math.abs(r.nextInt()%10000));
		};

//		System.out.println(list);

		FletteSortering sort = new FletteSortering();

		int mid = (0 + list.size()-1) / 2;
		MergeThread mtlo = new MergeThread(sort, list, 0, mid);
		MergeThread mthi = new MergeThread(sort, list, mid+1, list.size()-1);


		long l1,l2;
		l1 = System.nanoTime();
		mtlo.start();
		mthi.start();
		mtlo.join();
		mthi.join();
		sort.merge(list, 0, mid, list.size()-1);
		l2 = System.nanoTime();
		System.out.println();
		System.out.println("Køretiden var " + (l2-l1)/1000000);
		System.out.println();
//		System.out.println(list);
	}

}
