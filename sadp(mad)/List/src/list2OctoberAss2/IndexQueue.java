package list2OctoberAss2;

public class IndexQueue<E> implements QueueI<E> {

	private ListI<E> adapter;
	
	public IndexQueue(){
		adapter = new LinkedList<>(); 
	}
	
	@Override
	public void enqueue(E entry) {
		adapter.add(0, entry);
	}

	@Override
	public E dequeue() {
		return adapter.remove(0);
	}

	@Override
	public E getFront() {
		return adapter.get(0);
	}

	@Override
	public boolean isEmpty() {
		return adapter.isEmpty();
	}

	@Override
	public void clear() {
		adapter = new LinkedList<>();
	}

}
