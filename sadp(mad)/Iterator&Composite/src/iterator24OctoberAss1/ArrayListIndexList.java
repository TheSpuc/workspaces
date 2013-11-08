package iterator24OctoberAss1;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

public class ArrayListIndexList<E> implements IndexListI<E> {

	private List<E> list;
	private int modificationVar;
	
	public ArrayListIndexList(){
		list = new ArrayList<E>();
		modificationVar = 0;
	}
	
	@Override
	public E get(int i) {
		return list.get(i);
	}

	@Override
	public E remove(int i) {
		modificationVar++;
		return list.remove(i);
	}

	@Override
	public void add(int i, E e) {
		modificationVar++;
		list.add(i, e);

	}

	@Override
	public E set(int i, E e) {
		modificationVar++;
		return list.set(i, e);
	}

	@Override
	public int size() {
		return list.size();
	}

	@Override
	public boolean isEmpty() {
		return list.isEmpty();
	}
	@Override
	public Iterator<E> iterator() {
		return new IndexIterator();
	}

	private class IndexIterator implements Iterator<E>{

		private int index;
		private int modificationCheck;

		public IndexIterator(){
			index = 0;
			modificationCheck = modificationVar;
		}

		@Override
		public boolean hasNext() {
			return index < list.size();
		}

		@Override
		public E next() {
			if(modificationCheck != modificationVar){
				throw new ConcurrentModificationException();
			}
			if(hasNext()){
				E result = list.get(index);
				index++;
				return result;
			}else throw new NoSuchElementException();
		}

		@Override
		public void remove() {
			throw new UnsupportedOperationException();
		}

	}

}
