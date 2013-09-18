import java.util.ArrayList;


public class Test {

	public static void main(String[] args){
		ArrayList<Hund> list = new ArrayList<>();
		Hund h1 = new Hund("Abe", 100, false, Race.BOXER);
		Hund h2 = new Hund("Abe", 100, false, Race.TERRIER);
		Hund h3 = new Hund("Abe", 100, false, Race.PUDDEL);
		Hund h4 = new Hund("Abe", 100, false, Race.PUDDEL);
		Hund h5 = new Hund("Abe", 100, false, Race.TERRIER);
		Hund h6 = new Hund("Abe", 100, false, Race.BOXER);
		list.add(h1);
		list.add(h2);
		list.add(h3);
		list.add(h4);
		list.add(h5);
		list.add(h6);
		
		System.out.println(samletPris(list, Race.BOXER));
	}
	
	public static int samletPris(ArrayList<Hund> list, Race race){
		int result = 0;
		for(Hund hund : list){
			if(hund.getRace().equals(race)){
				result += hund.getPris();
			}
		}
		return result;
	}
}
