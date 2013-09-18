package adt11September;

public class ConcreteRing<T> implements RingInterface<T>{

	private T[] data;
	private int numberOfEntries;
	private int currentItem;
	
	@SuppressWarnings("unchecked")
	public ConcreteRing(){
		data = (T[])new Object[10];
		numberOfEntries = 0;
		currentItem = 0;
	}
	
	
	@Override
	public void advance() {
		if(currentItem+1 < numberOfEntries){
			currentItem++;
		}else currentItem = 0;
	}

	@Override
	public T getCurrentItem() {
		return data[currentItem];
	}

	@Override
	public void add(T entry) {
		if(data.length == numberOfEntries){
			@SuppressWarnings("unchecked")
			T[] temp = (T[])new Object[(numberOfEntries*2)];
			for(int i=0; i<numberOfEntries; i++){
				temp[i] = data[i];
			}
			data = temp;
			for(int i=0; i<numberOfEntries; i++){
			}
		}
		data[numberOfEntries] = entry;
		currentItem = numberOfEntries;
		numberOfEntries++;
	}

	@Override
	public T remove(T entry) {
		T result = null;
		if(numberOfEntries > 0){
			int i = 0;
			boolean found = false;
			while(!found && i<numberOfEntries){
				if(data[i].equals(entry)){
					found = true;
					result = data[i];
					data[i] = data[numberOfEntries-1];
					numberOfEntries--;
				}
				i++;
			}
		}
		return result;
	}


	@SuppressWarnings("unchecked")
	@Override
	public void clear() {
		data = (T[])new Object[10];
		numberOfEntries = 0;
		currentItem = 0;
	}


	@Override
	public boolean contains(T entry) {
		boolean result = false;
		if(numberOfEntries > 0){
			int i = 0;
			boolean found = false;
			while(!found && i<numberOfEntries){
				if(data[i].equals(entry)){
					found = true;
					result = found;
				}
				i++;
			}
		}
		return result;
	}


	@Override
	public int getCurrentSize() {
		return numberOfEntries;
	}


	@Override
	public boolean isEmpty() {
		return numberOfEntries == 0;
	}


	@Override
	public T[] toArray() {
		@SuppressWarnings("unchecked")
		T[] result = (T[])new Object[numberOfEntries];
		for(int i=0; i<numberOfEntries; i++){
			result[i] = data[i]; 
		}
		return result;
	}

}
