package assign425September;

public class CircularList<T> {

	private Node firstNode;
	private Node randomStart;

	public CircularList(){
		firstNode = new Node();
		randomStart = null;
		firstNode.next = firstNode;
		
	}

	public void addEntry(T entry){
		Node toInsert = new Node();
		toInsert.data = entry;
		if(firstNode.next == firstNode){
			toInsert.next = firstNode;
			firstNode.next = toInsert;
		}else{
			Node tempNext = firstNode.next;
			firstNode.next = toInsert;
			toInsert.next = tempNext;
		}
	}

	public void print(){
		Node temp = firstNode.next;
		while(temp != firstNode){
			System.out.println(temp.data);
			temp = temp.next;
		}
	}
	
	public void randomStart(){
		if(firstNode.next != firstNode){
			int num = (int) (Math.random()*1000);
			randomStart = firstNode;
			while(num>=0){
				randomStart = randomStart.next;
				if(randomStart == firstNode){
					randomStart = randomStart.next;
				}
				num--;
			}
		}
	}

	public T remove(int count){
		T result = null;
		if(firstNode.next != firstNode && randomStart != null){
			Node temp = randomStart;
			Node runNode = randomStart.next;
			int i = 1;
			while(i<count){
				temp = runNode; 
				runNode = runNode.next; 
				if(runNode == firstNode){
					temp = runNode;
					runNode = runNode.next;
				}
				i++;
			}
			result = runNode.data;
			runNode.data = null;
			runNode = runNode.next;
			temp.next = runNode; 
		}
		return result;
	}

	private class Node{
		private Node next;
		private T data;
	}
}
