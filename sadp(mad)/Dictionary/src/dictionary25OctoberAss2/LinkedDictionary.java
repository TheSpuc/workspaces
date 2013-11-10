package dictionary25OctoberAss2;

import java.util.Iterator;

public class LinkedDictionary<K, V> implements Dictionary<K, V> {

	private Node head;
	private int size;
	
	public LinkedDictionary(){
		head = new Node();
		size = 0;
	}
	
	@Override
	public V get(K key) {
		Node temp = head;
		boolean found = false;
		while(!found && temp.next != null){
			if(temp.next.key == key){
				found = true;
			}else temp = temp.next;
		}
		if(found){
			return temp.value;
		}else return null;
	}

	@Override
	public V put(K key, V value) {
		if(key == null || value == null){
			throw new RuntimeException("key and value cant be null");
		}
		Node n = new Node();
		n.key = key;
		n.value = value;
		
		return null;
	}

	@Override
	public V remove(K key) {
		Node temp = head;
		boolean found = false;
		V result = null;
		while(!found && temp.next != null){
			if(temp.next.key == key){
				found = true;
				result = temp.next.value;
				temp.next = temp.next.next;
			}else temp = temp.next;
		}
		return result;
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
	
	private class Node{
		Node next;
		K key;
		V value;
	}
	
	private class KeyIterator implements Iterator<K>{

		private Node start;
		
		public KeyIterator(){
			start = head;
		}
		
		@Override
		public boolean hasNext() {
			return start.next != null;
		}

		@Override
		public K next() {
			if(hasNext()){
				start = start.next;
				return start.key;
			}else throw new RuntimeException("There is nothing");
		}

		@Override
		public void remove() {
			throw new UnsupportedOperationException();
		}
	}
	
	private class ValueIterator implements Iterator<V>{

		private Node start;
		
		public ValueIterator(){
			start = head;
		}
		
		@Override
		public boolean hasNext() {
			return start.next != null;
		}

		@Override
		public V next() {
			if(hasNext()){
				start = start.next;
				return start.value;
			}else throw new RuntimeException("There is nothing");
		}

		@Override
		public void remove() {
			throw new UnsupportedOperationException();
		}
	}

}
