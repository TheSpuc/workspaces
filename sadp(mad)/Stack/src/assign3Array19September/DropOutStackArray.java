package assign3Array19September;

public class DropOutStackArray<T> implements DropOutStackI<T> {

	private T[] array;
	private final int MAX = 5;
	private int numberOfEntries;

	public DropOutStackArray(){
		@SuppressWarnings("unchecked")
		T[] temp = (T[])new Object[MAX]; 
		array = temp;
		numberOfEntries = 0;
	}

	@Override
	public void push(T newEntry) {
		if(numberOfEntries == MAX){
			@SuppressWarnings("unchecked")
			T[] temp = (T[])new Object[MAX]; 
			numberOfEntries--;
			System.arraycopy(array, 1, temp, 0, numberOfEntries);
			array = temp;
		}
		array[numberOfEntries] = newEntry;
		numberOfEntries++;
	}

	@Override
	public T pop() {
		T result = array[numberOfEntries-1];
		array[numberOfEntries-1] = null;
		numberOfEntries--;
		return result;
	}

	@Override
	public T peek() {
		if(numberOfEntries == 0){
			return array[numberOfEntries];
		}else return array[numberOfEntries-1];
	}

	@Override
	public boolean isEmpty() {
		return numberOfEntries == 0;
	}

	@Override
	public void clear() {
		for(int i=0; i<numberOfEntries; i++){
			array[i] = null;
		}
		numberOfEntries = 0;
	}
}
