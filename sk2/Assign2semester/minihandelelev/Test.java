package minihandelelev;

public class Test {

	public static void main(String[] args) {
		Vare v1 = new Vare(1, "soem", 0.25);
		Vare v2 = new Vare(2, "skruer", 0.50);
		Vare v3 = new Vare(3, "moetrik", 1.00);
		Vare v4 = new Vare(4, "hammer", 53.75);
		Vare v5 = new Vare(5, "sav", 256.50);
		
		Rabat r1 = new ProcentRabat(15);
		Rabat r2 = new FastRabat(20, 100);
		
		Kunde k1 = new Kunde("Jane Jensen", specification.DateUtil.createDate("1964-05-23"), r1);
		Kunde k2 = new Kunde("Hans Kirk", specification.DateUtil.createDate("1954-12-12"), r2);
		
//		Jane Jensen afgiver 2 ordrer
//		•	8 søm og 1 hammer
//		•	2 save og 100 skruer
		
		Ordre o1 = new Ordre(1, k1);
		o1.createOrdreLinie(1, 8, v1);
		o1.createOrdreLinie(2, 1, v4);
		
		Ordre o2 = new Ordre(2, k1);
		o2.createOrdreLinie(3, 2, v5);
		o2.createOrdreLinie(4, 100, v2);

//	Hans Kirk afgiver 4 ordrer
//		•	10 møtrikker, 1 hammer og 1 sav
//		•	50 skruer og 1000 søm
//		•	10 skruer og 5 møtrikker
//		•	1 sav 
		
		Ordre o3 = new Ordre(3, k2);
		o3.createOrdreLinie(5, 10, v3);
		o3.createOrdreLinie(6, 1, v4);
		o3.createOrdreLinie(7, 1, v5);
		
		Ordre o4 = new Ordre(4, k2);
		o4.createOrdreLinie(8, 50, v2);
		o4.createOrdreLinie(9, 1000, v1);
		
		Ordre o5 = new Ordre(5, k2);
		o5.createOrdreLinie(10, 10, v2);
		o5.createOrdreLinie(11, 5, v3);
		
		Ordre o6 = new Ordre(6, k2);
		o6.createOrdreLinie(12, 1, v5);
		
		System.out.println(k1.samletKoeb());
		System.out.println(k2.samletKoeb());
	}

}
