package bookexObserver;

public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Seller s1 = new Seller("Hansen");
		Buyer b1 = new Buyer("Jensen");


		BookTitle bk1 = new BookTitle("Anders And", 6);
		BookTitle bk2 = new BookTitle("Java", 8);

		Customer c1 = new Customer("ReadHouse1");
		Customer c2 = new Customer("ReadHouse2");
		Customer c3 = new Customer("ReadHouse3");

		bk1.addObserver(s1); //Anders and for the seller

		//Observer for the buyer
		bk1.addObserver(b1); 
		bk2.addObserver(b1);

		//Java
		bk2.buy(c1);
		bk2.buy(c2);
		bk2.buy(c3);

		//Anders And
		bk1.buy(c1);
		bk1.buy(c2);
		bk1.buy(c3);
	}
}
