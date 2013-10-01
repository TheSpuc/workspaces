package assign125September;

public class FifoQueue<E> implements QueueI<E> {

	private E[] array;
	private int front;
	private int rear;
	private int numberOfEntries;

	public FifoQueue(){
		@SuppressWarnings("unchecked")
		E[] temp = (E[]) new Object[10];
		array = temp;
		//hack for setting all variables to 0
		front = rear = numberOfEntries = 0;
	}

	@Override
	public void enqueue(E entry) {
		if(numberOfEntries == array.length){
			enlarge();
		}
		//by inserting first, we take care of the special case, that we need
		//insertion on index 0, when the array is empty
		array[rear] = entry;
		rear = (rear + 1) % array.length;
		numberOfEntries++;
	}
	
	private void enlarge(){
		@SuppressWarnings("unchecked")
		E[] temp = (E[])new Object[array.length*2]; 
		for(int i = 0; i<numberOfEntries; i++){
			//using i as the basis for insertion
			temp[i] = array[front];
			//calculating where the new front is based on the array we are enlarging 
			front = (front+1) % array.length;
		}
		front = 0;
		//rear should point on the next place to insert.
		rear = numberOfEntries;
		array = temp;
	}

	@Override
	public E dequeue() {
		E result = null;
		if(!isEmpty()){
			result = array[front];
			//Helping the garbage collector
			array[front] = null;
			//calculating where the new front should be
			front = (front+1) % array.length;
			numberOfEntries--;
		}
		return result;
	}

	@Override
	public E getFront() {
		return array[front];
	}

	@Override
	public boolean isEmpty() {
		return numberOfEntries == 0;
	}

	@Override
	public void clear() {
		front = rear = numberOfEntries = 0;
		@SuppressWarnings("unchecked")
		E[] temp = (E[]) new Object[10];
		array = temp;
	}
}
