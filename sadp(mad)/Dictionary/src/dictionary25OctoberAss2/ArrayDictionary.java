package dictionary25OctoberAss2;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class ArrayDictionary<K, V> implements Dictionary<K, V>{

	private Entry<K, V>[] entries;
	private int size;

	@SuppressWarnings("unchecked")
	public ArrayDictionary(){
		entries = new Entry[10];
		size = 0;
	}

	@Override
	public V get(K key) {
		V result = null;
		boolean found = false;
		int index = 0;
		while(index < size && !found){
			if(entries[index].key == key){
				result = entries[index].value;
			}else index++;
		}
		return result;
	}

	@Override
	public V put(K key, V value) {
		if(key == null || value == null){
			throw new RuntimeException("Value and Key is not allowed to be null");
		}
		Entry<K, V> entry = new Entry<>();
		entry.key = key;
		entry.value = value;
		if(size == entries.length){
			grow();
		}

		int index = 0;
		boolean found = false;
		while(!found && index<size){
			if(entries[index].key == key){
				Entry<K, V> temp = entries[index];
				entries[index] = entry;
				entry = temp;
				found = true;
			}else index++;
		}
		if(!found){
			entries[size] = entry;
			size++;
			return entry.value;
		}else return null;
	}

	private void grow(){
		@SuppressWarnings("unchecked")
		Entry<K, V>[] temp = (Entry[]) new Object[entries.length*2];
		System.arraycopy(entries, 0, temp, 0, entries.length);
		entries = temp;
	}

	@Override
	public V remove(K key) {
		Entry<K, V> en = null;
		int index = 0;
		boolean found = false;
		while(index < size && !found){
			if(entries[index].key == key){
				en = entries[index];
				found = true;
				entries[index] = entries[size];
				size--;
			}else index++;
		}
		if(found){
			return en.value;
		}else return null;
	}

	@Override
	public Iterator<K> keys() {
		return new KeyIterator();
	}

	@Override
	public Iterator<V> values() {
		return new ValueIterator();
	}

	@Override
	public int size() {
		return size;
	}

	@Override
	public boolean isEmpty() {
		return size == 0;
	}

	private class Entry<K, V>{
		K key;
		V value;
	}

	/**
	 * Key iterator
	 * @author mmb
	 */
	private class KeyIterator implements Iterator<K>{

		private int currentIndex;

		public KeyIterator(){
			currentIndex = 0;
		}

		@Override
		public boolean hasNext() {
			return currentIndex < size;
		}

		@Override
		public K next() {
			if(hasNext()){
				return entries[currentIndex].key;
			}
			throw new NoSuchElementException();
		}

		@Override
		public void remove() {
			throw new UnsupportedOperationException();
		}
	}

	/**
	 * Value Iterator
	 * @author mmb
	 */
	private class ValueIterator implements Iterator<V>{

		private int currentIndex;

		public ValueIterator(){
			currentIndex = 0;
		}

		@Override
		public boolean hasNext() {
			return currentIndex < size;
		}

		@Override
		public V next() {
			if(hasNext()){
				return entries[currentIndex].value;
			}
			throw new NoSuchElementException();
		}

		@Override
		public void remove() {
			throw new UnsupportedOperationException();
		}

	}
}
