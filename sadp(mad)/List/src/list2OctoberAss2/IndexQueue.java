package list2OctoberAss2;

public class IndexQueue<E> implements QueueI<E> {

	private IndexListI adapter;
	
	public IndexQueue(){
		adapter = new LinkedIndexList<>(); 
	}
	
	@Override
	public void enqueue(E entry) {
		
	}

	@Override
	public E dequeue() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public E getFront() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void clear() {
		// TODO Auto-generated method stub
		
	}

}
