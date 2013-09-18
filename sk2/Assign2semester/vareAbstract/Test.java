package vareAbstract;

import vareAbstract.Vare;

public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Foedevare f1 = new Foedevare(10, "Agurk", "Groen frugt", 2);
		ElArtikel e1 = new ElArtikel(20, "Mikroovn", "Ovn", 5);
		Spiritus s1 = new Spiritus(10, "Oel", "Oel", 1);
		Vare v1 = new Vare(10, "Hest", "en stor hest");
		
		Kurv k1 = new Kurv();
		k1.addVare(f1);
		k1.addVare(e1);
		k1.addVare(s1);
		k1.addVare(v1);
		
		System.out.println(k1.samletBrutto());
	}

}
