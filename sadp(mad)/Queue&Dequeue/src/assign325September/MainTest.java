package assign325September;

public class MainTest {
	
	public static void main(String[] args){
		DequeArray<Integer> que = new DequeArray<>();
		
		que.addFirst(1);
		System.out.println(que.getLast());
		que.addFirst(232);
		que.addFirst(25);
		que.addFirst(2123);
		que.addFirst(26756);
		que.addFirst(234);
		que.addFirst(287);
		que.addFirst(209);
		que.addFirst(278);
		que.addFirst(23);
//		que.addFirst(123);
		System.out.println(que.getFirst());
		while(!que.isEmpty()){
			System.out.println("Input: " + que.removeLast());
		}
	}
}
