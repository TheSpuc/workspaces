import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * Class which describe a Rouletwheel 
 * @author MMB and Esben Dall
 *
 */
public class RouletteResult {

	private Random r;
	private List<Bet> bets;
	private int spin;
	private final List<Integer> red;
	private final List<Integer> black;
	
	/**
	 * Constructor
	 */
	public RouletteResult(){
		r = new Random();
		spin = -1;
		bets = new ArrayList<>();
		
		//red and black numbers split in each own list
		Integer[] redNumbers = {1, 3, 5, 7, 9, 12, 14, 16, 18, 19, 21, 23, 25, 27, 30, 32, 34, 36};
		red = Arrays.asList(redNumbers);
		Integer[] blackNumbers = {2, 4, 6, 8, 10, 11, 13, 15, 17, 20, 22, 24, 26, 28, 29, 31, 33, 35};
		black = Arrays.asList(blackNumbers);
		
	}
	
	/**
	 * Method for betting on one specific number
	 * @param nrOfChips placed on bettingNr
	 * @param bettingNr number betting on
	 */
	public void betOne(int nrOfChips, int bettingNr){
		bets.add(new Bet(nrOfChips, Arrays.asList(bettingNr)));
	}
	
	/**
	 * Method for betting on all even numbers
	 * @param nrOfChips placed on even
	 */
	public void betEven(int nrOfChips){
		List<Integer> result = new ArrayList<>();
		for(int i = 2; i<37; i+=2){
			result.add(i);
		}
		bets.add(new Bet(nrOfChips, result));
	}
	
	/**
	 * Method for betting on all odd numbers
	 * @param nrOfChips placed on odd
	 */
	public void betOdd(int nrOfChips){
		List<Integer> result = new ArrayList<>();
		for(int i = 1; i<37; i+=2){
			result.add(i);
		}
		bets.add(new Bet(nrOfChips, result));
	}
	
	/**
	 * Method for betting on low[1-18] or high[19-36]
	 */
	public void betLowHigh(boolean low, int nrOfChips){
		int start = -1;
		if(low){
			start = 1;
		} else start = 19;
		List<Integer> result = new ArrayList<>();
		for(int i = start; i <= start+17; i++){
			result.add(i);
		}
		bets.add(new Bet(nrOfChips, result));
	}
	
	/**
	 * Method for betting on four adjoining numbers
	 * @param upperLeftCorner number to start on
	 * @param nrOfChips placed on four numbers
	 */
	public void corner(int upperLeftCorner, int nrOfChips){
		if(upperLeftCorner%3 == 0 || upperLeftCorner > 33){
			throw new RuntimeException("Henrik 42!");
		}
		List<Integer> result = new ArrayList<>();
		result.add(upperLeftCorner);
		result.add(upperLeftCorner+1);
		result.add(upperLeftCorner+3);
		result.add(upperLeftCorner+4);
		bets.add(new Bet(nrOfChips, result));
	}
	
	/**
	 * Method for betting on three numbers horizontal
	 * @param leftNumber number to start on
	 * @param nrOfChips placed on three numbers
	 */
	public void street(int leftNumber, int nrOfChips){
		if(leftNumber%3 != 1){
			throw new RuntimeException("Henrik 42!");
		}
		List<Integer> result = new ArrayList<>();
		result.add(leftNumber);
		result.add(leftNumber+1);
		result.add(leftNumber+2);
		bets.add(new Bet(nrOfChips, result));
	}
	
	/**
	 * Method for betting on all red numbers
	 * @param nrOfChips placed on all red numbers
	 */
	public void red(int nrOfChips){
		bets.add(new Bet(nrOfChips, red));
	}
	
	/**
	 * Method for betting on all black numbers
	 * @param nrOfChips placed on all black numbers
	 */
	public void black(int nrOfChips){
		bets.add(new Bet(nrOfChips, black));
	}
	
	/**
	 * Method for betting on a dozen of numbers.
	 * @param chosenDozen 1 = [1-12] , 2 = [13-24] , 3 = [25-36]
	 * @param nrOfChips placed on chosen dozen
	 */
	public void dozen(int chosenDozen, int nrOfChips){
		if(chosenDozen < 1 || chosenDozen > 3){
			throw new RuntimeException("Henrik 42!");
		}
		
		int start = -1;
		if(chosenDozen == 1){
			start = 1;
		}else if(chosenDozen == 2){
			start = 13;
		}else start = 25;
		
		List<Integer> result = new ArrayList<>();
		for(int i=start; i<= start+11; i++){
			result.add(i);
		}
		bets.add(new Bet(nrOfChips, result));
	}
	
	/**
	 * Method for spinning the wheel
	 * based on a pseudo random.
	 * @return nr between 0 and 36
	 */
	public int spinTheWheel(){
		spin = r.nextInt(37);
		return spin;
	}
	
	/**
	 * Method for calculating the payment
	 * based on the betting information
	 * @return the pay out
	 */
	public int payout(){
		int result = 0;
		for(Bet b : bets){
			List<Integer> bettingList = b.getList();
			if(bettingList.contains(spin)){
				result += (36/bettingList.size()) * b.getNrOfChips();
			}
		}
		bets.clear();
		return result;
	}
	
	/**
	 * Class for defining a bet
	 * @author MMB and Esben Dall
	 *
	 */
	private class Bet{
		private int nrOfChips;
		private List<Integer> list;
		
		/**
		 * Constructor
		 * @param nrOfChips to bet
		 * @param list what is being bet on
		 */
		public Bet(int nrOfChips, List<Integer> list){
			this.nrOfChips = nrOfChips;
			this.list = list;
		}
		
		/**
		 * Get the number of chips betted
		 * @return nr of chips betted
		 */
		public int getNrOfChips(){
			return nrOfChips;
		}
		
		/**
		 * Get what has been betted on
		 * @return get whats has been betted on
		 */
		public List<Integer> getList(){
			return new ArrayList<>(list); 
		}
	}
	
}
