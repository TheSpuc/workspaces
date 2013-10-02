package list2OctoberAss3;

import java.util.NoSuchElementException;

public class PriorityQueue<E> implements PriorityQueueI<E> {

	private Item<E>[] array;
	private int numberOfEntries;
	
	@SuppressWarnings("unchecked")
	public PriorityQueue(){
		array = new Item[10];
		numberOfEntries = 0;
	}
	
	@Override
	public void insert(int priority, E entry) {
		if(priority < 0){
			throw new NiclasException("Hej jeg hedder Niclas og jeg er en stor exception");
		}
		if(numberOfEntries == array.length-1){
			grow();
		}
		Item<E> toInsert = new Item<E>();
		toInsert.setData(entry);
		toInsert.setPriority(priority);
		int index = 0;
		boolean found = false;
		while (!found && index<numberOfEntries){
			if(toInsert.compareTo(array[index]) <= 0){
				found = true;
			}else index++;
		}
		for(int i=numberOfEntries; i>index; i--){
			array[i] = array[i-1]; 
		}
		array[index] = toInsert;
		numberOfEntries++;
	}
	
	private void grow(){
		@SuppressWarnings("unchecked")
		Item<E>[] tempArray = new Item[array.length*2];
		System.arraycopy(array, 0, tempArray, 0, numberOfEntries);
		array = tempArray;
	}

	@Override
	public E removeMin() {
		if(isEmpty()){
			throw new NoSuchElementException();
		}
		E result = array[0].getData();
		for(int i=0; i<numberOfEntries-1; i++){
			array[i] = array[i+1];
		}
		numberOfEntries--;
		return result;
	}

	@Override
	public E min() {
		if(isEmpty()){
			throw new NoSuchElementException();
		}
		return array[0].getData();
	}

	@Override
	public int size() {
		return numberOfEntries;
	}

	@Override
	public boolean isEmpty() {
		return numberOfEntries == 0;
	}
	
}
