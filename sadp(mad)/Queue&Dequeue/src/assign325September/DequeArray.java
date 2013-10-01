package assign325September;

/**
 * Circular array class based as a Deque, front always points on the next place
 * to insert, the same goes for rear, thats why the check for full array is 
 * the length of the array-1, so there is always a free spot.
 * @author mmb
 *
 * @param <E>
 */
public class DequeArray<E> implements DequeI<E> {

	private E[] array; 
	private int front, rear, numberOfEntries;

	public DequeArray(){
		@SuppressWarnings("unchecked")
		E[] temp = (E[]) new Object[10];
		array = temp;
		front = 1; 
		rear = numberOfEntries = 0;
	}

	@Override
	public void addFirst(E entry) {
		if(numberOfEntries == array.length-1){
			grow();
		}
		array[front] = entry;
		front = (front+1) % array.length;
		numberOfEntries++;
	}

	@Override
	public void addLast(E entry) {
		if(numberOfEntries == array.length-1){
			grow();
		}
		array[rear] = entry;
		rear = (rear-1) % array.length;
		numberOfEntries++;
	}

	private void grow(){
		@SuppressWarnings("unchecked")
		E[] temp = (E[])new Object[array.length*2]; 
		for(int i = 0; i<numberOfEntries; i++){
			//using rear because we will make certain that 
			//the sorting is upholded, so we don't change
			//on any of the variables placement
			rear = (rear+1) % array.length;
			//using i as the basis for insertion
			temp[i] = array[rear];
		}
		//rear should point on the next place to insert.
		array = temp;
		front = numberOfEntries;
		rear = array.length-1;
	}

	@Override
	public E removeFirst() {
		E result = null;
		if(!isEmpty()){
			front = (((front-1) % array.length) + array.length) % array.length;
			result = array[front];
			array[front] = null;
			numberOfEntries--;
		}
		return result;
	}

	@Override
	public E removeLast() {
		E result = null;
		if(!isEmpty()){
			rear = (rear+1) % array.length;
			result = array[rear];
			array[rear] = null;
			numberOfEntries--;
		}
		return result;
	}

	@Override
	public E getFirst() {
		if(!isEmpty()){
			return array[front = (((front-1) % array.length) + array.length) % array.length];
		}
		return null;
	}

	@Override
	public E getLast() {
		if(!isEmpty()){
			return array[(rear+1) % array.length];
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
		@SuppressWarnings("unchecked")
		E[] temp = (E[]) new Object[10];
		array = temp;
		front = 1;
		rear = numberOfEntries = 0;
	}
}
