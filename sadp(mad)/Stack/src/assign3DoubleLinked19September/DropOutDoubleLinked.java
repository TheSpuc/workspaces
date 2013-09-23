package assign3DoubleLinked19September;

public class DropOutDoubleLinked<T> implements DropOutStackI<T> {

	private Node head;
	private Node tail;
	private int numberOfEntries;
	private final int MAX = 5;

	public DropOutDoubleLinked(){
		head = new Node();
		tail = new Node();
		tail.post = head;
		head.pre = tail;
		numberOfEntries = 0;
	}

	@Override
	public void push(T newEntry) {
		Node toInsert = new Node();
		toInsert.data = newEntry;
		if(numberOfEntries == MAX){
			Node temp = tail.post;
			tail.post = temp.post;
			temp.post.pre = tail;
			temp.data = null;
			temp.pre = null;
			temp.post = null;
			numberOfEntries--;
		}
		Node temp = head.pre;
		temp.post = toInsert;
		toInsert.pre = temp;
		toInsert.post = head;
		head.pre = toInsert;
		numberOfEntries++;
	}

	@Override
	public T pop() {
		T result = null;
		if(head.pre != tail){
			Node temp = head.pre;
			result = temp.data;
			head.pre = temp.pre;
			head.pre.post = head;
			temp.data = null;
			temp.post = null;
			temp.pre = null;
		}
		numberOfEntries--;
		return result;
	}

	@Override
	public T peek() {
		T result = null;
		if(head.pre != tail){
			result = head.pre.data;
		}
		return result;
	}

	@Override
	public boolean isEmpty() {
		return numberOfEntries==0;
	}

	@Override
	public void clear() {
		head.pre = tail;
		tail.post = head;
		numberOfEntries = 0;
	}

	private class Node{
		private Node post;
		private Node pre;
		private T data;
	}
}
