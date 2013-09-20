package nodes12September;

public class MainTest {
	
	public static void main(String[] args){
		
		String s1 = "Jan";
		String s2 = "Per";
		String s3 = "Hans";
		
		LinkedList<String> list = new LinkedList<>();
		
//		list.printElements();
		
		list.addElement(s1);
		list.addElement(s2);
		list.addElement(s3);
		
//		list.printElements();
		
//		list.removeElement("Jan");
//		list.removeElement("Hans");
		
		System.out.println();
		
		LinkedList<String> sortList = new LinkedList<>();
		sortList.addElement("Abe");
		sortList.addElement("Iver");
		sortList.addElement("Ole");
		sortList.addElement("Rand");
		sortList.addElement("Wii");
//		sortList.printElements();
		
		list.addAll(sortList);
		System.out.println();
		list.printElements();
	}
}
