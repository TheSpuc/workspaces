package assign119September;

import java.util.ArrayList;
import java.util.List;

public class ListStack<T> implements StackI<T> {
	
	private List<T> list;
	
	public ListStack(){
		list = new ArrayList<>();
	}
	
	@Override
	public void push(T newEntry) {
		list.add(newEntry);
	}

	@Override
	public T pop() {
		return list.remove(list.size()-1);
	}

	@Override
	public T peek() {
		return list.get(list.size()-1);
	}

	@Override
	public boolean isEmpty() {
		return list.size() == 0;
	}

	@Override
	public void clear() {
		list.clear();
	}
	
}
