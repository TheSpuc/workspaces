package list2OctoberAss1;

import java.util.NoSuchElementException;

public class LinkedList<E> implements ListI<E>{

	private Node firstNode;
	private int size;

	public LinkedList(){
		firstNode = null;
		size = 0;
	}

	@Override
	public E get(int index) {
		if(index < 0 || index >= size || isEmpty()){
			throw new NoSuchElementException();
		}
		E result = null;
		Node temp = firstNode;
		for(int i=0; i<index; i++){
			temp = temp.next;
		}
		result = temp.data;
		return result;
	}

	@Override
	public E remove(int index) {
		if(index < 0 || index >= size || isEmpty()){
			throw new NoSuchElementException();
		}
		E result = null;
		if(index == 0){
			result = firstNode.data;
			firstNode = firstNode.next;
		}else{
			Node temp = firstNode;
			for(int i=0; i<index-1; i++){
				temp = temp.next;
			}
			Node toRemove = temp.next;
			result = toRemove.data;
			toRemove.data = null;
			temp.next = toRemove.next;
			toRemove.next = null;
		}
		size--;
		return result;
	}

	@Override
	public void add(int index, E entry) {
		if(index < 0 || index > size){
			throw new NoSuchElementException();
		}
		Node toInsert = new Node();
		toInsert.data = entry;
		if(isEmpty()){
			firstNode = toInsert;
		}else {
			Node temp = firstNode;
			if(index == 0){
				toInsert.next = temp;
				firstNode = toInsert;
			}else {
				for(int i=0; i<index-1; i++){
					temp = temp.next;
				}
				toInsert.next = temp.next;
				temp.next = toInsert;
			}
		}
		size++;

	}

	@Override
	public void set(int index, E entry) {
		if(index < 0 || index >= size || isEmpty()){
			throw new NoSuchElementException();
		}
		Node temp = firstNode;
		for(int i=0; i<index; i++){
			temp = temp.next;
		}
		temp.data = entry;

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
		private Node next;
		private E data;
	}

}
