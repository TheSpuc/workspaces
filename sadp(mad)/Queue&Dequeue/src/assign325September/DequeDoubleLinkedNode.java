package assign325September;

public class DequeDoubleLinkedNode<E> implements DequeI<E> {

	private Node head, tail;
	private int numberOfEntries;
	
	/**
	 * Like reading, Head is always left, as in post of the first element
	 * and tail is always right, as in pre of the last element.
	 * Head and tail starts by pointing at each other.
	 */
	public DequeDoubleLinkedNode(){
		head = new Node();
		tail = new Node();
		head.pre = tail;
		tail.post = head;
		numberOfEntries = 0;
	}
	
	@Override
	public void addFirst(E entry) {
		Node toInsert = new Node();
		toInsert.data = entry;
		toInsert.pre = head.pre;
		head.pre.post = toInsert;
		toInsert.post = head;
		head.pre = toInsert;
		numberOfEntries++;
	}

	@Override
	public void addLast(E entry) {
		Node toInsert = new Node();
		toInsert.data = entry;
		toInsert.post = tail.post;
		tail.post.pre = toInsert;
		toInsert.pre = tail;
		tail.post = toInsert;
		numberOfEntries++;
	}

	@Override
	public E removeFirst() {
		if(!isEmpty()){
			Node temp = head.pre;
			head.pre = temp.pre;
			numberOfEntries--;
			return temp.data;
		}
		return null;
	}

	@Override
	public E removeLast() {
		if(!isEmpty()){
			Node temp = tail.post;
			tail.post = temp.post;
			numberOfEntries--;
			return temp.data;
		}
		return null;
	}

	@Override
	public E getFirst() {
		if(!isEmpty()){
			return head.pre.data;
		}
		return null;
	}

	@Override
	public E getLast() {
		if(!isEmpty()){
			return tail.post.data;
		}
		return null;
	}

	@Override
	public int size() {
		return numberOfEntries;
	}

	@Override
	public boolean isEmpty() {
		return numberOfEntries == 0;
	}

	@Override
	public void clear() {
		head = new Node();
		tail = new Node();
		head.pre = tail;
		tail.post = head;
		numberOfEntries = 0;
	}

	private class Node{
		private Node post;
		private Node pre;
		private E data;
	}
}
