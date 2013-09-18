package nodes12September;

public class LinkedList<E extends Comparable<E>> {

	private Node currentNode;
	private int numberOfEntries;

	public LinkedList() {
		currentNode = null;
		numberOfEntries = 0;
	}

	/**
	 * Tilføjer e til listen, så listen fortsat er sorteret i henhold til * den naturlige ordning på E
	 * @param e
	 */
	public void addElement(E entry) {
		Node toInsert = new Node();
		toInsert.data = entry;

		Node temp = currentNode;
		if(temp == null || temp.data.compareTo(entry) >= 0){
			toInsert.next = temp;
			currentNode = toInsert;
		}else{
			boolean found = false;
			Node post = temp.next;
			while(!found && post != null){
				int compareTemp = post.data.compareTo(entry);
				if(compareTemp >= 0){
					found = true;
				}else{
					temp = post;
					post = post.next;
				}
			}
			temp.next = toInsert;
			toInsert.next = post;
		}		
		numberOfEntries++;
	}
	
	/**
	* tilføjer alle elementerne fra list til den aktuelle liste.
	* Listen er fortsat sorteret i henhold til den naturlige ordning på
	* E
	* @param list
	*/
	public void addAll(LinkedList<E> list){
		
	}

	public boolean contains(E entry){
		Node temp = currentNode;
		boolean found = false;

		while(!found && temp != null){
			if(temp.data.equals(entry)){
				found = true;
			}else temp = temp.next;
		}
		return found;
	}

	/**
	 * Fjerner den første forrekomst af e i listen, listen er fortsat * sorteret i henhold til den naturlige ordning på E
	 * Returner true hvis e blev fjernet fra listen ellers returneres * false
	 * @param e
	 */
	public boolean removeElement(E entry) {
		boolean found = false;
		Node temp = currentNode;
		if(temp != null){
			if(temp.data.equals(entry)){
				temp.data = null;
				currentNode = temp.next;
				temp.next = null;
				found = true;
			}else{
				Node post = temp.next;

				while(!found && post != null){
					if(post.data.equals(entry)){
						post.data = null;
						temp.next = post.next;
						post.next = null;
						found = true;
					}else {
						temp = post;
						post = post.next;
					}
				}
			}
		}

		return found;
	}

	/**
	 * Udskriver elementerne fra liste i sorteret rækkefølge 
	 */
	public void printElements() {
		Node temp = currentNode;
		while(temp != null){
			System.out.println(temp.data);
			temp = temp.next;
		}
	}

	/*
	 * Returnerer antallet af elementer i listen
	 */
	public int countElements() {
		return numberOfEntries;
	}


	private class Node{
		private Node next;
		private E data;
	}

}
