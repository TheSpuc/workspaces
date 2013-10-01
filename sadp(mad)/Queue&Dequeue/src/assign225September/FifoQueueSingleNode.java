package assign225September;

public class FifoQueueSingleNode<E> implements QueueI<E> {
	
	private Node head;
	private int numberofEntries;
	
	public FifoQueueSingleNode(){
		head = null;
		numberofEntries = 0;
	}
	
	@Override
	public void enqueue(E entry) {
		Node toInsert = new Node();
		toInsert.data = entry;
		if(numberofEntries == 0){
			head = toInsert;
		}else {
			Node temp = head;
			while(temp.next != null){
				temp = temp.next;
			}
			temp.next = toInsert;
		}
		numberofEntries++;
	}

	@Override
	public E dequeue() {
		if(!isEmpty()){
			Node temp = head;
			head = head.next;
			numberofEntries--;
			return temp.data;
		}
		return null;
	}

	@Override
	public E getFront() {
		if(!isEmpty()){
			return head.data;
		} 
		return null;
	}

	@Override
	public boolean isEmpty() {
		return numberofEntries == 0;
	}

	@Override
	public void clear() {
		head = null;
		numberofEntries = 0;
	}
	
	private class Node{
		private E data;
		private Node next;
	}

}
