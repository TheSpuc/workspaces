package nodes12September;

public class LinkedList<E extends Comparable<E>> {

	private Node currentNode;
	private int numberOfEntries;

	public LinkedList() {
		currentNode = null;
		numberOfEntries = 0;
	}

	public Node getCurrentNode(){
		Node temp = currentNode;
		return temp;
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
		
		currentNode = recursiveMerge(currentNode, list.getCurrentNode());
				
				
//		//containing the pre node, so we always have a reference to the pre.
//		Node pre = currentNode;
//		//first node from the parameter
//		Node toInsert = list.getCurrentNode();
//		//helper node for saving information when its needed to insert nodes
//		Node helperNode = null;
//
//		//if list == null, no reason for doing anything
//		if(toInsert != null){
//			//special case for checking if the currentNode is the smallest
//			//or the first node in parameter list i the smallest or 
//			//if pre is null.
//			if(pre == null || pre.data.compareTo(toInsert.data) >= 0){
//				//helper node used for holding the next node for toInsert
//				//if we don't hold it we would get a infinite loop
//				helperNode = toInsert.next;
//				toInsert.next = pre;
//				//we have to update so the currentNode points on the new smallest node
//				currentNode = toInsert;
//				//remembering to set pre as the new smallest item too.
//				pre = toInsert;
//				toInsert = helperNode;
//			}
//			//normal merge, just remember to start from pre next item,
//			//then we have both the node before and after.
//			while(pre.next != null && toInsert != null){
//				int compare = pre.next.data.compareTo(toInsert.data);
//				if(compare >= 0){
//					//remember to hold the toInsert next before overwritten ref
//					helperNode = toInsert.next;
//					//updating toInsert with new next ref, from pre next ref
//					toInsert.next = pre.next;
//					//making use of the pre ref, by updating it to point
//					//on the new sorted node
//					pre.next = toInsert;
//					//making certain that we look at the rest of the toInsert nodes
//					toInsert = helperNode;
//				}else{
//					//updating the new pre
//					pre = pre.next;
//				}
//			}
//			//if the rest of toInsert node have larger data 
//			//then we make certain to get the rest sorted in
//			while(toInsert != null){
//				//using that we have a pre ref
//				pre.next = toInsert;
//				//updating pre to point at the newly inserted
//				pre = pre.next;
//				//updating toInsert
//				toInsert = toInsert.next;
//			}
//		}
	}

	private Node recursiveMerge(Node current, Node toInsert){
		//Basecase
		if (current == null) 
			return toInsert;
		//Basecase
		if (toInsert == null) 
			return current;

		if (current.data.compareTo(toInsert.data) < 0) {
			current.next = recursiveMerge(current.next, toInsert);
			return current;
		} else {
			toInsert.next = recursiveMerge(toInsert.next, current);
			return toInsert;
		}
	}

	/**
	 * Check whether or not it contains the entry
	 * @param entry
	 * @return
	 */
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
