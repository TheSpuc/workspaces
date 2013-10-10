package template3OctoberAss4;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ListSekvens<E> implements Sekvens<E> {

	private List<E> list;
	
	public ListSekvens(){
		list = new ArrayList<E>();
	}
	public void add(E obj) {
		list.add(obj);

	}


	public void addAll(Sekvens<E> s) {
		for (Iterator<E> iterator = s.iterator(); iterator.hasNext();) {
			E object = iterator.next();
			list.add(object);
			
		}

	}


	public E head() {
		return list.get(0);
	}

	
	public boolean isEmpty() {
		return list.isEmpty();
	}


	public Iterator<E> iterator() {
		return list.iterator();
	}

	
	public void tail() {
		list.remove(0);

	}

}
