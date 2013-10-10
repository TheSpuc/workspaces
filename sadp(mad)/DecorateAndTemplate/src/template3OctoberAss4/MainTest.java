package template3OctoberAss4;

import java.util.Iterator;

public class MainTest {

	public static void main(String[] args){
		Sekvens<Hold> s1 = new ListSekvens<>();
		Hold h1 = new Hold(Hold.Ugedag.TIRSDAG, "basketball");
		s1.add(h1);
		Hold h2 = new Hold(Hold.Ugedag.TIRSDAG, "volleybold");
		s1.add(h2);
		Hold h3 = new Hold(Hold.Ugedag.TORSDAG, "fodbold");
		s1.add(h3);
		Hold h4 = new Hold(Hold.Ugedag.TORSDAG, "H�ndbold");
		s1.add(h4);

		Iterator<Hold> it1 = s1.iterator();
		while(it1.hasNext()){
			System.out.println(it1.next());
		}

		System.out.println("\njust in the middle of everything\n");

		Sekvens<Hold> s2 = new ListSekvens<>();
		Hold h5 = new Hold(Hold.Ugedag.TIRSDAG, "basketball");
		s2.add(h5);
		Hold h6 = new Hold(Hold.Ugedag.ONSDAG, "floorball");
		s2.add(h6);
		Hold h7 = new Hold(Hold.Ugedag.TORSDAG, "fodbold");
		s2.add(h7);
		Iterator<Hold> it2 = s2.iterator();
		while(it2.hasNext()){
			System.out.println(it2.next());
		}

		System.out.println("\njust in the end of everything\n");

		
		//Ass 4.3
//		FindAllHold<Hold> ft = new FindAllHold<>(s1, s2);
//		Sekvens<Hold> result = ft.findAll();
//		Iterator<Hold> it3 = result.iterator();
//		while(it3.hasNext()){
//			System.out.println(it3.next());
//		}
		
		
		System.out.println("\njust in the end of everything again?!\n");
		
//		//Ass 4.4
		MergeAllHold<Hold> ft1 = new MergeAllHold<>(s1, s2);
		Sekvens<Hold>	result1 = ft1.MergeAll();
		Iterator<Hold> it4 = result1.iterator();
		while(it4.hasNext()){
			System.out.println(it4.next());
		}
	}
}
