package list2OctoberAss1;

public interface ListI<E> {
	
	public E get(int index);
	
	public E remove(int index);
	
	public void add(int index, E entry);
	
	public void set(int index, E entry);
	
	public int size();
	
	public boolean isEmpty();
}
