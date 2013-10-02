package list2OctoberAss3;

public interface PriorityQueueI<E> {
	
	public void insert(int priority, E entry);
	
	public E removeMin();
	
	public E min();
	
	public int size();
	
	public boolean isEmpty();
}
