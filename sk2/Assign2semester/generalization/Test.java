package generalization;

import java.util.ArrayList;
import java.util.List;

public class Test {
	
	public static void main(String[] args){
		List<Ansat> list = new ArrayList<>();
		Mekaniker m1 = new Mekaniker("Henrik", "moo", "1990", 100);
		Mekaniker m2 = new Mekaniker("Mark", "moo", "moo", 200);
		Vaerkfoerer v1 = new Vaerkfoerer("Vicki", "moo", "moo", 100, "1990", 50);
		Synsmand s1 = new Synsmand("Vicki", "moo", "fh", 100, 2);
		Arbejdsdreng a1 = new Arbejdsdreng("Mark", "moo", 100);
		list.add(m1);
		list.add(m2);
		list.add(v1);
		list.add(s1);
		list.add(a1);
		System.out.println(samletLoen(list));
	}
	
	public static double samletLoen(List<Ansat> list){
		double result = 0;
		for(Ansat a : list){
			result += a.beregnLoen();
		}
		return result;
	}
}
