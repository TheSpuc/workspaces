package assign3SingleNode19September;

public class DropOutStack<T> implements DropOutStackI<T> {

	private int numberOfEntries;
	private final int MAX = 5;
	private Node top;

	public DropOutStack(){
		numberOfEntries = 0;
		top = null;
	}

	@Override
	public void push(T newEntry) {
		Node toInsert = new Node();
		toInsert.data = newEntry;
		if(top == null){
			top = toInsert;
		}else{
			if(numberOfEntries == MAX){
				Node temp = top;
				for(int i=0; i<MAX-2; i++){
					temp = temp.pre;
				}
				temp.pre.data = null;
				temp.pre = null;
				numberOfEntries--;
			}
			toInsert.pre = top;
			top = toInsert;
		}
		numberOfEntries++;
	}

	@Override
	public T pop() {
		Node temp = top;
		if(!isEmpty()){
			top = temp.pre;
			numberOfEntries--;
		}
		return temp.data;
	}

	@Override
	public T peek() {
		T result = null;
		if(!isEmpty()){
			result = top.data;
		}
		return result;
	}

	@Override
	public boolean isEmpty() {
		return numberOfEntries == 0;
	}

	@Override
	public void clear() {
		Node temp = top;
		while(temp != null){
			Node helper = temp.pre;
			temp.data = null;
			temp.pre = null;
			temp = helper;
		}
		numberOfEntries = 0;
	}

	private class Node{
		private Node pre;
		private T data;
	}
}
