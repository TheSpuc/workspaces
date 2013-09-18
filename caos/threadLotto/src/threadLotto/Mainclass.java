package threadLotto;

public class mainclass {

	public static void main(String[] args) throws InterruptedException {
		lottoraek[] spillede = new lottoraek[800000];
		lottoraek rigtig = new lottoraek();

		for (int j=0; j < spillede.length; j++){
			spillede[j] = new lottoraek();
		}

		int[] antalgev = new int[8];

		long l1,l2;

		l1 = System.nanoTime();
		Thread[] threads = new Thread[4];
		int start = 0;
		int end = spillede.length/4-1;
		for(int i=0; i<4; i++){
			threads[i] = new ThreadLotto(spillede, rigtig, antalgev, start, end);
			threads[i].start();
			start = end+1;
			end = start+(spillede.length/4-1);
		}
		for(Thread t : threads){
			t.join();
		}
		l2 = System.nanoTime();

		System.out.println("Koeretiden var " +(l2-l1)/1000000 + " millisekunder");
		int all = 0;
		for (int i=0;i<8;i++){
			System.out.println("Der var " + antalgev[i] + " raekker med " + i + " rigtige" );
			all += antalgev[i];
		}
		System.out.println(all);
	}

}
