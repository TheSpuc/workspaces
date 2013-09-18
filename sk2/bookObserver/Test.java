
public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Seller s1 = new Seller("Hansen");
		Buyer b1 = new Buyer("Jensen");


		BookTitle bt1 = new BookTitle("Anders And", 6);
		BookTitle bt2 = new BookTitle("Java", 8);

		Customer c1 = new Customer("ReadHouse1");
		Customer c2 = new Customer("ReadHouse2");
		Customer c3 = new Customer("ReadHouse3");

		bt1.addObserver(s1); //Anders and for the seller

		//Observer for the buyer
		bt1.addObserver(b1); 
		bt2.addObserver(b1);

		//Java
		bt2.buy(c1);
		bt2.buy(c2);
		bt2.buy(c3);

		//Anders And
		bt1.buy(c1);
		bt1.buy(c2);
		bt1.buy(c3);
	}
}
