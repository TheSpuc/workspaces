package template3OctoberAss4;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ListSekvens implements Sekvens {

	private List<Comparable> list;
	
	public ListSekvens(){
		list = new ArrayList<Comparable>();
	}
	public void add(Comparable obj) {
		list.add(obj);

	}


	public void addAll(Sekvens s) {
		for (Iterator iterator = s.iterator(); iterator.hasNext();) {
			Comparable object = (Comparable) iterator.next();
			list.add(object);
			
		}

	}


	public Comparable head() {
		return list.get(0);
	}

	
	public boolean isEmpty() {
		return list.isEmpty();
	}


	public Iterator iterator() {
		return list.iterator();
	}

	
	public void tail() {
		list.remove(0);

	}

}
