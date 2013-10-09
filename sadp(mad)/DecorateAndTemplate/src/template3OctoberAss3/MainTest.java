package template3OctoberAss3;

import java.util.ArrayList;
import java.util.List;

public class MainTest {
	
	public static void main(String[] args){
		List<String> list = new ArrayList<>();
		list.add("Holme");
		list.add("Skaade");
		list.add("Viby");
		list.add("Beder");
		list.add("Stautrup");
		list.add("Engdal");
		list.add("Foraeldreskolen");
		list.add("Malling");
		
		SearchPattern<String> search = new LinarySearch<>(list);
		
		System.out.println(search.search("Malling"));
		System.out.println(search.search("Risskov"));
		
		
		String[] array = {"Holme", "Skaade", "Viby", "Beder", "Stautrup", "Engdal", "Foraeldreskolen", "Malling"};
		search = new LinarySearchArray<>(array);
			
		System.out.println(search.search("Malling"));
		System.out.println(search.search("Risskov"));	
	}
} 
