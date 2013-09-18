package adt11September;

public interface RingInterface<T> {
	
	public void advance();
	
	public T getCurrentItem();
	
	public void add(T entry);
	
	public T remove(T entry);
	
	public void clear();
	
	public boolean contains(T entry);
	
	public int getCurrentSize();
	
	public boolean isEmpty();
	
	public T[] toArray();
}
