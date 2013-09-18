package nodes5September1;/** * A class of bags whose entries are stored in a chain of linked nodes. The bag * is never full. *  * @author Frank M. Carrano * @version 3.0 */public class LinkedBag<T> implements BagI<T> {	private Node firstNode; // reference to first node	private int numberOfEntries;	public LinkedBag() {		firstNode = null;		numberOfEntries = 0;	} // end default constructor	/**	 * Sees whether this bag is empty.	 * 	 * @return true if the bag is empty, or false if not	 */	public boolean isEmpty() {		return numberOfEntries == 0;	} // end isEmpty	/**	 * Gets the number of entries currently in this bag.	 * 	 * @return the integer number of entries currently in the bag	 */	public int getCurrentSize() {		return numberOfEntries;	} // end getCurrentSize	/**	 * Adds a new entry to this bag.	 * 	 * @param newEntry	 *            the object to be added as a new entry	 * @return true if the addition is successful, or false if not	 */	public boolean add(T newEntry) // OutOfMemoryError possible	{		// add to beginning of chain:		Node newNode = new Node(newEntry);		newNode.next = firstNode; // make new node reference rest of chain		// (firstNode is null if chain is empty)		firstNode = newNode; // new node is at beginning of chain		numberOfEntries++;		return true;	} // end add	/**	 * Sees whether this bag is full.	 * 	 * @return false	 */	public boolean isFull() {		//TODO		return false;	} // end isFull	/**	 * Removes one unspecified entry from this bag, if possible.	 * 	 * @return either the removed entry, if the removal was successful, or null	 */	public T remove() {		T entry = null;		if(firstNode != null){			numberOfEntries--;			entry = firstNode.data;			firstNode.data = null;			firstNode = firstNode.next;		}		return entry; 	} // end remove	/**	 * Removes one occurrence of a given entry from this bag.	 * 	 * @param anEntry	 *            the entry to be removed	 * @return true if the removal was successful, or false otherwise	 */	public boolean remove(T anEntry) {		boolean found = false;		if(numberOfEntries == 0){			found = false;		}else if(firstNode.data.equals(anEntry)){			found = true;			remove();		}else{			Node temp = firstNode;			while(temp.next != null && !found){				Node search = temp.next;				if(search.data.equals(anEntry)){					temp.next = search.next;					search.data = null;					search.next = null;					numberOfEntries--;					found = true;				}else temp = search;			}		}		return found; // STUB	} // end remove	/** Removes all entries from this bag. */	public void clear() {		while(!isEmpty()){			remove();		}	} // end clear	/**	 * Counts the number of times a given entry appears in this bag.	 * 	 * @param anEntry	 *            the entry to be counted	 * @return the number of times anEntry appears in the bag	 */	public int getFrequencyOf(T anEntry) {		Node temp = firstNode;		int result = 0;		while(temp != null){			if(temp.data.equals(anEntry)){				result++;			}			temp = temp.next;		}		return result;	} // end getFrequencyOf	/**	 * Tests whether this bag contains a given entry.	 * 	 * @param anEntry	 *            the entry to locate	 * @return true if the bag contains anEntry, or false otherwise	 */	public boolean contains(T anEntry) {		boolean found = false;		Node temp = firstNode;		while(!found && temp != null){			if(anEntry.equals(temp.data)){				found = true;			}			else{				temp = temp.next;			}		}		return found; 	} // end contains	/**	 * Retrieves all entries that are in this bag.	 * 	 * @return a newly allocated array of all the entries in the bag	 */	public T[] toArray() {		// the cast is safe because the new array contains null entries		@SuppressWarnings("unchecked")		T[] result = (T[]) new Object[numberOfEntries]; // unchecked cast		int index = 0;		Node currentNode = firstNode;		while ((index < numberOfEntries) && (currentNode != null)) {			result[index] = currentNode.data;			index++;			currentNode = currentNode.next;		} // end while		return result;	} // end toArray	private class Node {		private T data; // entry in bag		private Node next; // link to next node		private Node(T dataPortion) {			this(dataPortion, null);		} // end constructor		private Node(T dataPortion, Node nextNode) {			data = dataPortion;			next = nextNode;		} // end constructor	} // end Node} // end LinkedBag1