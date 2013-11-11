package dictionary25OctoberAss2;

public class MainTest {
	
	public static void main(String[] args){
		//ArrayDictionary
		ArrayDictionary<String, String> dic = new ArrayDictionary<>();
		dic.put("1", "Nick");
		dic.put("2", "Lea");
		dic.put("3", "Mark");
		dic.put("4", "Lasse");
		
		System.out.println(dic.size());
		System.out.println(dic.remove("1"));
		System.out.println(dic.size());
		
		System.out.println(dic.size());
		System.out.println(dic.remove("1"));
		System.out.println(dic.size());
		
		System.out.println("\nLinked from here\n");
		
		LinkedDictionary<String, String> dic2 = new LinkedDictionary<>();
		dic2.put("1", "Nick");
		dic2.put("2", "Lea");
		dic2.put("3", "Mark");
		dic2.put("4", "Lasse");
		
		System.out.println(dic2.size());
		System.out.println(dic2.remove("1"));
		System.out.println(dic2.size());
	}
}
