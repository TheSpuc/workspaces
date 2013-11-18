
package hashing13NovemberAss1;


public class TestDictionary {

	public static void main(String[] args) {
//		Dictionary<Integer, String> map = new MapHashDictionary<Integer,String>();
//		System.out.println(map.isEmpty());
//		System.out.println(map.size());
//		
//		map.put(8,"hans");
//		map.put(3,"viggo");
//		System.out.println(map.isEmpty());
//		System.out.println(map.size());
//		
//		System.out.println(map.get(8));
//
//		map.put(7,"bent");
//		map.put(2,"lene");
//		System.out.println(map.isEmpty());
//		System.out.println(map.size());
//		System.out.println(map.remove(3));
//	
//		System.out.println(map.size());
//		
//		System.out.println(map.put(8,"Ida"));
//		map2.put(9, "stefan");
//		map2.put(10, "mark");
//		map2.put(11, "egon");
//		map2.put(12, "jonas");
//		map2.put(13, "emil");
//		map2.put(14, "christian");
//		map2.put(15, "nick");
		
		Dictionary<Integer, String> map2 = new ArrayListDictionary<>();
		System.out.println(map2.isEmpty());
		System.out.println(map2.size());
		
		map2.put(8,"hans");
		map2.put(3,"viggo");
		System.out.println(map2.isEmpty());
		System.out.println(map2.size());
		
		System.out.println(map2.get(8));

		map2.put(7,"bent");
		map2.put(2,"lene");
		System.out.println(map2.isEmpty());
		System.out.println(map2.size());
		System.out.println(map2.remove(3));
	
		System.out.println(map2.size());
		
		System.out.println(map2.put(8,"Ida"));
		map2.put(9, "stefan");
		map2.put(10, "mark");
		map2.put(11, "egon");
		map2.put(12, "jonas");
		map2.put(13, "emil");
		map2.put(14, "christian");
		map2.put(15, "nick");
	}
}
