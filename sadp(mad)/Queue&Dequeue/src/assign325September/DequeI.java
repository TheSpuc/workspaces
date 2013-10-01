package assign325September;

public interface DequeI<E> {
	
	public void addFirst(E entry);
	
	public void addLast(E entry);
	
	public E removeFirst();
	
	public E removeLast();
	
	public E getFirst();
	
	public E getLast();
	
	public int size();
	
	public boolean isEmpty();
	
	public void clear();
}
