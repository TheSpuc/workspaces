package stackpackage;

import java.util.NoSuchElementException;

public class LinkedStack<T> implements StackI<T> {

	private Node top;
	
	@Override
	public void push(T newEntry) {
		Node temp = new Node(newEntry, top);
		top = temp;
		
	}

	@Override
	public T pop() {
		Node temp = top;
		if (!isEmpty()){
			top = top.getNext();
			temp.setNext(null);
		}
		else throw new NoSuchElementException();
		return temp.getData();
	}

	@Override
	public T peek() {
		if (isEmpty()){
			throw new NoSuchElementException();
		}
		return top.getData();

	}

	@Override
	public boolean isEmpty() {
		return top==null;
	
	}

	@Override
	public void clear() {
		top = null;
		
	}
	private class Node{
		private T data;
		private Node next;
		
		public Node(){
			this(null,null);
		}
		public T getData() {
			return data;
		}
		public void setData(T data) {
			this.data = data;
		}
		public Node getNext() {
			return next;
		}
		public void setNext(Node next) {
			this.next = next;
		}
		public Node(T data, Node next){
			this.data = data;
			this.next = next;
		}
		
	}

}
