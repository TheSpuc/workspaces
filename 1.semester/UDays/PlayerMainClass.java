
public class PlayerMainClass {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		RouletteResult r = new RouletteResult();
		
		int winnings = 0;
		for(int i=1; i<=1000000; i++){
			r.betOne(1, 1);
			r.spinTheWheel();
			winnings += r.payout();
		}
		
		System.out.println(winnings);
		
		int systemWinning = 0;
		int moneyOutWindow = 0;
		int currentBet = 1;
		for(int s=1; s<=1000000; s++){
			r.betEven(currentBet);
			moneyOutWindow += currentBet;
			r.spinTheWheel();
			int winwin = r.payout();
			if(winwin == 0){
				currentBet *= 2;
				if(currentBet > 1000){
					currentBet /= 2;
				}
			}else {
				systemWinning += winwin;
				currentBet = 1;
			}
		}
		
		System.out.println("WinWin: " + systemWinning + " ::::: LoseLose: " + moneyOutWindow);
	}

}
