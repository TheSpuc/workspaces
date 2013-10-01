package assign225September;

public interface QueueI<E> {
	
	/**
	 * Inserts a new element to the queue
	 * The element is placed last in the queue
	 * based on the FIFO principle
	 */
	public void enqueue(E entry);
	
	/**
	 * Removes the first element from the queue
	 * @return the element that is removed
	 */
	public E dequeue();
	
	/**
	 * Returns the first element in the queue
	 * @return the first element in the queue
	 */
	public E getFront();
	
	/**
	 * Returns whether or not the queue is empty
	 * @return true or false based on if queue is empty
	 */
	public boolean isEmpty();
	
	/**
	 * Clears the queue
	 */
	public void clear();
}
