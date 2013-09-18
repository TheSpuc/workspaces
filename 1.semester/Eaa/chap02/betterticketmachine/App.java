package chap02.betterticketmachine;

public class App {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		TicketMachine t = new TicketMachine(100);
		System.out.println(t.getPrice());
		t.insertMoney(100);
		t.printTicket();
		
	}

}
