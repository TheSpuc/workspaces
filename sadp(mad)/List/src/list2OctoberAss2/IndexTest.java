package list2OctoberAss2;

public class IndexTest {
	
	public static void main(String[] args){
		IndexQueue<Integer> queue = new IndexQueue<>();
		System.out.println("Empty: " + queue.isEmpty());
		queue.enqueue(1);
		System.out.println("Empty: " + queue.isEmpty());
		System.out.println(queue.dequeue());
		System.out.println("\nNew awesome check!");
		queue.enqueue(10);
		queue.enqueue(1234);
		queue.enqueue(112);
		queue.enqueue(1456);
		queue.enqueue(1234);
		queue.enqueue(346);
		queue.enqueue(4567);
		queue.enqueue(658);
		queue.enqueue(234);
		while(!queue.isEmpty()){
			System.out.println(queue.dequeue());
		}
	}
}
