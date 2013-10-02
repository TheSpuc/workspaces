package list2OctoberAss1;

public class LinkedListTest {

	public static void main(String[] args){
		LinkedList<Integer> list = new LinkedList<>();
		System.out.println(list.isEmpty());
		list.add(0, 0);
		list.add(1, 1);
		list.add(2, 2);
		list.add(3, 3);
		list.add(4, 4);
		list.add(0, 5);
		System.out.println(list.isEmpty());
		System.out.println(list.get(0));
		System.out.println(list.get(1));
		System.out.println(list.get(2));
		System.out.println(list.get(3));
		System.out.println(list.get(4));
		System.out.println(list.get(5));
	}
}
